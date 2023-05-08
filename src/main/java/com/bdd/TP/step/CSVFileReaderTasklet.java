package com.bdd.TP.step;

import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVFileReaderTasklet implements Tasklet, StepExecutionListener {

    private ExitStatus exitStatus;

    public CSVFileReaderTasklet() {
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {

        String csvFile = "medicion.csv";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] values = line.split(",");
                System.out.println(Arrays.toString(values));
            }
            br.close();
            System.out.println("->TERMINO");
            return RepeatStatus.FINISHED;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("->FALLO");
            return RepeatStatus.FINISHED;
        }
    }



    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return this.exitStatus;
    }

}
