package ru.itdt.converterService;

import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, ParseException {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
                    Некорректное количество аргументов. Необходимо 2 аргумента:
                    1) Файл, из которого вы хотите ковертировать данные (.xml или .json)
                    2) Файл, в который вы хотите записать результат конвертации (.json или .xml, соответственно с первым параметром)""");
        }
        System.out.println("Все возникающие ошибки при чтении и записи в файл будут записываться в логи в папке out/logs");

        FileConverterFactory.create(args[0])
                .convert(args[1]);
        System.out.printf("Преобразование прошло успешно, файл %s создан%n", args[1]);
    }
}
