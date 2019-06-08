package com.github.ncarenton.pdg.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerTest {

    @Test
    public void pricePerShift_works_without_status() {

        // Given
        Worker worker = new PriceWorker(1L, "Julie", 230);

        // Then
        assertThat(worker.getPricePerShift()).isEqualTo(230);
    }

    @Test
    public void pricePerShift_works_with_status() {

        // Given
        Worker worker = new StatusWorker(1L, "Julie", "medic");

        // Then
        assertThat(worker.getPricePerShift()).isEqualTo(270);
    }

    @Test(expected = IllegalArgumentException.class)
    public void pricePerShift_unknown_status_throws_exception() {

        // Given
        Worker worker = new StatusWorker(1L, "Julie", "unknown");

        // Then
        worker.getPricePerShift();
    }

}
