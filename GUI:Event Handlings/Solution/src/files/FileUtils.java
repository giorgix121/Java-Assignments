package files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFile(String fileName) throws IOException {
        // Reads file content into a string
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void saveFile(String fileName, String data) throws IOException {
        // Saves string data into file
        Files.write(Paths.get(fileName), data.getBytes());
    }
}
