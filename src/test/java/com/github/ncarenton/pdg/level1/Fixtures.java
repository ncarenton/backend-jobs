package com.github.ncarenton.pdg.level1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.ncarenton.pdg.level1.domain.Output;
import com.github.ncarenton.pdg.level1.domain.data.Data;

import java.io.IOException;

import static com.github.ncarenton.pdg.utils.TestUtils.getResourceAsStream;

public class Fixtures {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModules(new JavaTimeModule());

    public static Data DATA;
    public static Output OUTPUT;

    public static Data DATA_MISSING_WORKER;
    public static Output OUTPUT_MISSING_WORKER;

    public static Data DATA_EMPTY_SHIFTS;
    public static Output OUTPUT_EMPTY_SHIFTS;

    public static Data DATA_EMPTY_WORKERS;

    public static Data DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS;

    static {
        try {
            DATA = fromResource("/level1/data.json", Data.class);
            OUTPUT = fromResource("/level1/output.json", Output.class);

            DATA_MISSING_WORKER = fromResource("/level1/data_missing_worker.json", Data.class);
            OUTPUT_MISSING_WORKER = fromResource("/level1/output_missing_worker.json", Output.class);

            DATA_EMPTY_SHIFTS = fromResource("/level1/data_empty_shifts.json", Data.class);
            OUTPUT_EMPTY_SHIFTS = fromResource("/level1/output_empty_shifts.json", Output.class);

            DATA_EMPTY_WORKERS = fromResource("/level1/data_empty_workers.json", Data.class);

            DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS =
                    fromResource("/level1/data_duplicate_workers_duplicate_shifts.json", Data.class);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private static <T> T fromResource(String resource, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(getResourceAsStream(resource), clazz);
    }
}
