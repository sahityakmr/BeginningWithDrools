package com.example.beginningwithdrools;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Qualifier("aws-zip")
@Component
public class KieContainerFactoryFromAWS extends KieContainerFactory {

    private final ConfigReader configReader;

    public KieContainerFactoryFromAWS(ConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public KieContainer getKieContainer() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAJCZG376FTPCUG3BA","SVptTcc3eorKax36jzfdySG7RsXUeirPH9gkyBrC");
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        S3Object zipObject = amazonS3.getObject(new GetObjectRequest("my-first-tour-bucket", "decision-rules.zip"));
        InputStream inputStream = getStreamFromZip(zipObject.getObjectContent());
        return getKieContainer(inputStream);
    }
}
