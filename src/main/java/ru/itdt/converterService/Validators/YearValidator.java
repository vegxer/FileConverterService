package ru.itdt.converterService.Validators;

public final class YearValidator implements Validator<Integer> {

    @Override
    public boolean validate(Integer year) {
        if (year == null)
            return false;

        return year >= 0;
    }
}
