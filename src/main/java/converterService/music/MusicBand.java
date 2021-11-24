package converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class MusicBand {
    private String name, country;
    private Integer activateYear;
    private ArrayList<MusicGenre> musicGenres;

    public MusicBand() {
        musicGenres = new ArrayList<>();
    }


    public void addGenre(@NotNull MusicGenre musicGenre) {
        musicGenres.add(musicGenre);
    }

    public void removeGenre(@NotNull MusicGenre musicGenre) {
        musicGenres.remove(musicGenre);
    }

    @Override
    public boolean equals(Object band) {
        if (!(band instanceof MusicBand musicBand))
            throw new IllegalArgumentException("Необходим объект класса MusicBand");

        return name.equals(musicBand.getName()) && country.equals(musicBand.getCountry())
                && activateYear.equals(musicBand.getActivateYear()) && musicGenres.equals(musicBand.getGenres());
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    public Integer getActivateYear() {
        return activateYear;
    }

    public void setActivateYear(int activateYear) {
        if (activateYear < 0)
            throw new IllegalArgumentException("Неверный ввод года, необходимо неотрицательное число");

        this.activateYear = activateYear;
    }

    public ArrayList<MusicGenre> getGenres() {
        return musicGenres;
    }

    public void setGenres(@NotNull ArrayList<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }
}
