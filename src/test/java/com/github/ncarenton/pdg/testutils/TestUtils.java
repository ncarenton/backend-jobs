package com.github.ncarenton.pdg.testutils;

import com.github.ncarenton.pdg.Application;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TestUtils {

    public static InputStream getResourceAsStream(String name) throws FileNotFoundException {
        Optional<InputStream> is = Optional.ofNullable(Application.class.getResourceAsStream(name));
        return is.orElseThrow(FileNotFoundException::new);
    }

    public static String getResourceAsString(String resource) throws IOException {
        return IOUtils.toString(getResourceAsStream(resource));
    }

    public static Path getResourcePath(String resource) throws URISyntaxException {
        Optional<URL> url = Optional.ofNullable(Application.class.getResource(resource));
        return Paths.get(url.orElseThrow(NullPointerException::new).toURI());
    }

}
