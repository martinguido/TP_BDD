
package com.bdd.TP.controller;
import com.bdd.TP.job.DataDownloader;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
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
    private final Job dataDownloaderJob;

    public AsyncController(ConfigurableApplicationContext context, JobLauncher jobLauncher, Job dataDownloaderJob) {
        this.context = context;
        this.jobLauncher= jobLauncher;
        this. dataDownloaderJob = dataDownloaderJob;
    }




    @PostMapping("/cammesa/runBatch")
    public String runBatch(@RequestParam("fecha") String fecha, @RequestParam("regionID") Integer regionID) throws Exception {
        log.info(String.valueOf(Thread.currentThread()));
        CompletableFuture.runAsync(() ->{
//            String sDate = "01/01/2023";
            Date date = null;
            long regionIDLong = Integer.valueOf(regionID).longValue();

            try{
                date = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);

            } catch (ParseException e){
                throw new RuntimeException(e);
            }
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("startDate",date)
                    .addLong("regionID",regionIDLong)
                    .addLong("random", Instant.now().toEpochMilli())
                    .toJobParameters();
            try{
                jobLauncher.run(dataDownloaderJob,jobParameters);
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