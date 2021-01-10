package com.example.beginningwithdrools;

import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class DroolsDecisionProvider {

    private final KieContainerSessionsPool sessionsPool;
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    public DroolsDecisionProvider() {
        pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        //KieContainer kieContainer = getKieContainerFromResource(getRuleFileResource());
        KieContainer kieContainer = getKieContainerFromInputStream(getRuleFileStream());
        sessionsPool = kieContainer.newKieSessionsPool(10);
    }

    private Resource getRuleFileResource() {
        return pathMatchingResourcePatternResolver.getResource("classpath:rules/decision-rules.drl");
    }

    private InputStream getRuleFileStream() {
        //return getStreamFromZip("classpath:rules/decision-rules.zip");
        return getStreamFromRemoteZip("D:\\Workspace\\OtherResources\\decision-rules.zip");
    }

    private InputStream getStreamFromRemoteZip(String location) {
        File file = new File(location);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            if (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                return zipFile.getInputStream(zipEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getStreamFromZip(String location) {
        Resource resource = pathMatchingResourcePatternResolver.getResource(location);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(resource.getFile());
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            if (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                return zipFile.getInputStream(zipEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KieContainer getKieContainerFromResource(Resource resource) {
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

    private KieContainer getKieContainerFromInputStream(InputStream inputStream) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kie = kieServices.newKieFileSystem();
        kie.write("src/main/resources/decision.drl", new ResourceFactoryServiceImpl().newInputStreamResource(inputStream));

        KieRepository kieRepository = KieServices.get().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        kieServices.newKieBuilder(kie).buildAll();

        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    public String getDecision(String param) {
        StatelessKieSession statelessKieSession = sessionsPool.newStatelessKieSession();
        List<Object> facts = new ArrayList<>();
        Result result = new Result();
        DecisionFact decisionFact = new DecisionFact();
        decisionFact.setValue1(getValue(param));
        facts.add(result);
        facts.add(decisionFact);
        statelessKieSession.execute(facts);
        return result.getResult();
    }

    private String getValue(String param) {
        switch (param.toCharArray()[0]) {
            case 'a':
                return "val1";
            case 'b':
                return "val2";
            case 'c':
                return "val3";
            case 'd':
                return "val4";
        }
        return "val5";
    }
}