package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class MusicGenre {
    private String genreName;
    private Set<MusicBand> musicBands;


    @Override
    public boolean equals(Object genre) {
        if (!(genre instanceof MusicGenre musicGenre)) {
            throw new IllegalArgumentException("Необходим объект класса MusicBand");
        }

        return genreName.equals(musicGenre.getGenreName()) && musicBands.equals(musicGenre.getMusicBands());
    }


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(@NotNull String genreName) {
        this.genreName = genreName;
    }

    public Set<MusicBand> getMusicBands() {
        if (musicBands == null) {
            musicBands = new HashSet<>();
        }

        return musicBands;
    }
}
