package com.bdd.TP.job;

import com.bdd.TP.dao.DemandaYTemperatura;
import com.bdd.TP.listener.JobCompletionNotificationListener;
import com.bdd.TP.step.ApiConsumerTasklet;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
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

    public DataDownloader(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, RestTemplateBuilder rsb) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.restTemplateBuilder = rsb;
    }
    @Bean
    public Job dataDownloaderJob(JobCompletionNotificationListener listener, JobBuilderFactory jobBuilderFactory,
                                 StepBuilderFactory stepBuilderFactory,
                                 ItemReader<DemandaYTemperatura> csvFileReader,
                                 ItemWriter<DemandaYTemperatura> databaseWriter,
                                 Step csvReaderStep) {
        return jobBuilderFactory
                .get("dataDownloader")
                .incrementer(new RunIdIncrementer())
                .start(apiConsumer())
                .next(csvReaderStep)
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
    public Step csvReaderStep(ItemReader<DemandaYTemperatura> csvFileReader, ItemWriter<DemandaYTemperatura> databaseWriter) {
        return stepBuilderFactory.get("csvReaderStep")
                .<DemandaYTemperatura, DemandaYTemperatura>chunk(10)
                .reader(csvFileReader)
                .writer(databaseWriter)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<DemandaYTemperatura> CSVFileReader() {
        FlatFileItemReader<DemandaYTemperatura> reader = new FlatFileItemReader<>();
        reader.setResource(new org.springframework.core.io.FileSystemResource("medicion.csv"));
        reader.setLineMapper(new DefaultLineMapper<DemandaYTemperatura>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"region", "fecha", "demanda","temperatura"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<DemandaYTemperatura>() {{
                setTargetType(DemandaYTemperatura.class);
            }});
        }});
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<DemandaYTemperatura> databaseWriter(DataSource dataSource) {
        JdbcBatchItemWriter<DemandaYTemperatura> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO demanda_y_temperatura_data (region, fecha, demanda, temperatura) VALUES (:region, to_timestamp(:fecha, 'YYYY-MM-DD\"T\"HH24:MI:SS.US\"Z\"'), :demanda, :temperatura)");
        writer.setDataSource(dataSource);
        return writer;
    }


}
