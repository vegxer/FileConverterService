package ru.itdt.converterService.fileConverter;


import org.apache.commons.io.FilenameUtils;
import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileWriter.MusicBandsWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

public final class JsonToXmlFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicBand>, Collection<MusicGenre>> {
    public JsonToXmlFileConverter(File file) {
        super(file);
    }

    @Override
    public void convert(@NotNull String xmlFileName)
            throws IOException, ParseException, ParserConfigurationException, TransformerException {
        if (!FilenameUtils.getExtension(xmlFileName)
                .equals("xml")) {
            throw new IllegalArgumentException(String.format("Неверное расширение файла %s", xmlFileName));
        }

        Collection<MusicBand> musicBands;
        try (MusicGenresReader jsonReader = new MusicGenresReader(new FileInputStream(file))) {
            Logger logger = null;
            try {
                logger = new Logger(new File("json reading log.txt"));
            } catch (IOException logExc) {
                System.out.println("Ошибка логирования процесса чтения. Процесс продолжится без логирования");
            }
            finally {
                musicBands = changeStructure(jsonReader.readFile());
                if (logger != null) {
                    logger.close();
                }
            }
        }

        try (MusicBandsWriter xmlWriter = new MusicBandsWriter(new FileOutputStream(xmlFileName))) {
            Logger logger = null;
            try {
                logger = new Logger(new File("xml writing log.txt"));
            } catch (IOException logExc) {
                System.out.println("Ошибка логирования процесса записи. Процесс продолжится без логирования");
            }
            finally {
                xmlWriter.write(musicBands);
                if (logger != null) {
                    logger.close();
                }
            }
        }
    }

    @Override
    public Collection<MusicBand> changeStructure(@NotNull Collection<MusicGenre> musicGenres) {
        HashMap<String, MusicBand> musicBands = new HashMap<>();

        for (MusicGenre musicGenre : musicGenres) {
            for (MusicBand musicBand : musicGenre.getMusicBands()) {
                if (!musicBands.containsKey(musicBand.getBandName())) {
                    musicBands.put(musicBand.getBandName(), musicBand);
                }

                MusicGenre genre = new MusicGenre();
                genre.setGenreName(musicGenre.getGenreName());
                MusicBand updatedMusicBand = musicBands.get(musicBand.getBandName());
                updatedMusicBand.getMusicGenres().add(genre);
                musicBands.put(musicBand.getBandName(), updatedMusicBand);
            }
        }

        return musicBands.values();
    }
}
