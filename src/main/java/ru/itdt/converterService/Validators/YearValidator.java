package ru.itdt.converterService.Validators;

public final class YearValidator implements Validator<Integer> {

    @Override
    public ValidationResult validate(Integer year) {
        if (year == null)
            return new ValidationResult(false, "Год был равен null");

        if (year < 0)
            return new ValidationResult(false, "Год был меньше нуля");

        return new ValidationResult(true, "");
    }
}
