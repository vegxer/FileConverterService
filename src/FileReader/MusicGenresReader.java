package FileReader;

import Music.MusicBand;
import Music.MusicGenre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicGenresReader extends Reader<ArrayList<MusicGenre>> {
    public MusicGenresReader(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public ArrayList<MusicGenre> readFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonGenres = (JSONArray)(((JSONObject)parser.parse(
                new FileReader(super.fileName))).get("genres"));
        ArrayList<MusicGenre> genres = new ArrayList<>();

        for (Object obj : jsonGenres) {
            JSONObject jsonGenre = (JSONObject)((JSONObject)obj).get("genre");
            MusicGenre genre = new MusicGenre();
            genre.setName((String)jsonGenre.get("name"));
            genre.setMusicBands(getMusicBands(jsonGenre));

            genres.add(genre);
        }

        if (genres.isEmpty())
            throw new IllegalArgumentException("Genres are not found");

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
            throw new IllegalArgumentException("Empty array of bands");

        return musicBands;
    }
}
