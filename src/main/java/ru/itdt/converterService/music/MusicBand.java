package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.validators.Validator;
import ru.itdt.converterService.validators.YearValidator;

import java.util.ArrayList;
import java.util.Collection;


public final class MusicBand {
    private String bandName, country;
    private Integer activateYear;
    private Collection<MusicGenre> musicGenres;
    private static final Validator<Integer> yearValidator = new YearValidator();


    public String getBandName() {
        return bandName;
    }

    public void setBandName(@NotNull String bandName) {
        this.bandName = bandName;
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
        if (!yearValidator.validate(activateYear))
            throw new IllegalArgumentException("Год должен быть неотрицательным числом");

        this.activateYear = activateYear;
    }

    public Collection<MusicGenre> getMusicGenres() {
        if (musicGenres == null) {
            musicGenres = new ArrayList<>();
        }

        return musicGenres;
    }
}
