package com.example.beginningwithdrools;

import org.drools.core.io.impl.ResourceFactoryServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public abstract class KieContainerFactory {

    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    public KieContainerFactory() {
        pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    public abstract KieContainer getKieContainer();

    protected Resource getResourceFromPath(String location) {
        return pathMatchingResourcePatternResolver.getResource(location);
    }

    protected InputStream getStreamFromZip(File file) {
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

    protected InputStream getStreamFromZip(InputStream inputStream) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        try {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry != null)
                return zipInputStream;
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    protected InputStream getStreamFromZip(Resource resource) {
        try {
            return getStreamFromZip(resource.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected KieContainer getKieContainer(InputStream inputStream){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kie = kieServices.newKieFileSystem();
        kie.write("src/main/resources/decision.drl", new ResourceFactoryServiceImpl().newInputStreamResource(inputStream));

        KieRepository kieRepository = KieServices.get().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        kieServices.newKieBuilder(kie).buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }
}
