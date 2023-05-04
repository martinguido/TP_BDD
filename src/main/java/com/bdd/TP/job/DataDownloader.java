package com.bdd.TP.job;

import com.bdd.TP.listener.JobCompletionNotificationListener;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.step.ApiConsumerTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DataDownloader {


    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;
    private final RestTemplateBuilder restTemplateBuilder;

    public DataDownloader(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, RestTemplateBuilder rsb) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.restTemplateBuilder = rsb;
    }

    @Bean
    public Job dataDownloaderJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory
                .get("dataDownloader")
                .incrementer(new RunIdIncrementer())
                .start(apiConsumer())
                .build();
    }
    @Bean
    private Step apiConsumer() {
        return stepBuilderFactory.get("apiConsumer")
                .tasklet(new ApiConsumerTasklet(restTemplateBuilder.build()))
                .build();
    }
}
