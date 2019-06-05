package com.github.ncarenton.pdg.service;

import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ncarenton.pdg.testutils.Fixtures.DATA;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_EMPTY_SHIFTS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_EMPTY_WORKERS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_MISSING_WORKER;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_EMPTY_SHIFTS;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_MISSING_WORKER;
import static org.assertj.core.api.Assertions.assertThat;

public class PayServiceTest {

    private PayService payService;

    @Before
    public void setUp() {
        payService = new PayService();
    }

    @Test
    public void getWorkerPays_should_work_default_data() {

        // When
        List<WorkerPay> workerPays = payService.getWorkerPays(
                DATA.getWorkers(),
                DATA.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_missing_worker() {

        // When
        List<WorkerPay> workerPays = payService.getWorkerPays(
                DATA_MISSING_WORKER.getWorkers(),
                DATA_MISSING_WORKER.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_MISSING_WORKER.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_empty_shifts() {

        // When
        List<WorkerPay> workerPays = payService.getWorkerPays(
                DATA_EMPTY_SHIFTS.getWorkers(),
                DATA_EMPTY_SHIFTS.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_EMPTY_SHIFTS.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_empty_workers() {

        // When
        List<WorkerPay> workerPays = payService.getWorkerPays(
                DATA_EMPTY_WORKERS.getWorkers(),
                DATA_EMPTY_WORKERS.getShifts());

        // Then
        assertThat(workerPays).isEmpty();
    }

    @Test
    public void getWorkerPays_should_work_duplicate_workers_duplicate_shifts() {

        // When
        List<WorkerPay> workerPays = payService.getWorkerPays(
                DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS.getWorkers(),
                DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT.getWorkers());
    }

}
