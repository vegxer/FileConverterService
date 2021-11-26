package ru.itdt.converterService.Validators;

public interface Validator<T> {
    ValidationResult validate(T object);
}
