package com.github.ncarenton.pdg;

import com.github.ncarenton.pdg.level1.domain.Output.WorkerPay;

import java.util.List;

public interface Data {
    List<WorkerPay> getWorkersPays();
}
