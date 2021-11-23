package converterService;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class FileExtension {
    private FileExtension() {}

    public static String getExtension(String filePath) throws IOException
    {
        if (filePath.indexOf('.') < 0 || filePath.indexOf('.') == filePath.length() - 1)
            throw new FileNotFoundException("Incorrect file name input");

        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
}
