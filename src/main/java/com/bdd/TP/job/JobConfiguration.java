//package com.bdd.TP.job;
//
////public class JobConfiguration {
////}
//import com.bdd.TP.service.CammesaService;
//import com.bdd.TP.step.DownloadDataToCSV;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class JobConfiguration {
//    public final JobBuilderFactory jobBuilderFactory;
//
//    public final StepBuilderFactory stepBuilderFactory;
//
//    public JobConfiguration( JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//
//    @Bean
//    public FlatFileItemReader<Region> reader() {
//        return new FlatFileItemReaderBuilder<Region>().name("regionItemReader")
//                .resource(new ClassPathResource("sample-data.csv"))
//                .delimited()
//                .names("id", "idElemento", "idPadre","idRge","nombre")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<Region>() {
//                    {
//                        setTargetType(Region.class);
//                    }
//                }).build();
//    }
//
//    @Bean
//    public RegionItemProcessor processor() {
//        return new RegionItemProcessor();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Region> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Region>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO regiones (id,id_elemento,id_padre, id_rge, nombre) VALUES (:id, :idElemento, :idPadre, :idRge , :nombre)")
//                .dataSource(dataSource)
//                .build();
//    }
//
//
//    @Bean
//    public Step doChunk(JdbcBatchItemWriter<Region> writer) {
//        return stepBuilderFactory.get("doChunk")
//                .<Region, Region>chunk(10)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer)
//                .build();
//    }
//}
//
