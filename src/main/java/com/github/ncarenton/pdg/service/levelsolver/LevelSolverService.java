package com.github.ncarenton.pdg.service.levelsolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.ncarenton.pdg.domain.Data;
import com.github.ncarenton.pdg.domain.Output;
import com.github.ncarenton.pdg.domain.Worker;
import com.github.ncarenton.pdg.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.function.Supplier;

import static com.github.ncarenton.pdg.service.pay.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.service.pay.PayService.WeekendShiftPriceMultiplier.WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER;
import static com.github.ncarenton.pdg.utils.IOUtils.deserialize;
import static com.github.ncarenton.pdg.utils.IOUtils.serialize;
import static com.github.ncarenton.pdg.utils.IOUtils.writeToOutputStream;

@Slf4j
public abstract class LevelSolverService<T extends Worker> {

    private final Validator validator;

    private final Class<T> workerType;

    protected LevelSolverService(Class<T> workerType) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.workerType = workerType;
    }

    public static LevelSolverServiceWithNoCommission forSimpleWeekendFee() {
        return new LevelSolverServiceWithNoCommission(new PayService(WEEKEND_SHIFT_SIMPLE_PRICE_MULTIPLIER));
    }

    public static LevelSolverServiceWithNoCommission forDoubleWeekendFee() {
        return new LevelSolverServiceWithNoCommission(new PayService(WEEKEND_SHIFT_DOUBLE_PRICE_MULTIPLIER));
    }

    public static LevelSolverServiceWithCommission withCommission() {
        return new LevelSolverServiceWithCommission();
    }

    public void solve(Supplier<InputStream> inputStream, Supplier<OutputStream> outputStream) {
        try {
            log.info("Data: \n" + IOUtils.toString(inputStream.get()));
            Data<T> data = deserialize(inputStream.get(), new TypeReference<Data<T>>() {});
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

    private void validate(Data<T> data) {
        data.getWorkers()
            .stream()
            .filter(t -> !workerType.isInstance(t))
            .findAny()
            .ifPresent((t) -> {
                throw new ValidationException("Invalid worker type");
            });

        Set<ConstraintViolation<Data<T>>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }

    protected abstract Output compute(Data<T> data);

}
