package com.bdd.TP.step;

import com.bdd.TP.dao.Medicion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ApiConsumerTasklet implements Tasklet, StepExecutionListener{

    private final RestTemplate restTemplate;

    private Long regionId;
    private Date startDate;

    private ExitStatus exitStatus;
    public ApiConsumerTasklet(RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;

    }
    @Override
    public void beforeStep(StepExecution stepExecution) {
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        System.out.println("CHECKPOINT 1");
//        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
//        List<HashMap<?,?>> data = restTemplate
//                .getForObject("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha=2023-04-01&id_region=418", List.class);
//        FileWriter fileWriter = new FileWriter(new File(  "/medicion.csv"));
//        if (data!=null) {
//            for (HashMap<?, ?> record : data) {
//                String csvRecord = String.format("%s, %s, %s, %s\n", "418", record.get("fecha"), record.get("dem"), record.get("temp"));
//                fileWriter.write(csvRecord);
//            }
//        }
        FileWriter fileWriter = new FileWriter(new File(  "/app/src/main/resources/medicion.csv"));
        fileWriter.write("HOLA");
        fileWriter.close();
//        System.out.println(data);
        return RepeatStatus.FINISHED;
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return this.exitStatus;
    }
}