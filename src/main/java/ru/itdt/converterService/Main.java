package ru.itdt.converterService;

import ru.itdt.converterService.fileConverter.FileConverterFactory;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, ParseException,
            ParserConfigurationException, XMLParseException, TransformerException {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
                    Некорректное количество аргументов. Необходимо 2 аргумента:
                    1) Файл, из которого вы хотите ковертировать данные (.xml или .json)
                    2) Файл, в который вы хотите записать результат конвертации (.json или .xml, соответственно с первым параметром)""");
        }

        FileConverterFactory.create(args[0]).convert(args[1]);
        System.out.println(String.format("Преобразование прошло успешно, файл %s создан", args[1]));
    }
}
