package com.github.ncarenton.pdg.utils;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.github.ncarenton.pdg.domain.Data;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT;
import static com.github.ncarenton.pdg.testutils.TestUtils.getResourceAsStream;
import static com.github.ncarenton.pdg.testutils.TestUtils.getResourceAsString;
import static com.github.ncarenton.pdg.utils.IOUtils.deserialize;
import static com.github.ncarenton.pdg.utils.IOUtils.serialize;
import static com.github.ncarenton.pdg.utils.IOUtils.writeToOutputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {

    @Test
    public void deserialize_should_work() throws IOException {

        //Given
        InputStream is = getResourceAsStream("/level1/data.json");

        //When
        Data data = deserialize(is, Data.class);

        //Then
        assertThat(data).isInstanceOf(Data.class);
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void deserialize_should_throw_UnrecognizedPropertyException() throws IOException {

        //Given
        InputStream is = getResourceAsStream("/level1/output.json");

        //When
        Data data = deserialize(is, Data.class);

        //Then
        assertThat(data).isInstanceOf(Data.class);
    }

    @Test
    public void serialize_should_work() throws IOException, JSONException {

        //When
        String serialized = serialize(OUTPUT);

        //Then
        JSONAssert.assertEquals(getResourceAsString("/level1/output.json"), serialized, true);
    }

    @Test
    public void writeToOutputStream_should_work() throws IOException {

        //Given
        OutputStream outputStream = new ByteArrayOutputStream();

        //When
        writeToOutputStream(outputStream, getResourceAsString("/level1/data.json"));

        //Then
        assertThat(outputStream.toString()).isEqualTo(getResourceAsString("/level1/data.json"));
    }
}
