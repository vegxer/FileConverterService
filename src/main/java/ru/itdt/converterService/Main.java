package ru.itdt.converterService;

import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
                    Некорректное количество аргументов. Необходимо 2 аргумента:
                    1) Файл, из которого вы хотите ковертировать данные (.xml или .json)
                    2) Файл, в который вы хотите записать результат конвертации (.json или .xml, соответственно с первым параметром)
                    """);
        }
        System.out.println("Все возникающие ошибки, связанные со структурой файла," +
                " при чтении и записи в файл будут записываться в логи в папке out/logs/");

        try {
            FileConverterFactory.create(args[0])
                    .convert(args[1]);
            System.out.printf("Преобразование прошло успешно, файл %s создан%n", args[1]);
        } catch (ParserConfigurationException jsonWriteException) {
            System.out.printf("Ошибка конфигурации парсера записи в json файл: %s%n",
                    jsonWriteException.getMessage());
        } catch (XMLStreamException xmlStreamException) {
            System.out.printf("Ошибка чтения xml файла: %s%n", xmlStreamException.getMessage());
        } catch (ParseException parseException) {
            System.out.printf("Ошибка чтения json файла (некорретная структура файла): %s%n",
                    parseException.getMessage());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.printf("Ошибка нахождения файла: %s%n", fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
