package ru.itdt.converterService.fileReader;

import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class MusicBandsReader extends Reader<List<MusicBand>> {
    public MusicBandsReader(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public List<MusicBand> readFile() throws IOException, XMLStreamException, XMLParseException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (FileInputStream inputStream = new FileInputStream(super.fileName)) {
            XMLEventReader reader = factory.createXMLEventReader(inputStream);
            XMLEvent xmlEvent = reader.nextEvent();
            return getMusicBands(xmlEvent, reader);
        }
    }

    private ArrayList<MusicBand> getMusicBands(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        ArrayList<MusicBand> musicBands = new ArrayList<>();

        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();

            if (!xmlEvent.isStartElement() || !xmlEvent.asStartElement().getName().getLocalPart().equals("Bands")) {
                throw new XMLParseException("Bands tag must be first tag");
            }
        }
        else
            throw new XMLParseException("File is empty");

        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Bands"))
        {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if ("Band".equals(tagName)) {
                    musicBands.add(getMusicBand(xmlEvent, reader));
                } else {
                    throw new XMLParseException("Incorrect tag");
                }
            }
        }

        if (musicBands.isEmpty()) {
            throw new XMLParseException("No music bands found");
        }

        return musicBands;
    }

    private MusicBand getMusicBand(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("Band")) {
            throw new IllegalArgumentException();
        }

        MusicBand musicBand = new MusicBand();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                switch (tagName) {
                    case "year" -> musicBand.setActivateYear(Integer.parseInt(reader.nextEvent().asCharacters().getData()));
                    case "name" -> musicBand.setName(reader.nextEvent().asCharacters().getData());
                    case "country" -> musicBand.setCountry(reader.nextEvent().asCharacters().getData());
                    case "Genres" -> musicBand.setGenres(getGenres(xmlEvent, reader));
                    default -> throw new XMLParseException("Incorrect tag");
                }
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Band"));

        if (musicBand.getName() == null || musicBand.getActivateYear() == null || musicBand.getCountry() == null
                || musicBand.getGenres().isEmpty()) {
            throw new XMLParseException("Not enough tags in Band tag");
        }

        return musicBand;
    }

    private ArrayList<MusicGenre> getGenres(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("Genres")) {
            throw new IllegalArgumentException();
        }

        ArrayList<MusicGenre> musicGenres = new ArrayList<>();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("genre")) {
                    MusicGenre musicGenre = new MusicGenre();
                    musicGenre.setName(reader.nextEvent().asCharacters().getData());
                    musicGenres.add(musicGenre);
                } else {
                    throw new XMLParseException("Incorrect tag");
                }
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Genres"));

        if (musicGenres.isEmpty()) {
            throw new XMLParseException("Genres are not found");
        }

        return musicGenres;
    }
}
