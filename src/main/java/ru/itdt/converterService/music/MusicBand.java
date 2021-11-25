package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Min;
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
        if (!(band instanceof MusicBand musicBand)) {
            throw new IllegalArgumentException("Необходим объект класса MusicBand");
        }

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

    public void setActivateYear(@Min(0) int activateYear) {
        this.activateYear = activateYear;
    }

    public ArrayList<MusicGenre> getGenres() {
        return musicGenres;
    }

    public void setGenres(@NotNull ArrayList<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }
}
