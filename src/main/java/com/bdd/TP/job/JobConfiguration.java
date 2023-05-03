package com.bdd.TP.job;

//public class JobConfiguration {
//}
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.step.DownloadDataToCSV;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.bdd.TP.dao.Region;
import  com.bdd.TP.listener.JobCompletionNotificationListener;
import  com.bdd.TP.processor.RegionItemProcessor;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {
    @Autowired
    public final CammesaService cammesaService;
    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(CammesaService cammesaService, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.cammesaService = cammesaService;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer()).listener(listener)
                .flow(downloadDataToCSV(null,cammesaService)) // primer step
                .on("ALLGOOD")
                .to(doChunk(null)) // otro step
                .end()
                .build();
    }

    private Step downloadDataToCSV(DataSource ds, CammesaService cammesaService) {
        return stepBuilderFactory.get("downloadDataToCSV")
                .tasklet(new DownloadDataToCSV(ds,cammesaService))
                .build();
    }

    @Bean
    public FlatFileItemReader<Region> reader() {
        return new FlatFileItemReaderBuilder<Region>().name("regionItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("id", "idElemento", "idPadre","idRge","nombre")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Region>() {
                    {
                        setTargetType(Region.class);
                    }
                }).build();
    }

    @Bean
    public RegionItemProcessor processor() {
        return new RegionItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Region> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Region>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO regiones (id,id_elemento,id_padre, id_rge, nombre) VALUES (:id, :idElemento, :idPadre, :idRge , :nombre)")
                .dataSource(dataSource)
                .build();
    }


    @Bean
    public Step doChunk(JdbcBatchItemWriter<Region> writer) {
        return stepBuilderFactory.get("doChunk")
                .<Region, Region>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}

