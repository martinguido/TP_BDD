package com.bdd.TP.step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
//public class MailSenderTasklet {
//}
public class MailSenderTasklet implements Tasklet, StepExecutionListener{
    private static final Logger log = LoggerFactory.getLogger(MailSenderTasklet.class);
    @Override
    public void beforeStep(StepExecution stepExecution) {
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
        log.info("ENVIANDO CORREO PARA NOTIFICAR LA FALLA");
        return RepeatStatus.FINISHED;
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return new ExitStatus("EMAILSENT");
    }
}
