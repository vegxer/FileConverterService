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
        Object genresObj;
        try {
            genresObj = ((JSONObject) new JSONParser().parse(
                    new InputStreamReader(inputStream)))
                    .get("genres");
        } catch (IOException readException) {
            throw new IOException(String.format("Ошибка создания входного потока чтения xml файла: %s",
                    readException.getMessage()), readException);
        }

        if (genresObj == null) {
            System.err.println("Ключ 'genres' не найден");
            return new ArrayList<>();
        }

        //идём по жанрам (ключ 'genre')
        JSONArray jsonGenres = (JSONArray) genresObj;
        int genresCount = 0;
        Collection<MusicGenre> genres = new ArrayList<>();
        for (Object obj : jsonGenres) {
            ++genresCount;
            JSONObject jsonGenre = (JSONObject) ((JSONObject) obj).get("genre");
            MusicGenre genre = new MusicGenre();

            Object name = jsonGenre.get("name");
            if (name == null) {
                System.err.printf("Ключ 'name' не найден в %d-м объекте 'genre'%n", genresCount);
            } else {
                genre.setGenreName((String)name);
            }

            genre.getMusicBands()
                    .addAll(getMusicBands(jsonGenre, genresCount));

            genres.add(genre);
        }

        if (genres.isEmpty()) {
            System.err.println("Жанры не найдены");
        }

        return genres;
    }

    private Collection<MusicBand> getMusicBands(JSONObject jsonGenre, int genresCount) {
        JSONArray jsonBands = (JSONArray)jsonGenre.get("bands");
        Collection<MusicBand> musicBands = new ArrayList<>();

        //считываем музыкальные группы, которые исполняют данный музыкальный жанр, в список объектов класса MusicBand
        for (Object band : jsonBands) {
            JSONObject jsonBand = (JSONObject)band;
            MusicBand musicBand = new MusicBand();

            Object name = jsonBand.get("name");
            if (name == null) {
                System.err.printf("Ключ 'name' музыкальной группы не найден в %d объекте 'genre'%n", genresCount);
            } else {
                musicBand.setBandName((String)name);
            }

            Object country = jsonBand.get("country");
            if (country == null) {
                System.err.printf("Ключ 'country' музыкальной группы не найден в %d объекте 'genre'%n", genresCount);
            } else {
                musicBand.setCountry((String)country);
            }

            Object year = jsonBand.get("year");
            if (year == null) {
                System.err.printf("Ключ 'year' музыкальной группы не найден в %d объекте 'genre'%n", genresCount);
            } else {
                musicBand.setActivateYear(Integer.parseInt((String)year));
            }

            musicBands.add(musicBand);
        }

        if (musicBands.isEmpty()) {
            System.err.printf("Музыкальные группы не найдены в %d объекте 'genre'%n", genresCount);
        }

        return musicBands;
    }
}
