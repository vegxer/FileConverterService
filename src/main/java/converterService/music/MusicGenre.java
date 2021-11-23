package converterService.music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MusicGenre {
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
