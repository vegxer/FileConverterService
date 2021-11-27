package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;


public final class MusicGenre {
    private String genreName;
    private Collection<MusicBand> musicBands;


    @Override
    public boolean equals(Object genre) {
        if (!(genre instanceof MusicGenre musicGenre)) {
            throw new IllegalArgumentException("Необходим объект класса MusicBand");
        }

        return genreName.equals(musicGenre.getGenreName()) &&
                (getMusicBands().isEmpty() && musicGenre.getMusicBands().isEmpty() ||
                        musicBands.equals(musicGenre.getMusicBands()));
    }


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
