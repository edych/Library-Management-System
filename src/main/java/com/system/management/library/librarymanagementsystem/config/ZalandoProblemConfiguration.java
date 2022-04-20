package com.system.management.library.librarymanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

@Configuration
public class ZalandoProblemConfiguration {

    @Bean
    public ProblemModule problemModule() {
        return new ProblemModule();
    }
}
