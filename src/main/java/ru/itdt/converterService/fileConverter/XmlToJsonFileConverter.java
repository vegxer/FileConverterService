package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.Logger;
import ru.itdt.converterService.fileReader.MusicBandsReader;
import ru.itdt.converterService.fileReader.Reader;
import ru.itdt.converterService.fileWriter.MusicGenresWriter;
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

public final class XmlToJsonFileConverter extends FileConverter {
    public XmlToJsonFileConverter(File file) {
        super(file);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Override
    public void convertTo(@NotNull String jsonFileName) throws XMLStreamException, IOException, ParseException,
            ParserConfigurationException {
        if (!FilenameUtils.getExtension(jsonFileName)
                .equals("json")) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживамое расширение файла %s, необходимо json", jsonFileName));
        }

        //чтение музыкальных групп из xml файла
        Collection<MusicGenre> musicGenres;
        try (Reader<Collection<MusicBand>> xmlReader = new MusicBandsReader(new FileInputStream(file))) {
            try (Logger logger = new Logger("xml reading log.txt")) {
                musicGenres = changeStructure(xmlReader.readFile());
            } catch (FileSystemException logExc) {
                System.out.printf("Ошибка логирования процесса чтения файла %s. " +
                        "Процесс продолжится без логирования%n", file.getName());
                musicGenres = changeStructure(xmlReader.readFile());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(String.format("Файл %s для чтения не найден", file.getName()));
        }

        //запись музыкальных жанров в json файл
        new File(jsonFileName).createNewFile();
        try (Writer<Collection<MusicGenre>> jsonWriter = new MusicGenresWriter(new FileOutputStream(jsonFileName))) {
            try (Logger logger = new Logger("json writing log.txt")) {
                jsonWriter.write(musicGenres);
            } catch (FileSystemException logExc) {
                System.out.printf("Ошибка логирования процесса записи файла %s. " +
                        "Процесс продолжится без логирования%n", jsonFileName);
                jsonWriter.write(musicGenres);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(String.format("Не удалось создать выходной файл %s", jsonFileName));
        }
    }

    //преобразование структуры xml файла в структуру json файла
    private Collection<MusicGenre> changeStructure(@NotNull Collection<MusicBand> musicBands) {
        Map<String, MusicGenre> musicGenres = new HashMap<>();

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
