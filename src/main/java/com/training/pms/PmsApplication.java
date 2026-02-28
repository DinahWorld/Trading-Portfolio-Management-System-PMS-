package com.training.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PmsApplication {
    private PmsApplication() {
        /* This utility class should not be instantiated */
    }

    static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
    }
}