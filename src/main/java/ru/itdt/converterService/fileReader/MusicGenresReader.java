package ru.itdt.converterService.fileReader;

import com.google.gson.JsonParseException;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class MusicGenresReader extends Reader<List<MusicGenre>> {
    public MusicGenresReader(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public List<MusicGenre> readFile() throws IOException, ParseException {
        JSONArray jsonGenres;
        try (FileReader fileReader = new FileReader(super.fileName)) {
            Object genres = ((JSONObject) new JSONParser().parse(fileReader)).get("genres");
            if (genres == null)
                throw new JsonParseException("Key genres is not found");

            jsonGenres = (JSONArray)genres;
        }

        ArrayList<MusicGenre> genres = new ArrayList<>();
        for (Object obj : jsonGenres) {
            JSONObject jsonGenre = (JSONObject)((JSONObject)obj).get("genre");
            MusicGenre genre = new MusicGenre();
            genre.setName((String)jsonGenre.get("name"));
            genre.setMusicBands(getMusicBands(jsonGenre));

            genres.add(genre);
        }

        if (genres.isEmpty())
            throw new JsonParseException("Genres are not found");

        return genres;
    }

    private ArrayList<MusicBand> getMusicBands(JSONObject jsonGenre) {
        JSONArray jsonBands = (JSONArray)jsonGenre.get("bands");
        ArrayList<MusicBand> musicBands = new ArrayList<>();

        for (Object band : jsonBands) {
            JSONObject jsonBand = (JSONObject)band;

            MusicBand musicBand = new MusicBand();
            musicBand.setName((String)jsonBand.get("name"));
            musicBand.setCountry((String)jsonBand.get("country"));
            musicBand.setActivateYear(Integer.parseInt((String)jsonBand.get("year")));

            musicBands.add(musicBand);
        }

        if (musicBands.isEmpty())
            throw new JsonParseException("Empty array of bands");

        return musicBands;
    }
}
