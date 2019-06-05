package com.github.ncarenton.pdg.utils;

import com.github.ncarenton.pdg.Application;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    public static InputStream getResourceAsStream(String name) throws FileNotFoundException {
        InputStream is = Application.class.getResourceAsStream(name);
        if (is == null) {
            throw new FileNotFoundException(name + " file not found in the classpath");
        }
        return is;
    }

    public static String getResourceAsString(String resource) throws IOException {
        return IOUtils.toString(getResourceAsStream(resource));
    }

    public static Path getResourcePath(String resource) throws URISyntaxException {
        URL url = Application.class.getResource(resource);
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        return Paths.get(url.toURI());
    }
}
