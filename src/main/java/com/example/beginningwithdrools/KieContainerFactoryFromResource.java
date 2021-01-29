package com.example.beginningwithdrools;

import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Qualifier;
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
        try {
            return getKieContainer(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
