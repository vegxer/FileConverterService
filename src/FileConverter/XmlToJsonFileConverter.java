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

public class XmlToJsonFileConverter extends FileConverter {
    public XmlToJsonFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String jsonFileName)
            throws IOException, XMLStreamException, XMLParseException,
            ParseException, ParserConfigurationException, TransformerException {
        if (!FileExtension.getExtension(jsonFileName).equals("json"))
            throw new IllegalArgumentException("Неверное расширение файла");

        Reader xmlReader = Reader.create(super.fileName);

        ArrayList<MusicBand> musicBands = (ArrayList<MusicBand>)xmlReader.readFile();
        ArrayList<MusicGenre> musicGenres = MusicGenre.convertMusicBandsToGenres(musicBands);

        Writer jsonWriter = Writer.create(jsonFileName);
        jsonWriter.write(musicGenres);
    }
}
