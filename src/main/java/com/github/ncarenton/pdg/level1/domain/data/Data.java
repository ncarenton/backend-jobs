package com.github.ncarenton.pdg.level1.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ncarenton.pdg.level1.domain.Output.WorkerPay;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public final class Data implements com.github.ncarenton.pdg.Data {

    private static final int DEFAULT_SHIFT_COUNT = 0;

    @Valid
    @NotNull
    private final Set<Worker> workers;

    @Valid
    @NotNull
    private final Set<Shift> shifts;

    public Data(
            @JsonProperty("workers") @Valid Set<Worker> workers,
            @JsonProperty("shifts") @Valid Set<Shift> shifts) {
        this.workers = workers;
        this.shifts = shifts;
    }

    public List<WorkerPay> getWorkersPays() {
        Map<Long, Integer> userIdToShiftCount = getShiftCountByWorker();

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

    private Map<Long, Integer> getShiftCountByWorker() {
        return shifts
                .stream()
                .collect(Collectors.groupingBy(Shift::getUserId, Collectors.reducing(0, e -> 1, Integer::sum)));
    }

}
