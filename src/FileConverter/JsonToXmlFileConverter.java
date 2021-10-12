package FileConverter;


import FileExtension.FileExtension;
import FileReader.Reader;
import FileWriter.Writer;
import Music.MusicBand;
import Music.MusicGenre;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class JsonToXmlFileConverter extends FileConverter {
    public JsonToXmlFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String xmlFileName)
            throws IOException, XMLStreamException, XMLParseException,
            ParseException, ParserConfigurationException, TransformerException {
        if (!FileExtension.getExtension(xmlFileName).equals("xml"))
            throw new IllegalArgumentException("Неверное расширение файла");

        Reader jsonReader = Reader.create(super.fileName);

        ArrayList<MusicGenre> musicGenres = (ArrayList<MusicGenre>)jsonReader.readFile();
        ArrayList<MusicBand> musicBands = MusicBand.convertGenresToMusicBands(musicGenres);

        Writer xmlWriter = Writer.create(xmlFileName);
        xmlWriter.write(musicBands);
    }
}
