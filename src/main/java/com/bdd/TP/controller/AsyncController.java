
package com.bdd.TP.controller;

import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
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
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api")
public class AsyncController {
    private final ConfigurableApplicationContext context;
    private static final Logger log = LoggerFactory.getLogger(AsyncController.class);
    private final JobLauncher jobLauncher;
    private final Job dataDownloaderJob;
    private final MedicionService medicionService;
    private final RegionService regionService;
    private final CammesaService cammesaService;

    public AsyncController(ConfigurableApplicationContext context, JobLauncher jobLauncher, Job dataDownloaderJob, MedicionService medicionService, RegionService regionService, CammesaService cammesaService) {
        this.context = context;
        this.jobLauncher= jobLauncher;
        this.dataDownloaderJob = dataDownloaderJob;
        this.medicionService = medicionService;
        this.regionService = regionService;
        this.cammesaService = cammesaService;
    }
    @PostMapping("/demandaYTemperaturaMensual")
    public String demandaYTemperaturaMensual(@RequestParam("fecha") String fecha, @RequestParam("regionID") Integer regionID) throws Exception {
        medicionService.deleteAll();
        regionService.deleteAllRegions();
        regionService.saveRegiones(cammesaService.actualizarRegiones());
        log.info(String.valueOf(Thread.currentThread()));
        CompletableFuture.runAsync(() ->{
            Date date = null;
            long regionIDLong = Integer.valueOf(regionID).longValue();
            try{
                date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
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
            } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                     JobInstanceAlreadyCompleteException | JobRestartException e) {
                throw new RuntimeException(e);
            }
        });
        return "El batch esta corriendo!";
    }
}