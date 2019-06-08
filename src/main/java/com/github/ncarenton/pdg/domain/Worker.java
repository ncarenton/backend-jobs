package com.github.ncarenton.pdg.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Worker {

    @NotNull
    @Min(1)
    private final Long id;

    @NotBlank
    private final String firstName;

    @JsonCreator
    public static Worker create(@JsonProperty("id") Long id,
                                @JsonProperty("first_name") String firstName,
                                @JsonProperty("price_per_shift") Integer pricePerShift,
                                @JsonProperty("status") String status) {
        return status != null
               ? new StatusWorker(id, firstName, status) : new PriceWorker(id, firstName, pricePerShift);
    }

    public abstract int getPricePerShift();

}

