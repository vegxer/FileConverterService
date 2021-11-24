package converterService.fileConverter;

import converterService.fileReader.MusicBandsReader;
import converterService.fileWriter.MusicGenresWriter;
import converterService.music.MusicBand;
import converterService.music.MusicGenre;
import org.apache.commons.io.FilenameUtils;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class XmlToJsonFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicGenre>, ArrayList<MusicBand>> {
    public XmlToJsonFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String jsonFileName)
            throws IOException, XMLStreamException, XMLParseException {
        if (!FilenameUtils.getExtension(jsonFileName).equals("json"))
            throw new IllegalArgumentException("Неверное расширение файла " + jsonFileName);

        MusicBandsReader jsonReader = new MusicBandsReader(fileName);
        Collection<MusicGenre> musicGenres = changeStructure(jsonReader.readFile());

        MusicGenresWriter jsonWriter = new MusicGenresWriter(jsonFileName);
        jsonWriter.write(musicGenres);
    }

    @Override
    public Collection<MusicGenre> changeStructure(ArrayList<MusicBand> musicBands) {
        HashMap<String, MusicGenre> musicGenres = new HashMap<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getGenres()) {
                if (!musicGenres.containsKey(musicGenre.getName()))
                    musicGenres.put(musicGenre.getName(), musicGenre);

                MusicBand band = new MusicBand();
                band.setName(musicBand.getName());
                band.setActivateYear(musicBand.getActivateYear());
                band.setCountry(musicBand.getCountry());
                MusicGenre updatedMusicGenre = musicGenres.get(musicGenre.getName());
                updatedMusicGenre.addMusicBand(band);
                musicGenres.put(musicGenre.getName(), updatedMusicGenre);
            }
        }

        return musicGenres.values();
    }
}
