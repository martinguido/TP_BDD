
package com.bdd.TP.controller;
import com.bdd.TP.service.CammesaService;
import javassist.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1")
public class AsyncController {
    private final ConfigurableApplicationContext context;
    private static final Logger log = LoggerFactory.getLogger(AsyncController.class);
    private final JobLauncher jobLauncher;

    public AsyncController(ConfigurableApplicationContext context, JobLauncher jobLauncher) {
        this.context = context;
        this.jobLauncher= jobLauncher;
    }

    @PostMapping("/cammesa/runBatch")
    public String runBatch() throws Exception {
        log.info(String.valueOf(Thread.currentThread()));
        CompletableFuture.runAsync(() ->{
            Job job = context.getBean("dataDownloaderJob", Job.class);
            //JobLauncher jobLauncher = context.getBean(JobLauncher.class);
            String sDate = "01/01/2023";
            Date date = null;
            try{
                date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

            } catch (ParseException e){
                throw new RuntimeException(e);
            }
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("startDate",date)
                    .addLong("regionID",418L)
                    .addLong("random", Instant.now().toEpochMilli())
                    .toJobParameters();
            try{
                jobLauncher.run(job,jobParameters);
            } catch (JobExecutionAlreadyRunningException e) {
                throw new RuntimeException(e);
            } catch(JobRestartException e){
                throw new RuntimeException(e);
            } catch(JobInstanceAlreadyCompleteException e){
                throw new RuntimeException(e);
            } catch(JobParametersInvalidException e){
                throw new RuntimeException(e);
            }


        });
        return "Something is running";

    }



}