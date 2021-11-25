package ru.itdt.converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class MusicGenre {
    private String name;
    private ArrayList<MusicBand> musicBands;

    public MusicGenre() {
        musicBands = new ArrayList<>();
    }


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

    public ArrayList<MusicBand> getMusicBands() {
        return musicBands;
    }

    public void setMusicBands(@NotNull ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }
}
