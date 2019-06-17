package com.github.ncarenton.pdg.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
public final class Data<T extends Worker> {

    @Valid
    @NotNull
    private final Set<T> workers;

    @Valid
    @NotNull
    private final Set<Shift> shifts;

    public Data(
            @JsonProperty("workers") Set<T> workers,
            @JsonProperty("shifts") Set<Shift> shifts) {
        this.workers = workers;
        this.shifts = shifts;
    }

}
