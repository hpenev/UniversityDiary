package com.example.demo.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class LiquibaseRunner {

    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        try {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
            liquibase.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase", e);
        }
    }
}