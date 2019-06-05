package com.github.ncarenton.pdg.service;

import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import com.github.ncarenton.pdg.domain.Shift;
import com.github.ncarenton.pdg.domain.Worker;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class PayService {

    private static final int DEFAULT_SHIFT_COUNT = 0;

    public List<WorkerPay> getWorkerPays(Set<Worker> workers, Set<Shift> shifts) {
        Map<Long, Integer> userIdToShiftCount = getShiftCountByWorker(shifts);

        return workers
                .stream()
                .map(w -> getWorkerPay(userIdToShiftCount.getOrDefault(w.getId(), DEFAULT_SHIFT_COUNT), w))
                .sorted(Comparator.comparing(WorkerPay::getId))
                .collect(Collectors.toList());
    }

    private WorkerPay getWorkerPay(int shiftCount, Worker worker) {
        return WorkerPay.builder()
                        .id(worker.getId())
                        .price(shiftCount * worker.getPricePerShift())
                        .build();
    }

    private Map<Long, Integer> getShiftCountByWorker(Set<Shift> shifts) {
        return shifts
                .stream()
                .collect(Collectors.groupingBy(Shift::getUserId, Collectors.reducing(0, e -> 1, Integer::sum)));
    }
}
