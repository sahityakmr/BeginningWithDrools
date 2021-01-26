package com.example.beginningwithdrools;

import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Qualifier("resource")
@Component
public class KieContainerFactoryFromResource extends KieContainerFactory {
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    public KieContainerFactoryFromResource() {
        this.pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    @Override
    public KieContainer getKieContainer() {
        Resource resource = getResourceFromPath("classpath:rules/decision-rules.drl");
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kie = kieServices.newKieFileSystem();
        try {
            kie.write("src/main/resources/decision.drl", new ResourceFactoryServiceImpl().newInputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        KieRepository kieRepository = KieServices.get().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        kieServices.newKieBuilder(kie).buildAll();

        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }
}
