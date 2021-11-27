package ru.itdt.converterService;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.xml.sax.SAXException;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/correctFiles/controlFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/correctFiles/controlFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/correctFiles/tempTestFiles";

    @Before
    public void setIgnoreOrder()
    {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Test
    public void twoGenres() throws IOException, XMLStreamException, ParseException, JSONException, SAXException {
        testEquality(XML_PATH + "/differentGenres.xml", JSON_PATH + "/differentGenres.json");
    }

    @Test
    public void manyBands() throws IOException, XMLStreamException, ParseException, JSONException, SAXException {
        testEquality(XML_PATH + "/manyBands.xml", JSON_PATH + "/manyBands.json");
    }

    @Test
    public void differentGenres() throws IOException, XMLStreamException, ParseException, JSONException, SAXException {
        testEquality(XML_PATH + "/differentGenres.xml", JSON_PATH + "/differentGenres.json");
    }

    @Test
    public void initialFiles() throws IOException, XMLStreamException, ParseException, JSONException, SAXException {
        testEquality(XML_PATH + "/initial.xml", JSON_PATH + "/initial.json");
    }

    //конвертирование из xml в json (и наоборот) и сверение конвертированного файла с эталонным
    private void testEquality(String xmlPath, String jsonPath) throws IOException, XMLStreamException, ParseException,
            SAXException, JSONException {
        FileConverterFactory.create(jsonPath)
                .convert(OUTPUT_PATH + "/out.xml");
        Diff diff = new Diff(
                FileUtils.readFileToString(new File(xmlPath), "utf-8"),
                FileUtils.readFileToString(new File(OUTPUT_PATH + "/out.xml"), "utf-8"));
        Assert.assertTrue(diff.similar());

        FileConverterFactory.create(xmlPath)
                .convert(OUTPUT_PATH + "/out.json");
        JSONAssert.assertEquals(FileUtils.readFileToString(new File(jsonPath), "utf-8"),
                FileUtils.readFileToString(new File(OUTPUT_PATH + "/out.json"), "utf-8"),
                JSONCompareMode.LENIENT);
    }
}