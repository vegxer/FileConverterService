package ru.itdt.converterService.file.converter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.file.reader.MusicBandsReader;
import ru.itdt.converterService.file.writer.MusicGenresWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public final class XmlToJsonFileConverter extends FileConverter {
    public XmlToJsonFileConverter(File file) {
        super(file);
    }

    @Override
    public void convertTo(@NotNull String jsonFileName) throws XMLStreamException, IOException {
        if (!"json".equals(FilenameUtils.getExtension(jsonFileName))) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживамое расширение файла %s, необходимо json", jsonFileName));
        }

        //чтение музыкальных групп из xml файла
        Collection<MusicGenre> musicGenres;
        try (MusicBandsReader xmlReader = new MusicBandsReader(new FileInputStream(file))) {
            musicGenres = changeStructure(xmlReader.readFile());
        }

        //запись музыкальных жанров в json файл
        try (MusicGenresWriter jsonWriter = new MusicGenresWriter(new FileOutputStream(jsonFileName))) {
            jsonWriter.write(musicGenres);
        }
    }

    //преобразование структуры xml файла в структуру json файла
    private Collection<MusicGenre> changeStructure(@NotNull Collection<MusicBand> musicBands) {
        HashMap<String, MusicGenre> musicGenres = new HashMap<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getMusicGenres()) {
                if (!musicGenres.containsKey(musicGenre.getGenreName())) {
                    musicGenres.put(musicGenre.getGenreName(), musicGenre);
                }

                MusicGenre updatedMusicGenre = musicGenres.get(musicGenre.getGenreName());
                updatedMusicGenre.getMusicBands()
                        .add(musicBand);
                musicGenres.put(musicGenre.getGenreName(), updatedMusicGenre);
            }
        }

        return musicGenres.values();
    }
}
