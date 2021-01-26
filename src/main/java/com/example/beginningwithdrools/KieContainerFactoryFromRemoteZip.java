package com.example.beginningwithdrools;

import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
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
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kie = kieServices.newKieFileSystem();
        kie.write("src/main/resources/decision.drl", new ResourceFactoryServiceImpl().newInputStreamResource(inputStream));

        KieRepository kieRepository = KieServices.get().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        kieServices.newKieBuilder(kie).buildAll();

        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }
}
