package com.example.beginningwithdrools;

import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        Resource resource = getResourceFromPath("D:/Workspace/OtherResources/decision-rules.zip");
        InputStream inputStream = getStreamFromZip(resource);
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kie = kieServices.newKieFileSystem();
        kie.write("src/main/resources/decision.drl", new ResourceFactoryServiceImpl().newInputStreamResource(inputStream));

        KieRepository kieRepository = KieServices.get().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        kieServices.newKieBuilder(kie).buildAll();

        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }
}
