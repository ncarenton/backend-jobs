package com.github.ncarenton.pdg.service.pay;

import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ncarenton.pdg.service.pay.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.service.pay.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_EMPTY_SHIFTS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_EMPTY_WORKERS;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_LEVEL1;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_LEVEL2;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_LEVEL3;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_LEVEL4;
import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_MISSING_WORKER;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_EMPTY_SHIFTS;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_LEVEL1;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_LEVEL2;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_LEVEL3;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_LEVEL4;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_MISSING_WORKER;
import static org.assertj.core.api.Assertions.assertThat;

public class PayServiceTest {

    private PayService payServiceSimpleWeekendFee;

    private PayService payServiceDoubleWeekendFee;

    @Before
    public void setUp() {
        payServiceSimpleWeekendFee = new PayService(WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER);
        payServiceDoubleWeekendFee = new PayService(WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER);
    }

    @Test
    public void getWorkerPays_should_work_default_data_level1() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_LEVEL1.getWorkers(),
                DATA_LEVEL1.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_LEVEL1.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_default_data_level2() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_LEVEL2.getWorkers(),
                DATA_LEVEL2.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_LEVEL2.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_default_data_level3() {

        // When
        List<WorkerPay> workerPays = payServiceDoubleWeekendFee.getWorkerPays(
                DATA_LEVEL3.getWorkers(),
                DATA_LEVEL3.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_LEVEL3.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_default_data_level4() {

        // When
        List<WorkerPay> workerPays = payServiceDoubleWeekendFee.getWorkerPays(
                DATA_LEVEL4.getWorkers(),
                DATA_LEVEL4.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_LEVEL4.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_missing_worker() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_MISSING_WORKER.getWorkers(),
                DATA_MISSING_WORKER.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_MISSING_WORKER.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_empty_shifts() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_EMPTY_SHIFTS.getWorkers(),
                DATA_EMPTY_SHIFTS.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_EMPTY_SHIFTS.getWorkers());
    }

    @Test
    public void getWorkerPays_should_work_empty_workers() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_EMPTY_WORKERS.getWorkers(),
                DATA_EMPTY_WORKERS.getShifts());

        // Then
        assertThat(workerPays).isEmpty();
    }

    @Test
    public void getWorkerPays_should_work_duplicate_workers_duplicate_shifts() {

        // When
        List<WorkerPay> workerPays = payServiceSimpleWeekendFee.getWorkerPays(
                DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS.getWorkers(),
                DATA_DUPLICATE_SHIFTS_DUPLICATE_WORKERS.getShifts());

        // Then
        assertThat(workerPays).isEqualTo(OUTPUT_LEVEL1.getWorkers());
    }

}
