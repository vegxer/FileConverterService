package ru.itdt.converterService.fileConverter;


import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileWriter.MusicBandsWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public final class JsonToXmlFileConverter extends FileConverter {
    public JsonToXmlFileConverter(File file) {
        super(file);
    }

    @Override
    public void convertTo(@NotNull String xmlFileName) throws ParseException, ParserConfigurationException, IOException {
        if (!FilenameUtils.getExtension(xmlFileName)
                .equals("xml")) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживаемое расширение файла %s, необходимо xml", xmlFileName));
        }

        //чтение музыкальных жанров из json файла
        Collection<MusicBand> musicBands;
        try (MusicGenresReader jsonReader = new MusicGenresReader(new FileInputStream(file))) {
            musicBands = changeStructure(jsonReader.readFile());
        }

        //запись музыкальных групп в xml файл
        try (MusicBandsWriter xmlWriter = new MusicBandsWriter(new FileOutputStream(xmlFileName))) {
            xmlWriter.write(musicBands);
        }
    }

    //преобразование структуры json файла в структуру xml файла
    private Collection<MusicBand> changeStructure(@NotNull Collection<MusicGenre> musicGenres) {
        HashMap<String, MusicBand> musicBands = new HashMap<>();

        for (MusicGenre musicGenre : musicGenres) {
            for (MusicBand musicBand : musicGenre.getMusicBands()) {
                if (!musicBands.containsKey(musicBand.getBandName())) {
                    musicBands.put(musicBand.getBandName(), musicBand);
                }

                MusicBand updatedMusicBand = musicBands.get(musicBand.getBandName());
                updatedMusicBand.getMusicGenres()
                        .add(musicGenre);
                musicBands.put(musicBand.getBandName(), updatedMusicBand);
            }
        }

        return musicBands.values();
    }
}
