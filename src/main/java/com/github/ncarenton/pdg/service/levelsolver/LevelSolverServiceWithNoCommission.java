package com.github.ncarenton.pdg.service.levelsolver;

import com.github.ncarenton.pdg.domain.Data;
import com.github.ncarenton.pdg.domain.Output;
import com.github.ncarenton.pdg.domain.Worker;
import com.github.ncarenton.pdg.service.pay.PayService;

public class LevelSolverServiceWithNoCommission extends LevelSolverService<Worker> {

    private final PayService payService;

    public LevelSolverServiceWithNoCommission(PayService payService) {
        super(Worker.class);
        this.payService = payService;
    }

    @Override
    protected Output compute(Data<Worker> data) {
        return Output.builder()
                     .workers(payService.getWorkerPays(data.getWorkers(), data.getShifts()))
                     .build();
    }
}
