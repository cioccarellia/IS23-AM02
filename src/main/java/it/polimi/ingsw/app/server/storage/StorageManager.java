package it.polimi.ingsw.app.server.storage;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.utils.json.Parsers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class used to manage stored configurations
 * */
public class StorageManager {

    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);

    public static final String RECORDS_DIRECTORY = "records";
    public static final String FULL_SAVE_PATH_PREFIX = RECORDS_DIRECTORY + "/" + "msf_saves_";

    public synchronized @Nullable GameModel load(@NotNull List<String> playerList) {
        assert !playerList.isEmpty() && playerList.size() <= 4;

        String orderedPlayers = playerList.stream().sorted().collect(Collectors.joining());
        String HASHED_KEY = hash(orderedPlayers);

        String PATH = FULL_SAVE_PATH_PREFIX + HASHED_KEY;
        File file = new File(PATH);

        if (file.exists() && file.canRead()) {
            String content = readFileContents(file);
            logger.info("Deserialized file=" + file.getAbsolutePath() + " into content=" + content);

            return Parsers.defaultGson().fromJson(content, GameModel.class);
        } else {
            logger.info("Non-existent file=" + file.getAbsolutePath());
            return null;
        }
    }

    public synchronized void save(@NotNull List<String> playerList, GameModel model) {
        assert !playerList.isEmpty() && playerList.size() <= 4;
        assert model != null;

        String orderedPlayers = playerList.stream().sorted().collect(Collectors.joining());
        String HASHED_KEY = hash(orderedPlayers);

        String PATH = FULL_SAVE_PATH_PREFIX + HASHED_KEY;
        File file = new File(PATH);

        // if file is already stored, we need to remove it
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Can not delete already present file " + file.getAbsolutePath());
        }

        File recordsDirectory = new File(RECORDS_DIRECTORY);

        try {
            if (recordsDirectory.mkdirs()) {
                logger.warn("Created directories for storing records");
            } else {
                logger.info("Directory for records already exist");
            }

            if (file.createNewFile()) {
                String serializedModel = Parsers.defaultGson().toJson(model);
                writeFileContents(file, serializedModel);
            } else {
                throw new RuntimeException("Can not create file for storing data " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void delete(@NotNull List<String> playerList) {
        assert !playerList.isEmpty() && playerList.size() <= 4;

        String orderedPlayers = playerList.stream().sorted().collect(Collectors.joining());
        String HASHED_KEY = hash(orderedPlayers);

        String PATH = FULL_SAVE_PATH_PREFIX + HASHED_KEY;
        File file = new File(PATH);
        String path = file.getAbsolutePath();

        if (file.exists() ) {
            if (file.delete()) {
                logger.info("Successfully deleted file=" + path);
            } else {
                logger.error("Could not delete file=" + path);
            }
        } else {
            logger.warn("Could not find file=" + path);
        }
    }



    private @NotNull String readFileContents(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            final int BUFFER_SIZE = 1024;
            char[] buffer = new char[BUFFER_SIZE];

            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, charsRead));
                buffer = new char[BUFFER_SIZE];
            }

            reader.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileContents(File file, String str) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            writer.write(str);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest(input);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String hash(String input) {
        return bytesToHex(digest(input.getBytes()));
    }
}
