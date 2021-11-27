package ru.itdt.converterService.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    public void write(@NotNull Collection<MusicGenre> musicGenres) {
        JSONObject genresObject = getGenresObject(musicGenres);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        java.io.Writer writer = new OutputStreamWriter(outputStream);
        try {
            writer.write(gson.toJson(new JsonParser().parse(genresObject.toJSONString())));
            writer.flush();
        } catch (IOException writeException) {
            System.out.println("Ошибка Writer'а при записи в json файл");
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject getGenresObject(Collection<MusicGenre> musicGenres) {
        JSONArray jsonGenres = new JSONArray();
        int genresCount = 0;
        for (MusicGenre musicGenre : musicGenres) {
            ++genresCount;
            JSONObject jsonGenre = new JSONObject();

            if (musicGenre.getGenreName() == null) {
                System.err.println(
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
            System.err.println(
                    String.format("Значение 'bands' не было установлено в %d-м объекте 'genre'", genresCount));
        }

        for (MusicBand band : musicGenre.getMusicBands()) {
            JSONObject jsonBand = new JSONObject();

            if (band.getActivateYear() == null) {
                System.err.println(
                        String.format("Значение 'year' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonBand.put("year", "");
            } else {
                jsonBand.put("year", Integer.toString(band.getActivateYear()));
            }

            if (band.getCountry() == null) {
                System.err.println(
                        String.format("Значение 'country' не было установлено в %d-м объекте 'genre'", genresCount));
                jsonBand.put("country", "");
            } else {
                jsonBand.put("country", band.getCountry());
            }

            if (band.getBandName() == null) {
                System.err.println(
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
