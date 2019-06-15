package com.github.ncarenton.pdg.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@Value
public final class Shift {

    private static final Set<DayOfWeek> WEEKEND_DAYS = Set.of(SATURDAY, SUNDAY);

    @NotNull
    @Min(1)
    private final Long id;

    @NotNull
    @Min(1)
    private final Long planningId;

    @Min(1)
    private final Long userId;

    @NotNull
    private final LocalDate startDate;

    public Shift(@JsonProperty("id") Long id,
                 @JsonProperty("planning_id") Long planningId,
                 @JsonProperty("user_id") Long userId,
                 @JsonFormat(pattern = "yyyy-M-d")
                 @JsonProperty("start_date") LocalDate startDate) {
        this.id = id;
        this.planningId = planningId;
        this.userId = userId;
        this.startDate = startDate;
    }

    public boolean isOnWeekend() {
        return WEEKEND_DAYS.contains(startDate.getDayOfWeek());
    }
}
