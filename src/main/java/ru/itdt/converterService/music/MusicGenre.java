package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class MusicGenre {
    private String name;
    private List<MusicBand> musicBands;


    public void addMusicBand(@NotNull MusicBand musicBand) {
        musicBands.add(musicBand);
    }

    public void removeMusicBand(@NotNull MusicBand musicBand) {
        musicBands.remove(musicBand);
    }

    @Override
    public boolean equals(Object genre) {
        if (!(genre instanceof MusicGenre musicGenre)) {
            throw new IllegalArgumentException("Необходим объект класса MusicBand");
        }

        return name.equals(musicGenre.getName()) && musicBands.equals(musicGenre.getMusicBands());
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public List<MusicBand> getMusicBands() {
        if (musicBands == null)
            musicBands = new ArrayList<>();

        return musicBands;
    }
}
