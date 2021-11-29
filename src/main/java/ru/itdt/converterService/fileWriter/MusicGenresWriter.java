package ru.itdt.converterService.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.itdt.converterService.Logger;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;


public final class MusicGenresWriter extends Writer<Collection<MusicGenre>> {
    public MusicGenresWriter(FileOutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(@NotNull Collection<MusicGenre> musicGenres) throws IOException {
        JSONObject genresObject = getGenresObject(musicGenres);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            //noinspection deprecation
            writer.write(gson.toJson(
                    new JsonParser().parse(genresObject.toJSONString())));
        } catch (IOException writeException) {
            throw new IOException(String.format("Ошибка записи успешно созданного json документа в файл: %s",
                    writeException.getMessage()), writeException);
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject getGenresObject(Collection<MusicGenre> musicGenres) {
        //создание json объекта, который содержит набор музыкальных жанров
        JSONArray jsonGenres = new JSONArray();
        int genresCount = 0;
        for (MusicGenre musicGenre : musicGenres) {
            ++genresCount;
            JSONObject jsonGenre = new JSONObject();

            if (musicGenre.getGenreName() == null) {
                Logger.addError(
                        String.format("Значение 'name' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonGenre.put("name", "");
            } else {
                jsonGenre.put("name", musicGenre.getGenreName());
            }

            jsonGenre.put("bands", getMusicBandsArray(musicGenre, genresCount));

            JSONObject genreObject = new JSONObject();
            genreObject.put("genre", jsonGenre);
            jsonGenres.add(genreObject);
        }

        JSONObject genresObject = new JSONObject();
        genresObject.put("genres", jsonGenres);
        return genresObject;
    }

    @SuppressWarnings("unchecked")
    private JSONArray getMusicBandsArray(MusicGenre musicGenre, int genresCount) {
        JSONArray genreArray = new JSONArray();
        if (musicGenre.getMusicBands().isEmpty()) {
            Logger.addError(
                    String.format("Значение 'bands' не было установлено в %d-м объекте 'genre'", genresCount));
        }

        //получение набора музыкальных групп, которые исполняют данный жанр
        for (MusicBand band : musicGenre.getMusicBands()) {
            JSONObject jsonBand = new JSONObject();

            if (band.getActivateYear() == null) {
                Logger.addError(
                        String.format("Значение 'year' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonBand.put("year", "");
            } else {
                jsonBand.put("year", Integer.toString(band.getActivateYear()));
            }

            if (band.getCountry() == null) {
                Logger.addError(
                        String.format("Значение 'country' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonBand.put("country", "");
            } else {
                jsonBand.put("country", band.getCountry());
            }

            if (band.getBandName() == null) {
                Logger.addError(
                        String.format("Значение 'name' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonBand.put("name", "");
            } else {
                jsonBand.put("name", band.getBandName());
            }

            genreArray.add(jsonBand);
        }

        return genreArray;
    }
}
