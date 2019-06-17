package com.github.ncarenton.pdg;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class LevelValidator implements IParameterValidator {

    public void validate(String name, String value) throws ParameterException {
        int n = Integer.parseInt(value);
        if (n < 1 || n > 4) {
            throw new ParameterException("Parameter " + name
                                         + " should be between 1 and 4");
        }
    }

}
