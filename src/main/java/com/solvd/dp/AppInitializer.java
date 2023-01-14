package com.solvd.dp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AppInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }

}
