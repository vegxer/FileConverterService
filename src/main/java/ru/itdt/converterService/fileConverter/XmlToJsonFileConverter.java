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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class XmlToJsonFileConverter extends FileConverter {
    public XmlToJsonFileConverter(File file) {
        super(file);
    }

    @Override
    public void convert(@NotNull String jsonFileName) throws XMLStreamException, IOException, ParseException {
        if (!FilenameUtils.getExtension(jsonFileName)
                .equals("json")) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживамое расширение файла %s, необходимо json", jsonFileName));
        }

        Collection<MusicGenre> musicGenres = null;
        try (Reader<Collection<MusicBand>> xmlReader = new MusicBandsReader(new FileInputStream(file))) {
            Logger logger = null;
            //noinspection TryFinallyCanBeTryWithResources
            try {
                logger = new Logger(new File("xml reading log.txt"));
            } catch (IOException logExc) {
                System.out.println("Ошибка логирования процесса чтения. Процесс продолжится без логирования");
            }
            finally {
                musicGenres = changeStructure(xmlReader.readFile());
                if (logger != null) {
                    logger.close();
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(String.format("Файл %s не найден", file.getName()));
        } catch (IOException inputException) {
            System.out.println("Ошибка закрытия входного потока чтения музыкальных групп");
        } catch (XMLStreamException xmlStreamException) {
            throw new XMLStreamException("Ошибка чтения XML потока", xmlStreamException);
        }

        //noinspection ResultOfMethodCallIgnored
        new File(jsonFileName).createNewFile();
        try (Writer<Collection<MusicGenre>> jsonWriter = new MusicGenresWriter(new FileOutputStream(jsonFileName))) {
            Logger logger = null;
            //noinspection TryFinallyCanBeTryWithResources
            try {
                logger = new Logger(new File("json writing log.txt"));
            } catch (IOException logExc) {
                System.out.println("Ошибка логирования процесса записи. Процесс продолжится без логирования");
            }
            finally {
                jsonWriter.write(musicGenres);
                if (logger != null) {
                    logger.close();
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(String.format("Не удалось создать выходной файл %s", jsonFileName));
        } catch (IOException outputCloseException) {
            System.out.println("Ошибка закрытия выходного потока записи музыкальных жанров");
        } catch (ParserConfigurationException parserConfigurationException) {
            System.out.println("Ошибка конфигурации Writer'а жанров");
        }
    }

    private Collection<MusicGenre> changeStructure(@NotNull Collection<MusicBand> musicBands) {
        Map<String, MusicGenre> musicGenres = new HashMap<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getMusicGenres()) {
                if (!musicGenres.containsKey(musicGenre.getGenreName())) {
                    musicGenres.put(musicGenre.getGenreName(), musicGenre);
                }

                MusicGenre updatedMusicGenre = musicGenres.get(musicGenre.getGenreName());
                updatedMusicGenre.getMusicBands().add(musicBand);
                musicGenres.put(musicGenre.getGenreName(), updatedMusicGenre);
            }
        }

        return musicGenres.values();
    }
}
