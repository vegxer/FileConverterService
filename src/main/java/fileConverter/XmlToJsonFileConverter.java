package fileConverter;

import fileExtension.FileExtension;
import fileReader.MusicBandsReader;
import fileWriter.MusicGenresWriter;
import listShell.ArrayListShell;
import music.MusicBand;
import music.MusicGenre;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class XmlToJsonFileConverter extends FileConverter
        implements StructureChanger<ArrayList<MusicGenre>, ArrayList<MusicBand>> {
    public XmlToJsonFileConverter(String fileName) throws FileNotFoundException {
        super.setFileName(fileName);
    }

    @Override
    public void convert(String jsonFileName)
            throws IOException, XMLStreamException, XMLParseException {
        if (!FileExtension.getExtension(jsonFileName).equals("json"))
            throw new IllegalArgumentException("Неверное расширение файла " + jsonFileName);

        MusicBandsReader jsonReader = new MusicBandsReader(fileName);
        ArrayList<MusicGenre> musicGenres = changeStructure(jsonReader.readFile());

        MusicGenresWriter jsonWriter = new MusicGenresWriter(jsonFileName);
        jsonWriter.write(musicGenres);
    }

    @Override
    public ArrayList<MusicGenre> changeStructure(ArrayList<MusicBand> musicBands) {
        ArrayListShell<MusicGenre> musicGenres = new ArrayListShell<>();

        for (MusicBand musicBand : musicBands) {
            for (MusicGenre musicGenre : musicBand.getGenres()) {
                if (!musicGenres.contains(x -> x.getName().equals(musicGenre.getName())))
                    musicGenres.add(musicGenre);

                MusicBand band = new MusicBand();
                band.setName(musicBand.getName());
                band.setActivateYear(musicBand.getActivateYear());
                band.setCountry(musicBand.getCountry());
                Objects.requireNonNull(musicGenres.get(x -> x.getName().equals(musicGenre.getName()))).addMusicBand(band);
            }
        }

        return musicGenres;
    }
}
