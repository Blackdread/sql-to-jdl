package org.blackdread.sqltojava.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.springframework.core.io.Resource;

public final class ResourceUtil {
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
}
