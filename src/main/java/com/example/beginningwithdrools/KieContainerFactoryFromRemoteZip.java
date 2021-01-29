package com.example.beginningwithdrools;

import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Qualifier("remote-zip")
@Component
public class KieContainerFactoryFromRemoteZip extends KieContainerFactory {

    private final ConfigReader configReader;

    public KieContainerFactoryFromRemoteZip(ConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public KieContainer getKieContainer() {
        //Resource resource = getResourceFromPath(configReader.getZipLocation());
        File file = new File("D:/Workspace/OtherResources/decision-rules.zip");
        InputStream inputStream = getStreamFromZip(file);
        return getKieContainer(inputStream);
    }
}
