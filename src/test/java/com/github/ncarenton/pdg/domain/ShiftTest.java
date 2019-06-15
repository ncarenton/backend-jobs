package com.github.ncarenton.pdg.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShiftTest {

    @Parameter
    public LocalDate localDate;

    @Parameter(1)
    public boolean expected;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {LocalDate.of(2018, 8, 27), false}, // Monday
                {LocalDate.of(2018, 8, 28), false}, // Tuesday
                {LocalDate.of(2018, 8, 29), false}, // Wednesday
                {LocalDate.of(2018, 8, 30), false}, // Thursday
                {LocalDate.of(2018, 8, 31), false}, // Friday
                {LocalDate.of(2018, 9, 1), true}, // Saturday
                {LocalDate.of(2018, 9, 2), true}, // Sunday
        });
    }

    @Test
    public void isOnWeekend_should_work() {

        // Given
        Shift shift = new Shift(1L, 1L, 1L, localDate);

        // Then
        assertEquals(expected, shift.isOnWeekend());
    }
}
