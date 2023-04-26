//package com.bdd.TP.config;
//
//import javax.sql.DataSource;
//import org.postgresql.ds.PGSimpleDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Value("${db.password}")
//    private String password;
//
//    @Value("${db.name}")
//    private String name;
//
//    @Value("${db.host}")
//    private String host;
//
//    @Value("${db.port}")
//    private int port;
//
//    @Value("${db.user}")
//    private String user;
//
//    @Bean
//    public DataSource dataSource() {
//        PGSimpleDataSource ds = new PGSimpleDataSource();
//        ds.setServerName(host);
//        ds.setDatabaseName(name);
//        ds.setUser(user);
//        ds.setPassword(password);
//        ds.setPortNumber(port);
//        return ds;
//    }
//}
