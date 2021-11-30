package ru.itdt.converterService.file.reader;

import ru.itdt.converterService.Logger;
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

        //поиск корневого тега Bands
        XMLEvent xmlEvent;
        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();

            if (!xmlEvent.isStartElement() ||
                    !xmlEvent.asStartElement()
                    .getName()
                    .getLocalPart()
                    .equals("Bands")) {
                Logger.addError("Тег Bands должен быть корневым тегом файла");
            }
        }
        else {
            Logger.addError("Файл пустой");
            return musicBands;
        }

        //проход по всем тегам Band
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
                    Logger.addError(
                            String.format("Необрабатываемый тег %s", tagName));
                }
            }
        }

        if (musicBands.isEmpty()) {
            Logger.addError("Пустой тег Bands");
        }

        return musicBands;
    }

    private MusicBand getMusicBand(XMLEventReader reader, int bandsCount) throws XMLStreamException {
        MusicBand musicBand = new MusicBand();

        //считывание полей класса MusicBand из тега Band
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
                    default -> Logger.addError(
                            String.format("Необрабатываемый тег %s в %d-м теге Band", tagName, bandsCount));
                }
            }
        }
        while (!xmlEvent.isEndElement() ||
                !xmlEvent.asEndElement()
                        .getName()
                        .getLocalPart()
                        .equals("Band"));

        //если какая-то информация о группе не находится, то соответствующее сообщение записывается в лог
        if (musicBand.getBandName() == null) {
            Logger.addError(
                    String.format("Не найдено название группы в %d-м теге Band", bandsCount));
        }
        if (musicBand.getActivateYear() == null) {
            Logger.addError(
                    String.format("Не найден год создания группы в %d-м теге Band", bandsCount));
        }
        if (musicBand.getCountry() == null) {
            Logger.addError(
                    String.format("Не найдена страна группы в %d-м теге Band", bandsCount));
        }
        if (musicBand.getMusicGenres().isEmpty()) {
            Logger.addError(
                    String.format("Не найдены жанры группы в %d-м теге Band", bandsCount));
        }

        return musicBand;
    }

    private Collection<MusicGenre> getGenres(XMLEventReader reader, int bandsCount) throws XMLStreamException {
        Collection<MusicGenre> musicGenres = new ArrayList<>();

        //считывание музыкальных жанров у музыкальной группы
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
                    Logger.addError(
                            String.format("Необрабатываемый тег %s в %d-м теге Band в теге Genres", tagName, bandsCount));
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
