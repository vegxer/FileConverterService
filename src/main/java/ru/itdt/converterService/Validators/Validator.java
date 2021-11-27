package ru.itdt.converterService.Validators;

public interface Validator<T> {
    boolean validate(T object);
}
