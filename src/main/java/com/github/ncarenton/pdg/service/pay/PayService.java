package com.github.ncarenton.pdg.service.pay;

import com.github.ncarenton.pdg.domain.Output.WorkerPay;
import com.github.ncarenton.pdg.domain.Shift;
import com.github.ncarenton.pdg.domain.Worker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PayService {

    private final WeekendShiftPriceMultiplier weekendShiftPriceMultiplier;

    private static final int DEFAULT_SHIFT_PRICE_MULTIPLIER = 1;

    private static final Predicate<Shift> HAS_USER_ID = (shift) -> shift.getUserId() != null;

    public PayService(WeekendShiftPriceMultiplier weekendShiftPriceMultiplier) {
        this.weekendShiftPriceMultiplier = weekendShiftPriceMultiplier;
    }

    public List<WorkerPay> getWorkerPays(Set<? extends Worker> workers, Set<Shift> shifts) {
        Map<Long, Set<Shift>> shiftsByUserId = groupShiftsByWorkers(shifts);
        return workers
                .stream()
                .map(w -> getWorkerPay(shiftsByUserId.getOrDefault(w.getId(), Collections.emptySet()), w))
                .sorted(Comparator.comparing(WorkerPay::getId))
                .collect(Collectors.toList());
    }

    private Map<Long, Set<Shift>> groupShiftsByWorkers(Set<Shift> shifts) {
        return shifts
                .stream()
                .filter(HAS_USER_ID)
                .collect(Collectors.groupingBy(Shift::getUserId, Collectors.toSet()));
    }

    private WorkerPay getWorkerPay(Set<Shift> shifts, Worker worker) {
        return WorkerPay.builder()
                        .id(worker.getId())
                        .price(applyMultiplierAndSumShiftPrices(shifts, worker.getPricePerShift()))
                        .build();
    }

    private int applyMultiplierAndSumShiftPrices(Set<Shift> shifts, int pricePerShift) {
        return shifts.stream()
                     .map(s -> (pricePerShift * getShiftPriceMultiplier(s)))
                     .reduce(0, Integer::sum);
    }

    private int getShiftPriceMultiplier(Shift shift) {
        return shift.isOnWeekend() ? weekendShiftPriceMultiplier.priceMultiplier : DEFAULT_SHIFT_PRICE_MULTIPLIER;
    }

    public enum WeekendShiftPriceMultiplier {
        WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER(1),
        WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER(2);

        private final int priceMultiplier;

        WeekendShiftPriceMultiplier(int priceMultiplier) {
            this.priceMultiplier = priceMultiplier;
        }
    }

}
