package ru.itdt.converterService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Logger {
    private static List<String> errors;

    public static void addError(String errorMessage) {
        getErrors().add(errorMessage);
    }

    public static List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        return errors;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeTo(File file) throws IOException {
        file.getParentFile().mkdirs();
        Files.write(Paths.get(file.getAbsolutePath()), getErrors(), StandardCharsets.UTF_8);
    }

    public static void clear() {
        getErrors().clear();
    }
}
