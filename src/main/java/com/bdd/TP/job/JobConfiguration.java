package com.bdd.TP.job;

//public class JobConfiguration {
//}
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.bdd.TP.dao.Region;
import  com.bdd.TP.listener.JobCompletionNotificationListener;
import  com.bdd.TP.processor.RegionItemProcessor;
import  com.bdd.TP.step.MailSenderTasklet;
import  com.bdd.TP.step.TruncateTableTasklet;
import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer()).listener(listener)
                .flow(truncateTable(null)) // primer step
                .on("ALLGOOD")
                .to(doChunk(null)) // otro step
                .from(truncateTable(null))
                .on("TRUNCATEFAILED")
                .to(errorMailSender()) // otro step
                .on("EMAILSENT")
                .fail()
                .end()
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
    public Step truncateTable(DataSource ds) {
        return stepBuilderFactory.get("truncateTable")
                .tasklet(new TruncateTableTasklet(ds, "TRUNCATE REGIONES"))
                .build();
    }

    @Bean
    public Step errorMailSender() {
        return stepBuilderFactory.get("errorMailSender").tasklet(new MailSenderTasklet()).build();
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

