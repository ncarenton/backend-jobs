package com.github.ncarenton.pdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Slf4j
public abstract class LevelSolver<D extends Data, O> {

    private final ObjectMapper objectMapper;

    private final Validator validator;

    private final Class<D> dataClazz;

    private final Class<O> outputClazz;

    public LevelSolver(Class<D> dataClazz, Class<O> outputClazz) {
        objectMapper = new ObjectMapper().registerModules(new JavaTimeModule());
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.dataClazz = dataClazz;
        this.outputClazz = outputClazz;
    }

    private D deserialize(InputStream inputStream, Class<D> clazz) throws IOException {
        return objectMapper.readValue(inputStream, clazz);
    }

    private void validate(D data) {
        Set<ConstraintViolation<D>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }

    private O compute(D data)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return outputClazz.getConstructor(List.class).newInstance(data.getWorkersPays());
    }

    private String serialize(O output) throws JsonProcessingException {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(output);
    }

    private void write(OutputStream outputStream, String output) throws IOException {
        outputStream.write(output.getBytes());
    }

    public void solve(Supplier<InputStream> inputStream, Supplier<OutputStream> outputStream)
            throws InvalidFormatException {
        try {
            log.info("Data: \n" + IOUtils.toString(inputStream.get()));

            D data = deserialize(inputStream.get(), dataClazz);
            validate(data);
            O output = compute(data);
            String json = serialize(output);
            write(outputStream.get(), json);

            log.info("Output: \n" + json);
        } catch (ValidationException | InvalidFormatException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while running solver: " + e);
        }
    }
}
