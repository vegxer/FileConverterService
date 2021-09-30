import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название xml файла: ");
        String xmlFile = scanner.nextLine();
        System.out.print("Введите название json файла: ");
        String jsonFile = scanner.nextLine();

        System.out.print("1 - Конвертировать xml в json\n2 - Конвертировать json в xml\nВаш выбор: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            FileConverter converter;
            switch (choice) {
                case 1:
                    converter = FileConverter.create(xmlFile);
                    converter.convert(jsonFile);
                    break;
                case 2:
                    converter = FileConverter.create(jsonFile);
                    converter.convert(xmlFile);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception exc) {
            System.out.println("Ошибка: " + exc.getMessage());
        }
    }
}
