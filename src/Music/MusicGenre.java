package Music;
import ListShell.ArrayListShell;

import java.util.ArrayList;
import java.util.Objects;

public class MusicGenre {
    private String name = null;
    private ArrayList<MusicBand> musicBands;

    public MusicGenre() {
        musicBands = new ArrayList<>();
    }

    public static ArrayList<MusicGenre> convertMusicBandsToGenres(ArrayList<MusicBand> musicBands) {
        ArrayListShell<MusicGenre> musicGenres = new ArrayListShell<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getGenres()) {
                if (!musicGenres.contains(x -> x.getName().equals(musicGenre.getName())))
                    musicGenres.add(musicGenre);

                MusicBand band = new MusicBand();
                band.setName(musicBand.getName());
                band.setActivateYear(musicBand.getActivateYear());
                band.setCountry(musicBand.getCountry());
                Objects.requireNonNull(musicGenres.get(x -> x.getName().equals(musicGenre.getName()))).addMusicBand(band);
            }
        }

        return musicGenres;
    }

    public void addMusicBand(MusicBand musicBand) {
        if (musicBand == null)
            throw new NullPointerException();

        musicBands.add(musicBand);
    }

    public void removeMusicBand(MusicBand musicBand) {
        if (musicBand == null)
            throw new NullPointerException();

        musicBands.remove(musicBand);
    }

    public String getName() {
        if (name == null)
            throw new NullPointerException("Название не было установлено");

        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException();

        this.name = name;
    }

    public ArrayList<MusicBand> getMusicBands() {
        if (musicBands == null)
            throw new NullPointerException("Группы не были установлены");

        return musicBands;
    }

    public void setMusicBands(ArrayList<MusicBand> musicBands) {
        if (musicBands == null)
            throw new NullPointerException();

        this.musicBands = musicBands;
    }
}
