package ru.itdt.converterService;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;


//проверка логов ошибок после конвертирования файлов
public class IncorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/incorrectFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/incorrectFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/incorrectFiles/outputFiles";

    @Test
    public void emptyGenres() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(XML_PATH + "/emptyGenres.xml")
                    .convertTo(OUTPUT_PATH + "/out.json");

            Assert.assertEquals("Не найдены жанры группы в 1-м теге Band",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void incorrectRootTag() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(XML_PATH + "/incorrectRootTag.xml")
                    .convertTo(OUTPUT_PATH + "/out.json");

            Assert.assertEquals("Тег Bands должен быть корневым тегом файла",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void noGenresXML() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(XML_PATH + "/noGenres.xml")
                    .convertTo(OUTPUT_PATH + "/out.json");

            Assert.assertEquals("Не найдены жанры группы в 1-м теге Band",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void noBandName() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(XML_PATH + "/noBandName.xml")
                    .convertTo(OUTPUT_PATH + "/out.json");

            Assert.assertEquals("Не найдено название группы в 1-м теге Band",
                    Logger.getErrors().get(0));

            Assert.assertEquals("Значение 'name' не было установлено в 1-м объекте 'genre'",
                    Logger.getErrors().get(1));

            Assert.assertEquals("Значение 'name' не было установлено в 2-м объекте 'genre'",
                    Logger.getErrors().get(2));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void emptyBands() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(JSON_PATH + "/emptyBands.json")
                    .convertTo(OUTPUT_PATH + "/out.xml");

            Assert.assertEquals("Музыкальные группы не найдены в 1 объекте 'genre'",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void noCountry() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(JSON_PATH + "/noCountry.json")
                    .convertTo(OUTPUT_PATH + "/out.xml");

            Assert.assertEquals("Ключ 'country' музыкальной группы не найден в 1 объекте 'genre'",
                    Logger.getErrors().get(0));

            Assert.assertEquals("Значение 'country' не было установлено в 2-м теге Band",
                    Logger.getErrors().get(1));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void noGenreName() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(JSON_PATH + "/noGenreName.json")
                    .convertTo(OUTPUT_PATH + "/out.xml");

            Assert.assertEquals("Ключ 'name' не найден в 1-м объекте 'genre'",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }

    @Test
    public void noGenresJSON() throws IOException, XMLStreamException, ParseException, ParserConfigurationException {
        try {
            FileConverterFactory.create(JSON_PATH + "/noGenres.json")
                    .convertTo(OUTPUT_PATH + "/out.xml");

            Assert.assertEquals("Ключ 'genres' не найден",
                    Logger.getErrors().get(0));
        } finally {
            Logger.clear();
        }
    }
}