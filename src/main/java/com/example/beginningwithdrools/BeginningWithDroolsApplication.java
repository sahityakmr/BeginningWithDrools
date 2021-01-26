package com.example.beginningwithdrools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigReader.class)
public class BeginningWithDroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeginningWithDroolsApplication.class, args);
    }

}
