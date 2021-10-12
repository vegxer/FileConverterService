import FileConverter.FileConverter;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 2)
                throw new IllegalArgumentException("Некорректное количество аргументов");

            FileConverter converter = FileConverter.create(args[0]);
            converter.convert(args[1]);
            System.out.println("Преобразование прошло успешно, файл " + args[1] + " создан");
        } catch (Exception exc) {
            System.out.println("Ошибка: " + exc.getMessage());
        }
    }
}
