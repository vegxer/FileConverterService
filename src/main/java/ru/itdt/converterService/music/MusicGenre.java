package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;


public final class MusicGenre {
    private String genreName;
    private Collection<MusicBand> musicBands;


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(@NotNull String genreName) {
        this.genreName = genreName;
    }

    public Collection<MusicBand> getMusicBands() {
        if (musicBands == null) {
            musicBands = new ArrayList<>();
        }

        return musicBands;
    }
}
