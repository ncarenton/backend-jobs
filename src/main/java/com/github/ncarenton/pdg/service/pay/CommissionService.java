package com.github.ncarenton.pdg.service.pay;

import com.github.ncarenton.pdg.domain.Output.Commission;
import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import com.github.ncarenton.pdg.domain.Shift;
import com.github.ncarenton.pdg.domain.StatusWorker;
import com.github.ncarenton.pdg.domain.Worker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.ncarenton.pdg.domain.StatusWorker.Status.INTERIM;

public class CommissionService {

    private static final BigDecimal COMMISSION = BigDecimal.valueOf(0.05);

    private static final int INTERIM_SHIFT_FIXED_FEE = 80;

    public Commission getCommission(List<WorkerPay> workerPays, Set<StatusWorker> statusWorkers, Set<Shift> shifts) {

        int interimShiftsCount = getInterimShiftsCount(statusWorkers, shifts);
        BigDecimal pdgFee = getPdgFee(workerPays, interimShiftsCount);

        return new Commission(pdgFee, interimShiftsCount);
    }

    private int getInterimShiftsCount(Set<StatusWorker> statusWorkers, Set<Shift> shifts) {
        Set<Long> interimWorkersIds = getInterimWorkersIds(statusWorkers);
        return countInterimWorkersShifts(interimWorkersIds, shifts);
    }

    private Set<Long> getInterimWorkersIds(Set<StatusWorker> statusWorkers) {
        return statusWorkers
                .stream()
                .filter(w -> w.getStatus().equals(INTERIM))
                .map(Worker::getId)
                .collect(Collectors.toSet());
    }

    private int countInterimWorkersShifts(Set<Long> interimWorkersIds, Set<Shift> shifts) {
        return shifts
                .stream()
                .filter(s -> interimWorkersIds.contains(s.getUserId()))
                .map(e -> 1).reduce(0, Integer::sum);
    }

    private BigDecimal getPdgFee(List<WorkerPay> workerPays, int interimShiftsCount) {
        return sumShiftsAndApplyCommission(workerPays)
                .add(applyFixedFeeToInterimShifts(interimShiftsCount));
    }

    private BigDecimal sumShiftsAndApplyCommission(List<WorkerPay> workerPays) {
        return workerPays
                .stream()
                .map(wp -> BigDecimal.valueOf(wp.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(COMMISSION);
    }

    private BigDecimal applyFixedFeeToInterimShifts(int interimShiftsCount) {
        return BigDecimal.valueOf(interimShiftsCount * INTERIM_SHIFT_FIXED_FEE);
    }

}
