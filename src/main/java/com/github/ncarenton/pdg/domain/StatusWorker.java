package com.github.ncarenton.pdg.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;

@Value
@EqualsAndHashCode(callSuper = true)
public final class StatusWorker extends Worker {

    @NotNull
    @Pattern(regexp = "medic|interne")
    private final String status;

    public StatusWorker(Long id,
                        String firstName,
                        String status) {
        super(id, firstName);
        this.status = status;
    }

    @Override
    public int getPricePerShift() {
        return Status.lookup(status).pricePerShift;
    }

    private enum Status {
        MEDIC(270),
        INTERNE(126);

        private final int pricePerShift;

        Status(int pricePerShift) {
            this.pricePerShift = pricePerShift;
        }

        public static Status lookup(String status) {
            return Arrays.stream(Status.values())
                         .filter(s -> s.name().equalsIgnoreCase(status))
                         .findFirst()
                         .orElseThrow(IllegalArgumentException::new);
        }
    }

}

