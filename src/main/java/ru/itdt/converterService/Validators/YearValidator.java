package ru.itdt.converterService.Validators;

public class YearValidator implements Validator<Integer> {

    @Override
    public boolean isValid(Integer year) {
        return year >= 0;
    }
}
