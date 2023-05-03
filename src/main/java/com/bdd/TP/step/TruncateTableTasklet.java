//package com.bdd.TP.step;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import javax.sql.DataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.StepExecutionListener;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//
////public class TruncateTableTasklet {
////}
//public class TruncateTableTasklet implements Tasklet, StepExecutionListener{
//    private static final Logger log = LoggerFactory.getLogger(TruncateTableTasklet.class);
//    private final DataSource dataSource;
////    private final String query;
//    private ExitStatus exitStatus;
//    public TruncateTableTasklet(DataSource ds) {
//        this.dataSource = ds;
////        this.query = query;
//    }
//    @Override
//    public void beforeStep(StepExecution stepExecution) {
//    }
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
//        try(Connection conn = this.dataSource.getConnection()){
//            try(PreparedStatement query = conn.prepareStatement(this.query)){
//                query.execute();
//                log.info("Table truncated :)");
//                this.exitStatus = new ExitStatus("ALLGOOD");
//            }
//        }catch (SQLException e) {
//            log.error("Truncate table failed!!" + e.toString());
//            this.exitStatus = new ExitStatus("TRUNCATEFAILED");
//        }
//        return RepeatStatus.FINISHED;
//    }
//    @Override
//    public ExitStatus afterStep(StepExecution stepExecution) {
//        return this.exitStatus;
//    }
//}
