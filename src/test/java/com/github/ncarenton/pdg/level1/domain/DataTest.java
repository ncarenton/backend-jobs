package com.github.ncarenton.pdg.level1.domain;

import com.github.ncarenton.pdg.level1.Fixtures;
import org.junit.Test;

import static com.github.ncarenton.pdg.level1.Fixtures.DATA;
import static com.github.ncarenton.pdg.level1.Fixtures.DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS;
import static com.github.ncarenton.pdg.level1.Fixtures.DATA_EMPTY_SHIFTS;
import static com.github.ncarenton.pdg.level1.Fixtures.DATA_EMPTY_WORKERS;
import static com.github.ncarenton.pdg.level1.Fixtures.DATA_MISSING_WORKER;
import static com.github.ncarenton.pdg.level1.Fixtures.OUTPUT;
import static com.github.ncarenton.pdg.level1.Fixtures.OUTPUT_MISSING_WORKER;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class DataTest {

    @Test
    public void getWorkerPays_should_work_default_data() {

        // When
        Output output = Output.builder()
                              .workers(DATA.getWorkersPays())
                              .build();

        // Then
        assertThat(output).isEqualTo(OUTPUT);
    }

    @Test
    public void getWorkerPays_should_work_missing_worker() {

        // When
        Output output = Output.builder()
                              .workers(DATA_MISSING_WORKER.getWorkersPays())
                              .build();

        // Then
        assertThat(output).isEqualTo(OUTPUT_MISSING_WORKER);
    }

    @Test
    public void getWorkerPays_should_work_empty_shifts() {

        // When
        Output output = Output.builder()
                              .workers(DATA_EMPTY_SHIFTS.getWorkersPays())
                              .build();

        // Then
        assertThat(output).isEqualTo(Fixtures.OUTPUT_EMPTY_SHIFTS);
    }

    @Test
    public void getWorkerPays_should_work_empty_workers() {

        // When
        Output output = Output.builder()
                              .workers(DATA_EMPTY_WORKERS.getWorkersPays())
                              .build();

        // Then
        assertThat(output.getWorkers()).isEmpty();
    }

    @Test
    public void getWorkerPays_should_work_duplicate_workers_duplicate_shifts() {

        // When
        Output output = Output.builder()
                              .workers(DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS.getWorkersPays())
                              .build();

        // Then
        assertThat(output).isEqualTo(OUTPUT);
    }

}
