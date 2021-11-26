package ru.itdt.converterService.fileConverter;


import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileReader.MusicGenresReader;
import ru.itdt.converterService.fileWriter.MusicBandsWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;

public final class JsonToXmlFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicBand>, Collection<MusicGenre>> {
    public JsonToXmlFileConverter(File file) {
        super(file);
    }

    @Override
    public void convert(@NotNull String xmlFileName) throws ParseException, IOException {
        if (!FilenameUtils.getExtension(xmlFileName)
                .equals("xml")) {
            throw new IllegalArgumentException(String.format("Неверное расширение файла %s, необходимо xml", xmlFileName));
        }

        Collection<MusicBand> musicBands = null;
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
        } catch (IOException inputException) {
            System.out.println("Ошибка закрытия входного потока");
        }

        new File(xmlFileName).createNewFile();
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
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(String.format("Не удалось создать выходной файл %s", xmlFileName));
        } catch (IOException outputException) {
            System.out.println("Ошибка закрытия выходного потока");
        } catch (ParserConfigurationException parserConfigurationException) {
            System.out.println("Ошибка конфигурации Writer'а музыкальных групп");
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
