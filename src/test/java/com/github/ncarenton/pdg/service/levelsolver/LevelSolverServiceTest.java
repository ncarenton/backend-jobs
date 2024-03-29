package com.github.ncarenton.pdg.service.levelsolver;

import com.github.ncarenton.pdg.testutils.TestAppender;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import static com.github.ncarenton.pdg.testutils.TestUtils.getResourceAsString;
import static com.github.ncarenton.pdg.testutils.TestUtils.getResourcePath;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelSolverServiceTest {

    private LevelSolverServiceWithNoCommission levelSolverServiceForSimpleWeekendFee;

    private LevelSolverServiceWithNoCommission levelSolverServiceForDoubleWeekendFee;

    private LevelSolverServiceWithCommission levelSolverServiceWithCommissionComputation;

    @Before
    public void setUp() {

        levelSolverServiceForSimpleWeekendFee = LevelSolverService.forSimpleWeekendFee();
        levelSolverServiceForDoubleWeekendFee = LevelSolverService.forDoubleWeekendFee();
        levelSolverServiceWithCommissionComputation = LevelSolverService.withCommission();

        TestAppender.clear();
    }

    @Test
    public void solve_should_log_error_if_InvalidFormatException() throws URISyntaxException {

        // Given
        Path resourcePath = getResourcePath("/level1/data_invalid_field_wrong_type.json");

        // When
        levelSolverServiceForSimpleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(ByteArrayOutputStream::new));

        //Then
        assertThat(TestAppender.messages)
                .isNotNull()
                .hasSize(2)
                .extracting("level")
                .contains("ERROR");
    }

    @Test
    public void solve_should_log_error_if_ValidationException_invalid_field() throws URISyntaxException {

        // Given
        Path resourcePath = getResourcePath("/level1/data_invalid_field_right_type.json");

        // When
        levelSolverServiceForSimpleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(ByteArrayOutputStream::new));

        //Then
        assertThat(TestAppender.messages)
                .isNotNull()
                .hasSize(2)
                .extracting("level")
                .contains("ERROR");
    }

    @Test
    public void solve_should_log_error_if_ValidationException_invalid_enum_field() throws URISyntaxException {

        // Given
        Path resourcePath = getResourcePath("/level2/data_invalid_enum_field_right_type.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // Then
        levelSolverServiceForSimpleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        //Then
        assertThat(outputStream.toString()).isEmpty();
        assertThat(TestAppender.messages)
                .isNotNull()
                .hasSize(2)
                .extracting("level")
                .contains("ERROR");
    }

    @Test
    public void solve_should_work_level1() throws IOException, JSONException, URISyntaxException {

        //Given
        Path resourcePath = getResourcePath("/level1/data.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // When
        levelSolverServiceForSimpleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        // Then
        JSONAssert.assertEquals(getResourceAsString("/level1/output.json"), outputStream.toString(), true);
    }

    @Test
    public void solve_should_work_level2() throws IOException, JSONException, URISyntaxException {

        //Given
        Path resourcePath = getResourcePath("/level2/data.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // When
        levelSolverServiceForSimpleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        // Then
        JSONAssert.assertEquals(getResourceAsString("/level2/output.json"), outputStream.toString(), true);
    }

    @Test
    public void solve_should_work_level3() throws IOException, JSONException, URISyntaxException {

        //Given
        Path resourcePath = getResourcePath("/level3/data.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // When
        levelSolverServiceForDoubleWeekendFee.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        // Then
        getResourceAsString("/level3/output.json");
        JSONAssert.assertEquals(getResourceAsString("/level3/output.json"), outputStream.toString(), true);
    }

    @Test
    public void solve_should_work_level4() throws IOException, JSONException, URISyntaxException {

        //Given
        Path resourcePath = getResourcePath("/level4/data.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        // When
        levelSolverServiceWithCommissionComputation.solve(
                uncheck(() -> Files.newInputStream(resourcePath)),
                uncheck(() -> outputStream));

        // Then
        JSONAssert.assertEquals(getResourceAsString("/level4/output.json"), outputStream.toString(), true);
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
