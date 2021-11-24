package converterService;

import converterService.fileConverter.FileConverterFactory;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 2)
                throw new IllegalArgumentException("Некорректное количество аргументов. Необходимо 2 аргумента");

            FileConverterFactory.create(args[0]).convert(args[1]);
            System.out.println("Преобразование прошло успешно, файл " + args[1] + " создан");
        }
        catch (XMLStreamException | IOException | ParseException | ParserConfigurationException | XMLParseException
                | TransformerException e) {
            e.printStackTrace();
        }
    }
}
