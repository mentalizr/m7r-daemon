package org.mentalizr.scheduler.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;
import static de.arthurpicht.utils.io.assertions.PathAssertions.assertIsExistingDirectory;

public class FileHelper {

    public static List<Path> getRegularFilesNotEndingWithTildeInDirectory(Path dir) throws IOException {
        assertArgumentNotNull("dir", dir);
        assertIsExistingDirectory(dir);
        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.getFileName().toString().endsWith("~"))
                    .collect(Collectors.toList());
        }
    }

}
