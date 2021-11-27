package ru.itdt.converterService;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.itdt.converterService.fileConverter.FileConverterFactory;
import ru.itdt.converterService.fileReader.MusicBandsReader;
import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileReader.Reader;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;


public class CorrectFilesTest {
    private static final String JSON_PATH = "src/test/resources/correctFiles/controlFiles/jsonFiles";
    private static final String XML_PATH = "src/test/resources/correctFiles/controlFiles/xmlFiles";
    private static final String OUTPUT_PATH = "src/test/resources/correctFiles/tempTestFiles";


    @Test
    public void twoGenres() throws IOException, XMLStreamException, ParseException {
        testEquality(XML_PATH + "/differentGenres.xml", JSON_PATH + "/differentGenres.json");
    }

    @Test
    public void manyBands() throws IOException, XMLStreamException, ParseException {
        testEquality(XML_PATH + "/manyBands.xml", JSON_PATH + "/manyBands.json");
    }

    @Test
    public void differentGenres() throws IOException, XMLStreamException, ParseException {
        testEquality(XML_PATH + "/differentGenres.xml", JSON_PATH + "/differentGenres.json");
    }

    @Test
    public void initialFiles() throws IOException, XMLStreamException, ParseException {
        testEquality(XML_PATH + "/initial.xml", JSON_PATH + "/initial.json");
    }

    //конвертирование из xml в json (и наоборот) и сверение текста конвертированного файла
    //с эталонным без учёта порядка элементов
    private void testEquality(String xmlPath, String jsonPath) throws IOException, XMLStreamException, ParseException {
        FileConverterFactory.create(jsonPath)
                .convert(OUTPUT_PATH + "/out.xml");
        try (Reader<Collection<MusicBand>> xmlReaderTest = new MusicBandsReader(new FileInputStream(OUTPUT_PATH + "/out.xml"));
             Reader<Collection<MusicBand>> xmlReaderControl = new MusicBandsReader(new FileInputStream(xmlPath))) {
            ArrayList<MusicBand> testList = new ArrayList<>(xmlReaderTest.readFile());
            testList.forEach(x -> new ArrayList<>(x.getMusicGenres()).sort(Comparator.comparing(MusicGenre::getGenreName)));
            testList.sort(Comparator.comparing(MusicBand::getBandName));
            ArrayList<MusicBand> controlList = new ArrayList<>(xmlReaderControl.readFile());
            testList.forEach(x -> new ArrayList<>(x.getMusicGenres()).sort(Comparator.comparing(MusicGenre::getGenreName)));
            controlList.sort(Comparator.comparing(MusicBand::getBandName));
            Assert.assertTrue(testList.equals(controlList));
        }

        FileConverterFactory.create(xmlPath)
                .convert(OUTPUT_PATH + "/out.json");
        try (Reader<Collection<MusicGenre>> jsonReaderTest = new MusicGenresReader(new FileInputStream(OUTPUT_PATH + "/out.json"));
             Reader<Collection<MusicGenre>> jsonReaderControl = new MusicGenresReader(new FileInputStream(jsonPath))) {
            ArrayList<MusicGenre> testList = new ArrayList<>(jsonReaderTest.readFile());
            testList.forEach(x -> new ArrayList<>(x.getMusicBands()).sort(Comparator.comparing(MusicBand::getBandName)));
            testList.sort(Comparator.comparing(MusicGenre::getGenreName));
            ArrayList<MusicGenre> controlList = new ArrayList<>(jsonReaderControl.readFile());
            testList.forEach(x -> new ArrayList<>(x.getMusicBands()).sort(Comparator.comparing(MusicBand::getBandName)));
            controlList.sort(Comparator.comparing(MusicGenre::getGenreName));
            Assert.assertTrue(testList.equals(controlList));
        }
    }
}