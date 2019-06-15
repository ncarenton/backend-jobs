package com.github.ncarenton.pdg.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.ncarenton.pdg.domain.Data;
import com.github.ncarenton.pdg.domain.Output;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.function.Supplier;

import static com.github.ncarenton.pdg.service.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.service.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.utils.IOUtils.deserialize;
import static com.github.ncarenton.pdg.utils.IOUtils.serialize;
import static com.github.ncarenton.pdg.utils.IOUtils.writeToOutputStream;

@Slf4j
public final class LevelSolverService {

    private final Validator validator;

    private final PayService payService;

    private LevelSolverService(PayService payService) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.payService = payService;
    }

    public static LevelSolverService forSimpleWeekendFee() {
        return new LevelSolverService(new PayService(WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER));
    }

    public static LevelSolverService forDoubleWeekendFee() {
        return new LevelSolverService(new PayService(WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER));
    }

    public void solve(Supplier<InputStream> inputStream, Supplier<OutputStream> outputStream) {
        try {
            log.info("Data: \n" + org.apache.commons.io.IOUtils.toString(inputStream.get()));
            Data data = deserialize(inputStream.get(), Data.class);
            validate(data);
            Output output = compute(data);
            String json = serialize(output);
            writeToOutputStream(outputStream.get(), json);
            log.info("Output: \n" + json);
        } catch (ValidationException | InvalidFormatException e) {
            log.error("Error while validating input data: " + e);
        } catch (Exception e) {
            log.error("Error while running level solver service: " + e);
        }
    }

    private void validate(Data data) {
        Set<ConstraintViolation<Data>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }

    private Output compute(Data data) {
        return new Output(payService.getWorkerPays(data.getWorkers(), data.getShifts()));
    }

}
