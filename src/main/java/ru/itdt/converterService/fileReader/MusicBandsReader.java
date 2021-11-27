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

    private Collection<MusicBand> getMusicBands(XMLEventReader reader) throws XMLStreamException {
        Collection<MusicBand> musicBands = new ArrayList<>();

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

        int bandsCount = 0;
        while (!xmlEvent.isEndDocument() && (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Bands"))) {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement() && !xmlEvent.isEndElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName()
                        .getLocalPart();

                if ("Band".equals(tagName)) {
                    musicBands.add(getMusicBand(reader, ++bandsCount));
                } else {
                    System.err.printf("Необрабатываемый тег %s%n", tagName);
                }
            }
        }

        if (musicBands.isEmpty()) {
            System.err.println("Пустой тег Bands");
        }

        return musicBands;
    }

    private MusicBand getMusicBand(XMLEventReader reader, int bandsCount) throws XMLStreamException {
        MusicBand musicBand = new MusicBand();

        XMLEvent xmlEvent;
        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement() && !xmlEvent.isEndElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName()
                        .getLocalPart();

                switch (tagName) {
                    case "year" -> {
                        XMLEvent event = reader.nextEvent();
                        if (event.isCharacters()) {
                            musicBand.setActivateYear(Integer.parseInt(
                                    event.asCharacters()
                                    .getData()));
                        }
                    }
                    case "name" -> {
                        XMLEvent event = reader.nextEvent();
                        if (event.isCharacters()) {
                            musicBand.setBandName(event
                                    .asCharacters()
                                    .getData());
                        }
                    }
                    case "country" -> {
                        XMLEvent event = reader.nextEvent();
                        if (event.isCharacters()) {
                            musicBand.setCountry(event
                                    .asCharacters()
                                    .getData());
                        }
                    }
                    case "Genres" -> musicBand.getMusicGenres().addAll(getGenres(reader, bandsCount));
                    default -> System.err.printf("Необрабатываемый тег %s в %d-м теге Band%n", tagName, bandsCount);
                }
            }
        }
        while (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Band"));

        if (musicBand.getBandName() == null) {
            System.err.printf("Не найдено название группы в %d-м теге Band%n", bandsCount);
        }
        if (musicBand.getActivateYear() == null) {
            System.err.printf("Не найден год создания группы в %d-м теге Band%n", bandsCount);
        }
        if (musicBand.getCountry() == null) {
            System.err.printf("Не найдена страна группы в %d-м теге Band%n", bandsCount);
        }
        if (musicBand.getMusicGenres().isEmpty()) {
            System.err.printf("Не найдены жанры группы в %d-м теге Band%n", bandsCount);
        }

        return musicBand;
    }

    private Collection<MusicGenre> getGenres(XMLEventReader reader, int bandsCount) throws XMLStreamException {
        Collection<MusicGenre> musicGenres = new ArrayList<>();

        XMLEvent xmlEvent;
        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement() && !xmlEvent.isEndElement()) {
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
                    System.err.printf("Необрабатываемый тег %s в %d-м теге Band в теге Genres%n", tagName, bandsCount);
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
