package org.blackdread.sqltojava.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public final class ResourceUtil {
    private static final Logger log = LoggerFactory.getLogger(ResourceUtil.class);

    public static String readString(Resource resource) {
        String s;
        try {
            File file = resource.getFile();
            s = new String(Files.readAllBytes(file.toPath()));
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String path) {
        try {
            InputStream is = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
            String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFirstExistingResourcePath(String fileName, String... paths) {
        for (String path : paths) {
            if (ResourceUtil.class.getResource(path) != null) {
                log.info(String.format("%s found at %s", fileName, path));
                return path;
            } else {
                log.info(String.format("%s not found at %s", fileName, path));
            }
        }
        throw new RuntimeException(String.format("Could not find %s", fileName));
    }
}
