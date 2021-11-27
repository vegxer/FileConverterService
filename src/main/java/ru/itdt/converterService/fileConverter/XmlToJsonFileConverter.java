package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.fileReader.MusicBandsReader;
import ru.itdt.converterService.fileWriter.MusicGenresWriter;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;

public final class XmlToJsonFileConverter extends FileConverter
        implements StructureChanger<Collection<MusicGenre>, Collection<MusicBand>> {
    public XmlToJsonFileConverter(File file) {
        super(file);
    }

    @Override
    public void convert(@NotNull String jsonFileName) throws XMLStreamException, IOException {
        if (!FilenameUtils.getExtension(jsonFileName)
                .equals("json")) {
            throw new IllegalArgumentException(String.format("Неверное расширение файла %s, необходимо json", jsonFileName));
        }

        Collection<MusicGenre> musicGenres = null;
        try (MusicBandsReader xmlReader = new MusicBandsReader(new FileInputStream(file))) {
            Logger logger = null;
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
        }

        new File(jsonFileName).createNewFile();
        try (MusicGenresWriter jsonWriter = new MusicGenresWriter(new FileOutputStream(jsonFileName))) {
            Logger logger = null;
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
        }
    }

    @Override
    public Collection<MusicGenre> changeStructure(@NotNull Collection<MusicBand> musicBands) {
        HashMap<String, MusicGenre> musicGenres = new HashMap<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getMusicGenres()) {
                if (!musicGenres.containsKey(musicGenre.getGenreName())) {
                    musicGenres.put(musicGenre.getGenreName(), musicGenre);
                }

                /*MusicBand band = new MusicBand();
                band.setBandName(musicBand.getBandName());
                band.setActivateYear(musicBand.getActivateYear());
                band.setCountry(musicBand.getCountry());*/
                MusicGenre updatedMusicGenre = musicGenres.get(musicGenre.getGenreName());
                updatedMusicGenre.getMusicBands().add(musicBand);
                musicGenres.put(musicGenre.getGenreName(), updatedMusicGenre);
            }
        }

        return musicGenres.values();
    }
}
