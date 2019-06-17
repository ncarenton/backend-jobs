package com.github.ncarenton.pdg.testutils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.ncarenton.pdg.domain.Data;
import com.github.ncarenton.pdg.domain.Output;
import com.github.ncarenton.pdg.domain.StatusWorker;
import com.github.ncarenton.pdg.domain.Worker;

import java.io.IOException;

import static com.github.ncarenton.pdg.testutils.TestUtils.getResourceAsStream;

public class Fixtures {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModules(new JavaTimeModule());

    public static Data<Worker> DATA_LEVEL1;
    public static Output OUTPUT_LEVEL1;

    public static Data<Worker> DATA_LEVEL2;
    public static Output OUTPUT_LEVEL2;

    public static Data<Worker> DATA_LEVEL3;
    public static Output OUTPUT_LEVEL3;

    public static Data<StatusWorker> DATA_LEVEL4;
    public static Output OUTPUT_LEVEL4;

    public static Data<Worker> DATA_MISSING_WORKER;
    public static Output OUTPUT_MISSING_WORKER;

    public static Data<Worker> DATA_EMPTY_SHIFTS;
    public static Output OUTPUT_EMPTY_SHIFTS;

    public static Data<Worker> DATA_EMPTY_WORKERS;

    public static Data<Worker> DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS;

    static {
        try {
            DATA_LEVEL1 = dataFomResource("/level1/data.json");

            OUTPUT_LEVEL1 = outputFromResource("/level1/output.json");

            DATA_LEVEL2 = dataFomResource("/level2/data.json");
            OUTPUT_LEVEL2 = outputFromResource("/level2/output.json");

            DATA_LEVEL3 = dataFomResource("/level3/data.json");
            OUTPUT_LEVEL3 = outputFromResource("/level3/output.json");

            DATA_LEVEL4 = dataFomResource("/level4/data.json");
            OUTPUT_LEVEL4 = outputFromResource("/level4/output.json");

            DATA_MISSING_WORKER = dataFomResource("/level1/data_missing_worker.json");
            OUTPUT_MISSING_WORKER = outputFromResource("/level1/output_missing_worker.json");

            DATA_EMPTY_SHIFTS = dataFomResource("/level1/data_empty_shifts.json");
            OUTPUT_EMPTY_SHIFTS = outputFromResource("/level1/output_empty_shifts.json");

            DATA_EMPTY_WORKERS = dataFomResource("/level1/data_empty_workers.json");

            DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS =
                    dataFomResource("/level1/data_duplicate_workers_duplicate_shifts.json");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private static Output outputFromResource(String resource) throws IOException {
        return OBJECT_MAPPER.readValue(getResourceAsStream(resource), Output.class);
    }

    private static <T extends Data> T dataFomResource(String resource) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructParametricType(Data.class, Worker.class);
        return OBJECT_MAPPER.readValue(getResourceAsStream(resource), type);
    }
}
