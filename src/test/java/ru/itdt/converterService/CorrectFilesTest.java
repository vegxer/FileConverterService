package ru.itdt.converterService;

import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.matchers.CompareMatcher;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;


public class CorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/correctFiles/controlFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/correctFiles/controlFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/correctFiles/tempTestFiles";


    @Test
    public void twoGenres() throws IOException, XMLStreamException, ParseException, JSONException {
        testEquality(XML_PATH + "/twoGenres.xml", JSON_PATH + "/twoGenres.json");
    }

    @Test
    public void manyBands() throws IOException, XMLStreamException, ParseException, JSONException {
        testEquality(XML_PATH + "/manyBands.xml", JSON_PATH + "/manyBands.json");
    }

    @Test
    public void differentGenres() throws IOException, XMLStreamException, ParseException, JSONException {
        testEquality(XML_PATH + "/differentGenres.xml", JSON_PATH + "/differentGenres.json");
    }

    @Test
    public void initialFiles() throws IOException, XMLStreamException, ParseException, JSONException {
        testEquality(XML_PATH + "/initial.xml", JSON_PATH + "/initial.json");
    }

    //конвертирование из xml в json (и наоборот) и сверение текста конвертированного файла
    //с эталонным без учёта порядка элементов, пробелов и переносов строк
    private void testEquality(String xmlPath, String jsonPath) throws IOException, XMLStreamException, ParseException, JSONException {
        FileConverterFactory.create(jsonPath)
                .convert(OUTPUT_PATH + "/out.xml");
        MatcherAssert.assertThat(
                FileUtils.readFileToString(new File(OUTPUT_PATH + "/out.xml"), "utf-8"),
                CompareMatcher.isSimilarTo(FileUtils.readFileToString(new File(xmlPath), "utf-8"))
                        .ignoreComments()
                        .ignoreWhitespace()
                        .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes))
                        .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText)));

        FileConverterFactory.create(xmlPath)
                .convert(OUTPUT_PATH + "/out.json");
        JSONAssert.assertEquals(FileUtils.readFileToString(new File(jsonPath), "utf-8"),
                FileUtils.readFileToString(new File(OUTPUT_PATH + "/out.json"), "utf-8"),
                JSONCompareMode.NON_EXTENSIBLE);
    }
}