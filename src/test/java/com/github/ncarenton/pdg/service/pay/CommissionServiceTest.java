package com.github.ncarenton.pdg.service.pay;

import com.github.ncarenton.pdg.domain.Output.Commission;
import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ncarenton.pdg.testutils.Fixtures.DATA_LEVEL4;
import static com.github.ncarenton.pdg.testutils.Fixtures.OUTPUT_LEVEL4;
import static org.assertj.core.api.Assertions.assertThat;

public class CommissionServiceTest {

    private CommissionService commissionService;

    @Before
    public void setUp() {
        commissionService = new CommissionService();
    }

    @Test
    public void getCommission_should_work_default_data_level4() {

        // Given
        List<WorkerPay> workerPays = OUTPUT_LEVEL4.getWorkers();

        // When
        Commission commission = commissionService.getCommission(
                workerPays,
                DATA_LEVEL4.getWorkers(),
                DATA_LEVEL4.getShifts());

        // Then
        assertThat(commission).isEqualTo(OUTPUT_LEVEL4.getCommission());
    }
}
