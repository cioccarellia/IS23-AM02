package it.polimi.ingsw.utils.resources;

import it.polimi.ingsw.utils.json.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to import and stream resource files
 * */
public class ResourceReader {

    public static InputStream readAsResource(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(filename);
    }

    public static @NotNull String readAndMapToString(String filename) {
        try (InputStream file = readAsResource(filename)) {
            String content = new String(file.readAllBytes(), StandardCharsets.UTF_8);

            file.close();
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull <T> T readAndDeserialize(String filename, Class<T> kclass) {
        String content = readAndMapToString(filename);
        return JsonParser.from(content, kclass);
    }
}
