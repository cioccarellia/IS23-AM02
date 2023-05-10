package it.polimi.ingsw.utils.resources;

import it.polimi.ingsw.utils.json.Parsers;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Utility class to import and stream resource files
 */
public class ResourceReader {

    public static InputStream readAsResource(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(filename);
    }

    public static @NotNull String readAndMapToString(String filename) {
        try (InputStream file = readAsResource(filename)) {
            return new String(file.readAllBytes(), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull <T> T readAndDeserialize(String filename, Class<T> kclass) {
        String content = readAndMapToString(filename);
        return Parsers.defaultJson().fromJson(content, kclass);
    }
}
