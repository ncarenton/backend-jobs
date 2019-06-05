package com.github.ncarenton.pdg.level1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Output {

    private final List<WorkerPay> workers;

    public Output(@JsonProperty("workers") List<WorkerPay> workers) {
        this.workers = workers;
    }

    @Value
    @Builder
    public static class WorkerPay {

        private final Long id;

        private final Integer price;

        public WorkerPay(@JsonProperty("id") Long id, @JsonProperty("price") Integer price) {
            this.id = id;
            this.price = price;
        }
    }
}
