package com.bdd.TP.step;

import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String contextDate = jobParameters.getString("startDate");
        Long contextIdRegion = jobParameters.getLong("regionID");
        try {
            String strRegion = contextIdRegion.toString();
            Instant instant = Instant.ofEpochMilli(Long.parseLong(contextDate));
            LocalDate fechaActual = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaLimite = fechaActual.plusMonths(1);
            List<List<HashMap<?,?>>> dataToCSV2= new ArrayList<>();
            while(fechaActual.isBefore(fechaLimite)){
                Date date = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
                String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println(strDate);
                List<HashMap<?, ?>> data = restTemplate
                        .getForObject("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha=" + strDate + "&id_region=" + strRegion, List.class);
                dataToCSV2.add(data);
                fechaActual = fechaActual.plusDays(1);
            }
            System.out.println(dataToCSV2);
            FileWriter fileWriter = new FileWriter(new File("medicion.csv"));
            if (dataToCSV2 != null) {
                for (List<HashMap<?,?>> lista1 :dataToCSV2){
                    for (HashMap<?, ?> elemento : lista1) {
                        String csvRecord = String.format("%s,%s,%s,%s\n", strRegion, elemento.get("fecha"), elemento.get("dem"), elemento.get("temp"));
                        fileWriter.write(csvRecord);
                    }
                }
            }
            fileWriter.close();
            System.out.println("TERMINO");
            return RepeatStatus.FINISHED;
        }
        catch (Exception e){
            System.out.println("FALLO");
            return RepeatStatus.FINISHED;
        }
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return this.exitStatus;
    }
}