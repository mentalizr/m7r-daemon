package org.mentalizr.scheduler.helper;

import de.arthurpicht.utils.io.nio2.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Checksums {

    @SuppressWarnings("unused")
    public static String computeSha256Checksum(Path path) throws NoSuchAlgorithmException, IOException {
        if (!FileUtils.isExistingRegularFile(path))
            throw new IllegalArgumentException("Path reference [" + path.toAbsolutePath() + "] " +
                                               "is no existing regular file.");
        byte[] bytes = Files.readAllBytes(path);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(bytes);
        byte[] hash = digest.digest();
        return toHexString(hash);
    }

    public static String computeSha256Checksum(List<Path> filePaths) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (Path path : filePaths) {
            if (!FileUtils.isExistingRegularFile(path))
                throw new IllegalArgumentException("Path reference [" + path.toAbsolutePath() + "] " +
                                                   "is no existing regular file.");
            byte[] bytes = Files.readAllBytes(path);
            digest.update(bytes);
        }
        byte[] hash = digest.digest();
        return toHexString(hash);
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
