package converterService.fileWriter;

import converterService.music.MusicBand;
import converterService.music.MusicGenre;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

public class MusicBandsWriter extends Writer<Collection<MusicBand>> {
    public MusicBandsWriter(String fileName) {
        super.setFileName(fileName);
    }

    @Override
    public void write(Collection<MusicBand> musicBands)
            throws ParserConfigurationException, TransformerException, IOException {
        Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        xmlDoc.appendChild(getMusicBands(xmlDoc, musicBands));

        DOMSource domSource = new DOMSource(xmlDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try (StringWriter sw = new StringWriter()) {
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            try (FileWriter writer = new FileWriter(super.fileName)) {
                writer.write(sw.toString());
            }
        }
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
