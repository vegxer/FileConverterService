package ru.itdt.converterService.validators;

import java.util.Calendar;

public final class YearValidator implements Validator<Integer> {

    @Override
    public boolean validate(Integer year) {
        if (year == null)
            return false;

        return year >= 0 && year <= Calendar.getInstance().get(Calendar.YEAR);
    }
}
