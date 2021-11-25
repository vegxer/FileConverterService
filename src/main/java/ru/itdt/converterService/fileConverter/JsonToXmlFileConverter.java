package ru.itdt.converterService.fileConverter;


import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileWriter.MusicBandsWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class JsonToXmlFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicBand>, List<MusicGenre>> {
    public JsonToXmlFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(@NotNull String xmlFileName)
            throws IOException, ParseException, ParserConfigurationException, TransformerException {
        if (!FilenameUtils.getExtension(xmlFileName)
                .equals("xml")) {
            throw new IllegalArgumentException(String.format("Неверное расширение файла %s", xmlFileName));
        }

        MusicGenresReader jsonReader = new MusicGenresReader(fileName);
        Collection<MusicBand> musicBands = changeStructure(jsonReader.readFile());

        MusicBandsWriter xmlWriter = new MusicBandsWriter(xmlFileName);
        xmlWriter.write(musicBands);
    }

    @Override
    public Collection<MusicBand> changeStructure(@NotNull List<MusicGenre> musicGenres) {
        HashMap<String, MusicBand> musicBands = new HashMap<>();

        for (MusicGenre musicGenre : musicGenres) {
            for (MusicBand musicBand : musicGenre.getMusicBands()) {
                if (!musicBands.containsKey(musicBand.getName())) {
                    musicBands.put(musicBand.getName(), musicBand);
                }

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
