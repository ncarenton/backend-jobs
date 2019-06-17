package com.github.ncarenton.pdg;

import com.beust.jcommander.ParameterException;
import org.junit.Before;
import org.junit.Test;

public class LevelValidatorTest {

    private LevelValidator levelValidator;

    @Before
    public void setUp() {
        levelValidator = new LevelValidator();
    }

    @Test
    public void validate_should_work() {
        levelValidator.validate("level", "4");
    }

    @Test(expected = ParameterException.class)
    public void validate_should_throw_parameter_exception() {
        levelValidator.validate("level", "-1");
    }
}
