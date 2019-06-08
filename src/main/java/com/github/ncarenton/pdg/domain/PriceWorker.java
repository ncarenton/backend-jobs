package com.github.ncarenton.pdg.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class PriceWorker extends Worker {

    @NotNull
    @Min(1)
    private final Integer pricePerShift;

    public PriceWorker(Long id,
                       String firstName,
                       Integer pricePerShift) {
        super(id, firstName);
        this.pricePerShift = pricePerShift;
    }

    @Override
    public int getPricePerShift() {
        return pricePerShift;
    }

}

