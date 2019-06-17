package com.github.ncarenton.pdg.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .setSerializationInclusion(Include.NON_NULL);

    public static <T> T deserialize(InputStream inputStream, Class<T> clazz) throws IOException {
        return MAPPER.readValue(inputStream, clazz);
    }

    public static <T> T deserialize(InputStream inputStream, TypeReference typeReference) throws IOException {
        return MAPPER.readValue(inputStream, typeReference);

    }

    public static <T> String serialize(T output) throws JsonProcessingException {
        return MAPPER
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(output);
    }

    public static void writeToOutputStream(OutputStream outputStream, String output) throws IOException {
        outputStream.write(output.getBytes());
    }
}
