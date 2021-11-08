package Music;

import java.util.ArrayList;

public class MusicBand {
    private String name = null, country = null;
    private int activateYear = -1;
    private ArrayList<MusicGenre> musicGenres;

    public MusicBand() {
        musicGenres = new ArrayList<>();
    }


    public void addGenre(MusicGenre musicGenre) {
        if (musicGenre == null)
            throw new NullPointerException();

        musicGenres.add(musicGenre);
    }

    public void removeGenre(MusicGenre musicGenre) {
        if (musicGenre == null)
            throw new NullPointerException();

        musicGenres.remove(musicGenre);
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

    public String getCountry() {
        if (country == null)
            throw new NullPointerException("Страна не была установлена");

        return country;
    }

    public void setCountry(String country) {
        if (country == null)
            throw new NullPointerException();

        this.country = country;
    }

    public int getActivateYear() {
        if (activateYear == -1)
            throw new NullPointerException("Год не был установлен");

        return activateYear;
    }

    public void setActivateYear(int activateYear) {
        if (activateYear < 0)
            throw new IllegalArgumentException("Неверный ввод года");

        this.activateYear = activateYear;
    }

    public ArrayList<MusicGenre> getGenres() {
        if (musicGenres == null)
            throw new NullPointerException("Жанры не были установлены");

        return musicGenres;
    }

    public void setGenres(ArrayList<MusicGenre> musicGenres) {
        if (musicGenres == null)
            throw new NullPointerException();

        this.musicGenres = musicGenres;
    }
}
