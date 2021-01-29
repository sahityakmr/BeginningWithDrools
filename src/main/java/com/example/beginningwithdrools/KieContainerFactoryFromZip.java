package com.example.beginningwithdrools;

import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Qualifier("resource-zip")
@Component
public class KieContainerFactoryFromZip extends KieContainerFactory {

    private final ConfigReader configReader;

    public KieContainerFactoryFromZip(ConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public KieContainer getKieContainer() {
        //Resource resource = getResourceFromPath(configReader.getZipLocation());
        Resource resource = getResourceFromPath("classpath:rules/decision-rules.zip");
        InputStream inputStream = getStreamFromZip(resource);
        return getKieContainer(inputStream);
    }
}
