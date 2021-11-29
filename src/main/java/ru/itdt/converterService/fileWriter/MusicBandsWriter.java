package ru.itdt.converterService.fileWriter;

import ru.itdt.converterService.Logger;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Collection;

public final class MusicBandsWriter extends Writer<Collection<MusicBand>> {
    public MusicBandsWriter(FileOutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(@NotNull Collection<MusicBand> musicBands) throws ParserConfigurationException, IOException {
        Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        xmlDoc.appendChild(getMusicBands(xmlDoc, musicBands));

        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            getPrettyOutputTransformer().transform(new DOMSource(xmlDoc), new StreamResult(writer));
        } catch (IOException writerException) {
            throw new IOException(
                    String.format("Ошибка создания Writer для записи успешно созданного xml документа в файл: %s",
                            writerException.getMessage()), writerException);
        } catch (TransformerException writeException) {
            throw new IOException(String.format("Ошибка записи успешно созданного xml документа в файл: %s",
                    writeException.getMessage()), writeException);
        }
    }

    //установка красивой записи в xml файл (с переносами строк и отступами)
    private Transformer getPrettyOutputTransformer() throws TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        return transformer;
    }

    public Element getMusicBands(Document xmlDoc, Collection<MusicBand> musicBands) {
        Element root = xmlDoc.createElement("Bands");

        int bandsCount = 0;
        //создание xml элементов Band, в каждом из которых хранится информация о группе
        for (MusicBand musicBand : musicBands) {
            ++bandsCount;
            Element musicBandElem = xmlDoc.createElement("Band");

            Element name = xmlDoc.createElement("name");
            if (musicBand.getBandName() == null) {
                Logger.addError(
                        String.format("Значение 'name' не было установлено в %d-м теге Band", bandsCount));
                name.setTextContent("");
            } else {
                name.setTextContent(musicBand.getBandName());
            }
            musicBandElem.appendChild(name);

            Element country = xmlDoc.createElement("country");
            if (musicBand.getCountry() == null) {
                Logger.addError(
                        String.format("Значение 'country' не было установлено в %d-м теге Band", bandsCount));
                country.setTextContent("");
            } else {
                country.setTextContent(musicBand.getCountry());
            }
            musicBandElem.appendChild(country);

            Element year = xmlDoc.createElement("year");
            if (musicBand.getActivateYear() == null) {
                Logger.addError(
                        String.format("Значение 'year' не было установлено в %d-м теге Band", bandsCount));
                year.setTextContent("");
            } else {
                year.setTextContent(Integer.toString(musicBand.getActivateYear()));
            }
            musicBandElem.appendChild(year);

            musicBandElem.appendChild(getBandGenres(xmlDoc, musicBand, bandsCount));

            root.appendChild(musicBandElem);
        }

        return root;
    }

    //создание элементов, в которых хранятся жанры, исполняемые группой
    public Element getBandGenres(Document xmlDoc, MusicBand musicBand, int bandsCount) {
        Element genresElem = xmlDoc.createElement("Genres");
        if (musicBand.getMusicGenres().isEmpty()) {
            Logger.addError(String.format("Значение 'Genres' не было установлено в %d-м теге Band", bandsCount));
        }

        for (MusicGenre musicGenre : musicBand.getMusicGenres()) {
            Element genreElem = xmlDoc.createElement("genre");
            genreElem.setTextContent(musicGenre.getGenreName());

            genresElem.appendChild(genreElem);
        }

        return genresElem;
    }
}
