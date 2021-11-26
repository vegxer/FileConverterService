package ru.itdt.converterService.fileReader;

import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class MusicBandsReader extends Reader<Collection<MusicBand>> {
    public MusicBandsReader(FileInputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Collection<MusicBand> readFile() throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        reader.nextEvent();
        return getMusicBands(reader);
    }

    private ArrayList<MusicBand> getMusicBands(XMLEventReader reader) throws XMLStreamException {
        ArrayList<MusicBand> musicBands = new ArrayList<>();

        XMLEvent xmlEvent;
        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();

            if (!xmlEvent.isStartElement() ||
                    !xmlEvent.asStartElement()
                    .getName()
                    .getLocalPart()
                    .equals("Bands")) {
                System.err.println("Тег Bands должен быть корневым тегом файла");
            }
        }
        else {
            System.err.println("Файл пустой");
            return musicBands;
        }

        while (!xmlEvent.isEndDocument() && (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Bands"))) {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName()
                        .getLocalPart();

                if ("Band".equals(tagName)) {
                    musicBands.add(getMusicBand(reader));
                } else {
                    System.err.println(String.format("Необрабатываемый тег %s", tagName));
                }
            }
        }

        if (musicBands.isEmpty()) {
            System.err.println("Пустой тег Bands");
        }

        return musicBands;
    }

    private MusicBand getMusicBand(XMLEventReader reader)
            throws XMLStreamException {
        MusicBand musicBand = new MusicBand();

        XMLEvent xmlEvent;
        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName()
                        .getLocalPart();

                switch (tagName) {
                    case "year" -> musicBand.setActivateYear(Integer.parseInt(reader.nextEvent()
                            .asCharacters()
                            .getData()));
                    case "name" -> musicBand.setBandName(reader.nextEvent()
                            .asCharacters()
                            .getData());
                    case "country" -> musicBand.setCountry(reader.nextEvent()
                            .asCharacters()
                            .getData());
                    case "Genres" -> musicBand.getMusicGenres().addAll(getGenres(reader));
                    default -> System.err.println(String.format("Необрабатываемый тег %s", tagName));
                }
            }
        }
        while (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Band"));

        if (musicBand.getBandName() == null) {
            System.err.println(String.format("Не найден тег 'name'"));
        }
        if (musicBand.getActivateYear() == null) {
            System.err.println(String.format("Не найден тег 'year'"));
        }
        if (musicBand.getCountry() == null) {
            System.err.println(String.format("Не найден тег 'country'"));
        }
        if (musicBand.getMusicGenres().isEmpty()) {
            System.err.println(String.format("Жанров не нашлось"));
        }

        return musicBand;
    }

    private ArrayList<MusicGenre> getGenres(XMLEventReader reader)
            throws XMLStreamException {
        ArrayList<MusicGenre> musicGenres = new ArrayList<>();

        XMLEvent xmlEvent;
        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName()
                        .getLocalPart();

                if (tagName.equals("genre")) {
                    MusicGenre musicGenre = new MusicGenre();
                    musicGenre.setGenreName(reader.nextEvent()
                            .asCharacters()
                            .getData());
                    musicGenres.add(musicGenre);
                } else {
                    System.err.println(String.format("Необрабатываемый тег %s", tagName));
                }
            }
        }
        while (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Genres"));

        return musicGenres;
    }
}
