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
    public MusicBandsWriter(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(@NotNull Collection<MusicBand> musicBands)
            throws ParserConfigurationException, TransformerException, IOException {
        Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        xmlDoc.appendChild(getMusicBands(xmlDoc, musicBands));

        try (StringWriter sw = new StringWriter()) {
            getPrettyOutputTransformer().transform(new DOMSource(xmlDoc), new StreamResult(sw));
            new OutputStreamWriter(outputStream).write(sw.toString());
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
            name.setTextContent(musicBand.getName());
            musicBandElem.appendChild(name);
            Element country = xmlDoc.createElement("country");
            country.setTextContent(musicBand.getCountry());
            musicBandElem.appendChild(country);
            Element year = xmlDoc.createElement("year");
            year.setTextContent(Integer.toString(musicBand.getActivateYear()));
            musicBandElem.appendChild(year);

            musicBandElem.appendChild(getBandGenres(xmlDoc, musicBand));

            root.appendChild(musicBandElem);
        }

        return root;
    }

    public Element getBandGenres(Document xmlDoc, MusicBand musicBand) {
        Element genresElem = xmlDoc.createElement("Genres");

        for (MusicGenre musicGenre : musicBand.getGenres()) {
            Element genreElem = xmlDoc.createElement("genre");
            genreElem.setTextContent(musicGenre.getName());

            genresElem.appendChild(genreElem);
        }

        return genresElem;
    }
}
