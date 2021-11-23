package fileConverter;


import fileExtension.FileExtension;
import fileReader.MusicGenresReader;
import fileWriter.MusicBandsWriter;
import listShell.ArrayListShell;
import music.MusicBand;
import music.MusicGenre;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JsonToXmlFileConverter extends FileConverter
        implements StructureChanger<ArrayList<MusicBand>, ArrayList<MusicGenre>> {
    public JsonToXmlFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String xmlFileName)
            throws IOException, ParseException, ParserConfigurationException, TransformerException {
        if (!FileExtension.getExtension(xmlFileName).equals("xml"))
            throw new IllegalArgumentException("Неверное расширение файла " + xmlFileName);

        MusicGenresReader jsonReader = new MusicGenresReader(fileName);
        ArrayList<MusicBand> musicBands = changeStructure(jsonReader.readFile());

        MusicBandsWriter xmlWriter = new MusicBandsWriter(xmlFileName);
        xmlWriter.write(musicBands);
    }

    @Override
    public ArrayList<MusicBand> changeStructure(ArrayList<MusicGenre> musicGenres) {
        ArrayListShell<MusicBand> musicBands = new ArrayListShell<>();

        for (MusicGenre musicGenre : musicGenres) {
            for (MusicBand musicBand : musicGenre.getMusicBands()) {
                if (!musicBands.contains(x -> x.getName().equals(musicBand.getName())))
                    musicBands.add(musicBand);

                MusicGenre genre = new MusicGenre();
                genre.setName(musicGenre.getName());
                Objects.requireNonNull(musicBands.get(x -> x.getName().equals(musicBand.getName()))).addGenre(genre);
            }
        }

        return musicBands;
    }
}
