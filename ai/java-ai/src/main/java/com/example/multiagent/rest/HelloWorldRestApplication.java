package com.example.multiagent.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.multiagent")
public class HelloWorldRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldRestApplication.class, args);
    }
}
