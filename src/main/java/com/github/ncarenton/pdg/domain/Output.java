package com.github.ncarenton.pdg.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class Output {

    private final List<WorkerPay> workers;

    private final Commission commission;

    public Output(
            @JsonProperty("workers") List<WorkerPay> workers,
            @JsonProperty("commission") Commission commission) {
        this.workers = workers;
        this.commission = commission;
    }

    @Value
    @Builder
    public static class WorkerPay {

        private final Long id;

        private final Integer price;

        public WorkerPay(
                @JsonProperty("id") Long id,
                @JsonProperty("price") Integer price) {
            this.id = id;
            this.price = price;
        }
    }

    @Value
    @Builder
    public static class Commission {

        @JsonProperty("pdg_fee")
        private final BigDecimal pdgFee;

        @JsonProperty("interim_shifts")
        private final Integer interimShifts;

        public Commission(
                @JsonProperty("pdg_fee") BigDecimal pdgFee,
                @JsonProperty("interim_shifts") Integer interimShifts) {
            this.pdgFee = pdgFee;
            this.interimShifts = interimShifts;
        }
    }
}
