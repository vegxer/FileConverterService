package ru.itdt.converterService.Validators;

import org.jetbrains.annotations.NotNull;

public record ValidationResult(boolean isValid, @NotNull String exceptionMessage) {
}
