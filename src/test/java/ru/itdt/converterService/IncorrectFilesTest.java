package ru.itdt.converterService;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class IncorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/incorrectFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/incorrectFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/incorrectFiles/outputFiles";

    @Test
    public void emptyGenres() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(XML_PATH + "/emptyGenres.xml").convert(OUTPUT_PATH + "/out.json");
        Assert.assertEquals("Не найдены жанры группы\r\n", FileUtils.readFileToString(
                new File("xml reading log.txt"), "utf-8"));
    }

    @Test
    public void incorrectRootTag() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(XML_PATH + "/incorrectRootTag.xml").convert(OUTPUT_PATH + "/out.json");
        Assert.assertEquals("Тег Bands должен быть корневым тегом файла\r\n", FileUtils.readFileToString(
                new File("xml reading log.txt"), "utf-8"));
    }

    @Test
    public void noGenresXML() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(XML_PATH + "/noGenres.xml").convert(OUTPUT_PATH + "/out.json");
        Assert.assertEquals("Не найдены жанры группы\r\n", FileUtils.readFileToString(
                new File("xml reading log.txt"), "utf-8"));
    }

    @Test
    public void noBandName() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(XML_PATH + "/noBandName.xml").convert(OUTPUT_PATH + "/out.json");
        Assert.assertEquals("Не найдено название группы\r\n", FileUtils.readFileToString(
                new File("xml reading log.txt"), "utf-8"));
    }

    @Test
    public void emptyBands() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(JSON_PATH + "/emptyBands.json").convert(OUTPUT_PATH + "/out.xml");
        Assert.assertEquals("Музыкальные группы не найдены\r\n", FileUtils.readFileToString(
                new File("json reading log.txt"), "utf-8"));
    }

    @Test
    public void noCountry() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(JSON_PATH + "/noCountry.json").convert(OUTPUT_PATH + "/out.xml");
        Assert.assertEquals("Ключ 'country' не найден\r\n", FileUtils.readFileToString(
                new File("json reading log.txt"), "utf-8"));
    }

    @Test
    public void noGenreName() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(JSON_PATH + "/noGenreName.json").convert(OUTPUT_PATH + "/out.xml");
        Assert.assertEquals("Ключ 'name' не найден\r\n", FileUtils.readFileToString(
                new File("json reading log.txt"), "utf-8"));
    }

    @Test
    public void noGenresJSON() throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(JSON_PATH + "/noGenres.json").convert(OUTPUT_PATH + "/out.xml");
        Assert.assertEquals("Ключ 'genres' не найден\r\n", FileUtils.readFileToString(
                new File("json reading log.txt"), "utf-8"));
    }
}