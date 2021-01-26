package com.example.beginningwithdrools;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class ConfigReader {

    private String zipLocation;

   /* @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard().withRegion("").build();
    }*/
}
