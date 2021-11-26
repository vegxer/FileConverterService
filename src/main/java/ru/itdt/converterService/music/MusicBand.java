package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.Validators.ValidationResult;
import ru.itdt.converterService.Validators.Validator;
import ru.itdt.converterService.Validators.YearValidator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public final class MusicBand {
    private String name, country;
    private Integer activateYear;
    private List<MusicGenre> musicGenres;


    public void addGenre(@NotNull MusicGenre musicGenre) {
        musicGenres.add(musicGenre);
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
        ValidationResult result = new YearValidator().validate(activateYear);
        if (!result.isValid())
            throw new IllegalArgumentException(result.exceptionMessage());

        this.activateYear = activateYear;
    }

    public List<MusicGenre> getGenres() {
        if (musicGenres == null)
            musicGenres = new ArrayList<>();

        return musicGenres;
    }
}
