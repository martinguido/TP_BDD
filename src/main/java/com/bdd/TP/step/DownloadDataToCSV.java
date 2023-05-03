package com.bdd.TP.step;

import javax.sql.DataSource;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.service.CammesaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DownloadDataToCSV implements Tasklet, StepExecutionListener{
    private CammesaService cammesaService;
    private static final Logger log = LoggerFactory.getLogger(DownloadDataToCSV.class);
    private final DataSource dataSource;
    private ExitStatus exitStatus;
    public DownloadDataToCSV(DataSource ds, CammesaService cammesaService) {
        this.dataSource = ds;
        this.cammesaService = cammesaService;
    }
    @Override
    public void beforeStep(StepExecution stepExecution) {
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        List<Medicion> medicion  = cammesaService.demandaYTempertauraRegionPorFecha("2023-05-01", 418);
        FileWriter fileWriter = new FileWriter(new File(  "medicion.csv"));
        for (Medicion record : medicion) {
            String csvRecord =  record.getMyid() + ", " + record.getFecha() + ", " + record. getDemanda () + ", "+ record.getRegion() + ", "+ record.getTemperatura()  + "/n";
            fileWriter.write(csvRecord);
        }
        fileWriter.close();
        return RepeatStatus.FINISHED;
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return this.exitStatus;
    }
}
