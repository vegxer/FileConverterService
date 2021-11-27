package ru.itdt.converterService;

import org.json.simple.parser.ParseException;
import ru.itdt.converterService.fileConverter.FileConverterFactory;
import ru.itdt.converterService.music.MusicBand;
import ru.itdt.converterService.music.MusicGenre;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, ParseException {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
                    Некорректное количество аргументов. Необходимо 2 аргумента:
                    1) Файл, из которого вы хотите ковертировать данные (.xml или .json)
                    2) Файл, в который вы хотите записать результат конвертации (.json или .xml, соответственно с первым параметром)""");
        }
        System.out.println("Все возникающие ошибки при чтении и записи в файл будут записываться в логи в текущей папке");


        MusicGenre genre = new MusicGenre();
        genre.setGenreName("rock");
        MusicBand band1 = new MusicBand();
        band1.setActivateYear(10);
        band1.setBandName("yeah");
        band1.setCountry("UK");
        MusicBand band2 = new MusicBand();
        band2.setCountry("sdfd");
        band2.setBandName("sdhtt");
        band2.setActivateYear(3);
        band1.getMusicGenres().add(genre);
        band2.getMusicGenres().add(genre);
        Set<MusicBand> set1 = new HashSet<>();
        set1.add(band1);
        set1.add(band2);
        Set<MusicBand> set2 = new HashSet<>();
        set2.add(band2);
        set2.add(band1);
        System.out.println(set1.equals(set2));

        FileConverterFactory.create(args[0])
                .convert(args[1]);
        System.out.println(String.format("Преобразование прошло успешно, файл %s создан", args[1]));
    }
}
