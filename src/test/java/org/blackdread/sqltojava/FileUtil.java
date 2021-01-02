package org.blackdread.sqltojava;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>Created on 2020/12/29.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class FileUtil {

    public static List<String> readAllLinesClasspath(final String name) throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(FileUtil.class.getResource(name).toURI()), StandardCharsets.UTF_8);
    }

    public static List<String> readAllLines(final String name) throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(name), StandardCharsets.UTF_8);
    }

    /*
        try (InputStream in = this.getClass().getResourceAsStream("/result/mysql57-expected.jdl"); InputStreamReader fin = new InputStreamReader(in, StandardCharsets.UTF_8); BufferedReader bufIn = new BufferedReader(fin)) {
          } catch (Exception e) {
            throw new RuntimeException(e);
        }
     */
}
