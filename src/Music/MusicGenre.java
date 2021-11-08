package Music;

import java.util.ArrayList;

public class MusicGenre {
    private String name = null;
    private ArrayList<MusicBand> musicBands;

    public MusicGenre() {
        musicBands = new ArrayList<>();
    }


    public void addMusicBand(MusicBand musicBand) {
        if (musicBand == null)
            throw new NullPointerException();

        musicBands.add(musicBand);
    }

    public void removeMusicBand(MusicBand musicBand) {
        if (musicBand == null)
            throw new NullPointerException();

        musicBands.remove(musicBand);
    }

    public String getName() {
        if (name == null)
            throw new NullPointerException("Название не было установлено");

        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException();

        this.name = name;
    }

    public ArrayList<MusicBand> getMusicBands() {
        if (musicBands == null)
            throw new NullPointerException("Группы не были установлены");

        return musicBands;
    }

    public void setMusicBands(ArrayList<MusicBand> musicBands) {
        if (musicBands == null)
            throw new NullPointerException();

        this.musicBands = musicBands;
    }
}
