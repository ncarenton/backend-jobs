package com.github.ncarenton.pdg.level1;

import com.github.ncarenton.pdg.LevelSolver;
import com.github.ncarenton.pdg.level1.domain.Output;
import com.github.ncarenton.pdg.level1.domain.data.Data;

public class Level1Solver extends LevelSolver<Data, Output> {

    public Level1Solver() {
        super(Data.class, Output.class);
    }

}
