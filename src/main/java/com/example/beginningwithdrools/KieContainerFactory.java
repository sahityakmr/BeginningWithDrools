package com.example.beginningwithdrools;

import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class KieContainerFactory {

    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    public KieContainerFactory() {
        pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    public abstract KieContainer getKieContainer();

    protected Resource getResourceFromPath(String location){
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

    protected InputStream getStreamFromZip(Resource resource) {
        try {
            return getStreamFromZip(resource.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
