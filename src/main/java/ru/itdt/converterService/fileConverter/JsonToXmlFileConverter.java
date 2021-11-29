package ru.itdt.converterService.fileConverter;


import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.Logger;
import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileReader.Reader;
import ru.itdt.converterService.fileWriter.MusicBandsWriter;
import ru.itdt.converterService.fileWriter.Writer;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class JsonToXmlFileConverter extends FileConverter {
    public JsonToXmlFileConverter(File file) {
        super(file);
    }

    @Override
    public void convertTo(@NotNull String xmlFileName) throws ParseException, XMLStreamException, ParserConfigurationException, IOException {
        if (!FilenameUtils.getExtension(xmlFileName)
                .equals("xml")) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживаемое расширение файла %s, необходимо xml", xmlFileName));
        }

        //чтение музыкальных жанров из json файла
        Collection<MusicBand> musicBands;
        try (Reader<Collection<MusicGenre>> jsonReader = new MusicGenresReader(new FileInputStream(file))) {
            try (Logger logger = new Logger("json reading log.txt")) {
                musicBands = changeStructure(jsonReader.readFile());
            } catch (FileSystemException logExc) {
                System.out.printf("Ошибка логирования процесса чтения файла %s. " +
                        "Процесс продолжится без логирования%n", file.getName());
                musicBands = changeStructure(jsonReader.readFile());
            }
        }

        //запись музыкальных групп в xml файл
        try (Writer<Collection<MusicBand>> xmlWriter = new MusicBandsWriter(new FileOutputStream(xmlFileName))) {
            try (Logger logger = new Logger("xml writing log.txt")) {
                xmlWriter.write(musicBands);
            } catch (FileSystemException logExc) {
                System.out.printf("Ошибка логирования процесса записи файла %s. " +
                        "Процесс продолжится без логирования%n", xmlFileName);
                xmlWriter.write(musicBands);
            }
        }
    }

    //преобразование структуры json файла в структуру xml файла
    private Collection<MusicBand> changeStructure(@NotNull Collection<MusicGenre> musicGenres) {
        Map<String, MusicBand> musicBands = new HashMap<>();

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
