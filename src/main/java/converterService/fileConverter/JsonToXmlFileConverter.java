package converterService.fileConverter;


import converterService.FileExtension;
import converterService.fileReader.MusicGenresReader;
import converterService.fileWriter.MusicBandsWriter;
import converterService.music.MusicBand;
import converterService.music.MusicGenre;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class JsonToXmlFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicBand>, ArrayList<MusicGenre>> {
    public JsonToXmlFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String xmlFileName)
            throws IOException, ParseException, ParserConfigurationException, TransformerException {
        if (!FileExtension.getExtension(xmlFileName).equals("xml"))
            throw new IllegalArgumentException("Неверное расширение файла " + xmlFileName);

        MusicGenresReader jsonReader = new MusicGenresReader(fileName);
        Collection<MusicBand> musicBands = changeStructure(jsonReader.readFile());

        MusicBandsWriter xmlWriter = new MusicBandsWriter(xmlFileName);
        xmlWriter.write(musicBands);
    }

    @Override
    public Collection<MusicBand> changeStructure(ArrayList<MusicGenre> musicGenres) {
        HashMap<String, MusicBand> musicBands = new HashMap<>();

        for (MusicGenre musicGenre : musicGenres) {
            for (MusicBand musicBand : musicGenre.getMusicBands()) {
                if (!musicBands.containsKey(musicBand.getName()))
                    musicBands.put(musicBand.getName(), musicBand);

                MusicGenre genre = new MusicGenre();
                genre.setName(musicGenre.getName());
                MusicBand updatedMusicBand = musicBands.get(musicBand.getName());
                updatedMusicBand.addGenre(genre);
                musicBands.put(musicBand.getName(), updatedMusicBand);
            }
        }

        return musicBands.values();
    }
}
