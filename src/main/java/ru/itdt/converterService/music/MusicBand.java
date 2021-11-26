package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.Validators.Validator;
import ru.itdt.converterService.Validators.YearValidator;

import java.util.ArrayList;

public final class MusicBand {
    private String name, country;
    private Integer activateYear;
    private final Validator<Integer> yearValidator;
    private ArrayList<MusicGenre> musicGenres;

    public MusicBand(@NotNull Validator<Integer> yearValidator) {
        this.yearValidator = yearValidator;
    }

    public MusicBand() {
        this(new YearValidator());
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

    public void setActivateYear(int activateYear) {
        if (!yearValidator.isValid(activateYear))
            throw new IllegalArgumentException("Год должен быть неотрицательным числом");

        this.activateYear = activateYear;
    }

    public ArrayList<MusicGenre> getGenres() {
        return musicGenres;
    }

    public void setGenres(@NotNull ArrayList<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }
}
