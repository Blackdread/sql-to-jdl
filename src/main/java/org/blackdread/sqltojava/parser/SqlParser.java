package org.blackdread.sqltojava.parser;

import com.google.common.io.Files;

import java.io.File;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public class SqlParser {

    /**
     * @param fileToParse Sql file
     * @param saveInto    Jdl file to save into
     */
    public static void parse(final File fileToParse, final File saveInto) {
        checkIsSql(fileToParse);


    }

    private static void checkIsSql(final File file) {
        final String fileExtension = Files.getFileExtension(file.getName());
        if (!fileExtension.equalsIgnoreCase("sql")) {
            throw new IllegalArgumentException("Not an sql file");
        }
    }
}
