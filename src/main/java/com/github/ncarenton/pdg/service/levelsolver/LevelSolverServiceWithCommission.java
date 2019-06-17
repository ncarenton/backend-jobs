package com.github.ncarenton.pdg.service.levelsolver;

import com.github.ncarenton.pdg.domain.Data;
import com.github.ncarenton.pdg.domain.Output;
import com.github.ncarenton.pdg.domain.Output.Commission;
import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import com.github.ncarenton.pdg.domain.StatusWorker;
import com.github.ncarenton.pdg.service.pay.CommissionService;
import com.github.ncarenton.pdg.service.pay.PayService;

import java.util.List;

import static com.github.ncarenton.pdg.service.pay.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER;

public final class LevelSolverServiceWithCommission extends LevelSolverService<StatusWorker> {

    private final PayService payService;

    private final CommissionService commissionService;

    public LevelSolverServiceWithCommission() {
        super(StatusWorker.class);
        this.payService = new PayService(WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER);
        this.commissionService = new CommissionService();
    }

    @Override
    protected Output compute(Data<StatusWorker> data) {

        List<WorkerPay> workerPays = payService.getWorkerPays(data.getWorkers(), data.getShifts());

        Commission commission = commissionService.getCommission(workerPays, data.getWorkers(), data.getShifts());

        return Output.builder()
                     .workers(workerPays)
                     .commission(commission)
                     .build();
    }

}
