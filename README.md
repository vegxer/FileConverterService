# Сервис конвертации файлов
Данная программа конвертирует файл, отсортированный по мызыкальным группам в файл, отсортированный по музыкальным жанрам.
## Создание JAR файла при помощи Maven
### Установка Maven
Если у Вас не установлен Maven, то
1) Скачайте архив по ссылке https://maven.apache.org/download.cgi и распакуйте его по пути
C:\Program Files;
2) Добавьте Maven в переменные среды при помощи команды
   ```
   set PATH="C:\Program Files\apache-maven-x.x.x\bin";%PATH%
   ```
   x.x.x - номер версии Maven.

### Создание JAR файла
1) Перейдите в директорию FileConverterService и введите в командную строку
   ```
   mvn package -Dmaven.test.skip 
   ```
   Эта команда сгенерирует jar файл по пути target/FileConverterService-1.0-SNAPSHOT-jar-with-dependencies.jar

## Запуск JAR файла
В командной строке напишите следующее:
```
java -jar target/FileConverterService-1.0-SNAPSHOT-jar-with-dependencies.jar <путь к файлу> <путь к файлу>
```
Пути к файлам являются параметрами запуска программы

Всего необходимо два параметра:
1) Файл, из которого вы хотите ковертировать данные (.xml или .json)
2) Файл, в который вы хотите записать результат конвертации (.json или .xml, соответственно с первым параметром)
