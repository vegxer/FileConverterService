package FileWriter;

import Music.MusicBand;
import Music.MusicGenre;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MusicGenresWriter extends Writer {
    public MusicGenresWriter(String fileName) {
        super.setFileName(fileName);
    }

    @Override
    public void write(Object obj) throws IOException {
        ArrayList<MusicGenre> musicGenres = (ArrayList<MusicGenre>)obj;

        JSONObject genresObject = getGenresObject(musicGenres);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        String prettyJsonString = gson.toJson(parser.parse(genresObject.toJSONString()));

        FileWriter writer = new FileWriter(super.fileName);
        writer.write(prettyJsonString);
        writer.flush();
        writer.close();
    }

    private JSONObject getGenresObject(ArrayList<MusicGenre> musicGenres) {
        JSONArray jsonGenres = new JSONArray();
        for (MusicGenre musicGenre : musicGenres) {
            JSONObject jsonGenre = new JSONObject();
            jsonGenre.put("name", musicGenre.getName());
            jsonGenre.put("bands", getMusicBandsArray(musicGenre));

            JSONObject genreObject = new JSONObject();
            genreObject.put("genre", jsonGenre);
            jsonGenres.add(genreObject);
        }

        JSONObject genresObject = new JSONObject();
        genresObject.put("genres", jsonGenres);
        return genresObject;
    }

    private JSONArray getMusicBandsArray(MusicGenre musicGenre) {
        JSONArray genreArray = new JSONArray();

        for (MusicBand band : musicGenre.getMusicBands()) {
            JSONObject jsonBand = new JSONObject();
            jsonBand.put("year", Integer.toString(band.getActivateYear()));
            jsonBand.put("country", band.getCountry());
            jsonBand.put("name", band.getName());

            genreArray.add(jsonBand);
        }

        if (genreArray.isEmpty())
            throw new IllegalArgumentException("Пустой список жанров");

        return genreArray;
    }
}