package com.rr.sociable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SociableApplication {

    public static void main(String[] args) {
        SpringApplication.run(SociableApplication.class, args);
    }

}
