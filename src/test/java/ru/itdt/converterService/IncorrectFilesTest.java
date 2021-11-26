package ru.itdt.converterService;

import com.google.gson.JsonParseException;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.itdt.converterService.fileConverter.FileConverterFactory;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class IncorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/incorrectFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/incorrectFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/incorrectFiles/outputFiles";
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void emptyGenres() throws IOException, XMLStreamException, ParseException {
        thrown.expect(XMLParseException.class);
        thrown.expectMessage("Genres are not found");
        FileConverterFactory.create(XML_PATH + "/emptyGenres.xml").convert(OUTPUT_PATH + "/out.json");
    }

    @Test
    public void incorrectRootTag() throws IOException, XMLStreamException, ParseException {
        thrown.expect(XMLParseException.class);
        thrown.expectMessage("Bands tag must be first tag");
        FileConverterFactory.create(XML_PATH + "/incorrectRootTag.xml").convert(OUTPUT_PATH + "/out.json");
    }

    @Test
    public void noGenresXML() throws IOException, XMLStreamException, ParseException {
        thrown.expect(XMLParseException.class);
        thrown.expectMessage("Not enough tags in Band tag");
        FileConverterFactory.create(XML_PATH + "/noGenres.xml").convert(OUTPUT_PATH + "/out.json");
    }

    @Test
    public void noBandName() throws IOException, XMLStreamException, ParseException {
        thrown.expect(XMLParseException.class);
        thrown.expectMessage("Not enough tags in Band tag");
        FileConverterFactory.create(XML_PATH + "/noBandName.xml").convert(OUTPUT_PATH + "/out.json");
    }

    @Test
    public void emptyBands() throws IOException, XMLStreamException, ParseException {
        thrown.expect(JsonParseException.class);
        thrown.expectMessage("Empty array of bands");
        FileConverterFactory.create(JSON_PATH + "/emptyBands.json").convert(OUTPUT_PATH + "/out.xml");
    }

    @Test
    public void noCountry() throws IOException, XMLStreamException, ParseException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("parameter 'country' of ru/itdt/converterService/music/MusicBand.setCountry must not be null");
        FileConverterFactory.create(JSON_PATH + "/noCountry.json").convert(OUTPUT_PATH + "/out.xml");
    }

    @Test
    public void noGenreName() throws IOException, XMLStreamException, ParseException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("parameter 'name' of ru/itdt/converterService/music/MusicGenre.setName must not be null");
        FileConverterFactory.create(JSON_PATH + "/noGenreName.json").convert(OUTPUT_PATH + "/out.xml");
    }

    @Test
    public void noGenresJSON() throws IOException, XMLStreamException, ParseException {
        thrown.expect(JsonParseException.class);
        thrown.expectMessage("Key genres is not found");
        FileConverterFactory.create(JSON_PATH + "/noGenres.json").convert(OUTPUT_PATH + "/out.xml");
    }
}