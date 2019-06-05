package com.github.ncarenton.pdg;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.github.ncarenton.pdg.service.LevelSolverService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

@Slf4j
public final class Application {

    @Parameter(
            names = {"--input-inputFile", "-i"},
            required = true,
            description = "File to read data from")
    private Path inputFile;

    @Parameter(
            names = {"--output-inputFile", "-o"},
            required = true,
            description = "File to writeToOutputStream output to")
    private Path outputFile;

    private Application(String[] args) {

        JCommander jCommander = new JCommander(this);

        try {
            jCommander.parse(args);
            jCommander.setProgramName("planning-de-garde");
        } catch (ParameterException pe) {
            log.error("Parsing error: " + pe.getLocalizedMessage());
            jCommander.usage();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Application(args).start();
    }

    private void start() {
        try {
            new LevelSolverService().solve(
                    uncheck(() -> Files.newInputStream(inputFile, READ)),
                    uncheck(() -> Files.newOutputStream(outputFile, CREATE, TRUNCATE_EXISTING, WRITE))
            );
        } catch (Exception e) {
            log.error("Error while running application: " + e);
        }
    }

    private <T> Supplier<T> uncheck(IOSupplier<T> ioSupplier) {
        return () -> {
            try {
                return ioSupplier.get();
            } catch (IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        };
    }

    private interface IOSupplier<T> {
        T get() throws IOException;
    }

}
