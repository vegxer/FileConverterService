package ru.itdt.converterService.fileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public final class MusicGenresReader extends Reader<Collection<MusicGenre>> {
    public MusicGenresReader(FileInputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Collection<MusicGenre> readFile() throws ParseException, IOException {
        Object genresObj = ((JSONObject) new JSONParser().parse(new InputStreamReader(inputStream)))
                .get("genres");
        if (genresObj == null) {
            System.err.println("Ключ 'genres' не найден");
            return new ArrayList<>();
        }

        JSONArray jsonGenres = (JSONArray) genresObj;
        ArrayList<MusicGenre> genres = new ArrayList<>();
        for (Object obj : jsonGenres) {
            JSONObject jsonGenre = (JSONObject) ((JSONObject) obj).get("genre");
            MusicGenre genre = new MusicGenre();

            Object name = jsonGenre.get("name");
            if (name == null) {
                System.err.println("Ключ 'name' не найден");
            } else {
                genre.setGenreName((String)name);
            }

            genre.getMusicBands().addAll(getMusicBands(jsonGenre));

            genres.add(genre);
        }

        if (genres.isEmpty()) {
            System.err.println("Жанры не найдены");
        }

        return genres;
    }

    private ArrayList<MusicBand> getMusicBands(JSONObject jsonGenre) {
        JSONArray jsonBands = (JSONArray)jsonGenre.get("bands");
        ArrayList<MusicBand> musicBands = new ArrayList<>();

        for (Object band : jsonBands) {
            JSONObject jsonBand = (JSONObject)band;
            MusicBand musicBand = new MusicBand();

            Object name = jsonBand.get("name");
            if (name == null) {
                System.err.println("Ключ 'name' не найден");
            } else {
                musicBand.setBandName((String)name);
            }

            Object country = jsonBand.get("country");
            if (country == null) {
                System.err.println("Ключ 'country' не найден");
            } else {
                musicBand.setCountry((String)country);
            }

            Object year = jsonBand.get("year");
            if (year == null) {
                System.err.println("Ключ 'year' не найден");
            } else {
                musicBand.setActivateYear(Integer.parseInt((String)year));
            }

            musicBands.add(musicBand);
        }

        if (musicBands.isEmpty()) {
            System.err.println("Музыкальные группы не найдены");
        }

        return musicBands;
    }
}
