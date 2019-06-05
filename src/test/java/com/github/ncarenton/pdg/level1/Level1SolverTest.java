package com.github.ncarenton.pdg.level1;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.validation.ValidationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import static com.github.ncarenton.pdg.utils.TestUtils.getResourceAsString;
import static com.github.ncarenton.pdg.utils.TestUtils.getResourcePath;

public class Level1SolverTest {

    private Level1Solver level1Solver;

    @Before
    public void setUp() {
        level1Solver = new Level1Solver();
    }

    @Test(expected = InvalidFormatException.class)
    public void solve_should_throw_InvalidFormatException() throws IOException, URISyntaxException {

        // Given
        Path resourcePath = getResourcePath("/level1/data_invalid_field_wrong_type.json");

        // Then
        level1Solver.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(ByteArrayOutputStream::new));
    }

    @Test(expected = ValidationException.class)
    public void solve_should_throw_ValidationException() throws IOException, URISyntaxException {

        // Given
        Path resourcePath = getResourcePath("/level1/data_invalid_field_right_type.json");

        // Then
        level1Solver.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(ByteArrayOutputStream::new));
    }

    @Test
    public void solve_should_work() throws IOException, JSONException, URISyntaxException {

        //Given
        Path resourcePath = getResourcePath("/level1/data.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // When
        level1Solver.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        // Then
        JSONAssert.assertEquals(getResourceAsString("/level1/output.json"), outputStream.toString(), true);
    }

    private <T> Supplier<T> uncheck(StreamSupplier<T> ioSupplier) {
        return () -> {
            try {
                return ioSupplier.get();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    private interface StreamSupplier<T> {
        T get() throws IOException;
    }

}
