package com.bdd.TP.job;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.listener.JobCompletionNotificationListener;
import com.bdd.TP.mapper.RegionFieldSetMapper;
import com.bdd.TP.processor.MedicionItemProcessor;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import com.bdd.TP.step.ApiConsumerTasklet;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class DataDownloader {
    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final RestTemplateBuilder restTemplateBuilder;
    private DataSource dataSource;

    private final RegionService regionService;

    private final MedicionService medicionService;
    public DataDownloader(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, RestTemplateBuilder rsb, RegionService regionService, MedicionService medicionService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.restTemplateBuilder = rsb;
        this.regionService = regionService;
        this.medicionService = medicionService;
    }
    @Bean
    public Job dataDownloaderJob(JobCompletionNotificationListener listener, JobBuilderFactory jobBuilderFactory,
                                 StepBuilderFactory stepBuilderFactory,
                                 ItemReader<Medicion> csvFileReader,
                                 ItemWriter<Medicion> databaseWriter,
                                 Step csvReaderStep) {
        return jobBuilderFactory
                .get("dataDownloader")
                .incrementer(new RunIdIncrementer())
                .start(apiConsumer())
                .next(csvReaderStep)
                .listener(listener)
                .build();
    }
    @Bean
    public Step apiConsumer() {
        return stepBuilderFactory.get("apiConsumer")
                .tasklet(new ApiConsumerTasklet(restTemplateBuilder.build()))
                .allowStartIfComplete(true)
                .build();
    }



    @Bean
    public Step csvReaderStep(ItemReader<Medicion> csvFileReader, ItemWriter<Medicion> databaseWriter) {
        return stepBuilderFactory.get("csvReaderStep")
                .<Medicion, Medicion>chunk(10)
                .reader(csvFileReader)
                .processor(dataProcessor())
                .writer(databaseWriter)
                .allowStartIfComplete(true)
                .build();
    }
    @Bean
    public ItemProcessor<Medicion, Medicion> dataProcessor() {
        return new MedicionItemProcessor();
    }




    @Bean
    @StepScope
    public FlatFileItemReader<Medicion> CSVFileReader() {
        FlatFileItemReader<Medicion> reader = new FlatFileItemReader<>();
        reader.setResource(new org.springframework.core.io.FileSystemResource("medicion.csv"));
        reader.setLineMapper(new DefaultLineMapper<Medicion>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id_region", "fecha", "demanda","temperatura");
            }});
            setFieldSetMapper(new RegionFieldSetMapper(regionService, medicionService));
        }});

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Medicion> databaseWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Medicion> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO mediciones (id_region, fecha, demanda, temperatura) VALUES (:region.id, :fecha, :demanda, :temperatura)");
        writer.setDataSource(dataSource);

        return writer;
    }


}