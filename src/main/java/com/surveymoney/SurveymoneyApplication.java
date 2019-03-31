package com.surveymoney;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SurveymoneyApplication {
    private static Logger logger = LogManager.getLogger(SurveymoneyApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Spring Boot SurveymoneyApplication");
        SpringApplication.run(SurveymoneyApplication.class, args);
    }

}

