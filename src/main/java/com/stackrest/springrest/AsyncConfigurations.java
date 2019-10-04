package com.stackrest.springrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfigurations {

//    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfigurations.class);
//
//    @Bean(name = "taskExecutor")
//    public Executor taskExecutor() {
//        LOGGER.debug("Creating Async Task Executor");
//        System.out.println("Async is creating");
//        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(25);
//        executor.setThreadNamePrefix("QuestionsThread-");
//        executor.initialize();
//        return executor;
//    }
    private static final Logger log = LoggerFactory.getLogger(AsyncConfigurations.class);
    @Bean
    public Executor taskExecutor() {

//		LOGGER.debug("inside task Executor");
        log.info("inside task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Getrestdata-");
        executor.initialize();
        log.info(executor.getThreadNamePrefix());
        return executor;
    }
}
