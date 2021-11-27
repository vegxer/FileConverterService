package ru.itdt.converterService.fileWriter;

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
    public void write(@NotNull Collection<MusicBand> musicBands) throws ParserConfigurationException {
        Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        xmlDoc.appendChild(getMusicBands(xmlDoc, musicBands));

        try (java.io.Writer sw = new StringWriter()) {
            java.io.Writer writer = new OutputStreamWriter(outputStream);
            try {
                getPrettyOutputTransformer().transform(new DOMSource(xmlDoc), new StreamResult(sw));
                writer.write(sw.toString());
                writer.flush();
            } catch (TransformerException | IOException writeException) {
                System.out.println("Ошибка Writer'а при записи в xml файл");
            }
        } catch (IOException writerException) {
            System.out.println("Ошибка создания StringWriter");
        }
    }

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

        for (MusicBand musicBand : musicBands) {
            Element musicBandElem = xmlDoc.createElement("Band");

            Element name = xmlDoc.createElement("name");
            if (musicBand.getBandName() == null) {
                System.err.println("Значение 'name' не было установлено");
                name.setTextContent("");
            } else {
                name.setTextContent(musicBand.getBandName());
            }
            musicBandElem.appendChild(name);

            Element country = xmlDoc.createElement("country");
            if (musicBand.getCountry() == null) {
                System.err.println("Значение 'country' не было установлено");
                country.setTextContent("");
            } else {
                country.setTextContent(musicBand.getCountry());
            }
            musicBandElem.appendChild(country);

            Element year = xmlDoc.createElement("year");
            if (musicBand.getActivateYear() == null) {
                System.err.println("Значение 'year' не было установлено");
                year.setTextContent("");
            } else {
                year.setTextContent(Integer.toString(musicBand.getActivateYear()));
            }
            musicBandElem.appendChild(year);

            musicBandElem.appendChild(getBandGenres(xmlDoc, musicBand));

            root.appendChild(musicBandElem);
        }

        return root;
    }

    public Element getBandGenres(Document xmlDoc, MusicBand musicBand) {
        Element genresElem = xmlDoc.createElement("Genres");
        if (musicBand.getMusicGenres().isEmpty()) {
            System.err.println("Значение 'Genres' не было установлено");
        }

        for (MusicGenre musicGenre : musicBand.getMusicGenres()) {
            Element genreElem = xmlDoc.createElement("genre");
            genreElem.setTextContent(musicGenre.getGenreName());

            genresElem.appendChild(genreElem);
        }

        return genresElem;
    }
}
