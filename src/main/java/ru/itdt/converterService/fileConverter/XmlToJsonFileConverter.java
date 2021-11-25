package ru.itdt.converterService.fileConverter;

import ru.itdt.converterService.fileReader.MusicBandsReader;
import ru.itdt.converterService.fileWriter.MusicGenresWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class XmlToJsonFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicGenre>, List<MusicBand>> {
    public XmlToJsonFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(@NotNull String jsonFileName)
            throws IOException, XMLStreamException, XMLParseException {
        if (!FilenameUtils.getExtension(jsonFileName).equals("json"))
            throw new IllegalArgumentException("Неверное расширение файла " + jsonFileName);

        MusicBandsReader jsonReader = new MusicBandsReader(fileName);
        Collection<MusicGenre> musicGenres = changeStructure(jsonReader.readFile());

        MusicGenresWriter jsonWriter = new MusicGenresWriter(jsonFileName);
        jsonWriter.write(musicGenres);
    }

    @Override
    public Collection<MusicGenre> changeStructure(@NotNull List<MusicBand> musicBands) {
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
