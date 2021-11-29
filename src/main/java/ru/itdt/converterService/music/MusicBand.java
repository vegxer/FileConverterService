package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;


public final class MusicBand {
    private String bandName, country;
    private Integer activateYear;
    private Set<MusicGenre> musicGenres;


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
        if (!validateYear(activateYear))
            throw new IllegalArgumentException("Год основания музыкальной группы должен быть неотрицательным числом");

        this.activateYear = activateYear;
    }

    private boolean validateYear(int year) {
        return year >= 0;
    }

    public Set<MusicGenre> getMusicGenres() {
        if (musicGenres == null) {
            musicGenres = new HashSet<>();
        }

        return musicGenres;
    }
}
