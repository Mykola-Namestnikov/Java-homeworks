package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SqlFileReader {
    public static String readSqlFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося прочитати файл: " + filePath, e);
        }
    }
}
