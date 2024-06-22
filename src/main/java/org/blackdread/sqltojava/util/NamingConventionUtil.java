package org.blackdread.sqltojava.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class NamingConventionUtil {

    public static String replaceSlavenChars(String columnName) {
        return columnName
            .replace(" ", "")
            .replace("š", "s")
            .replace("č", "c")
            .replace("ž", "z")
            .replace("ć", "c")
            .replace("đ", "d")
            .replace(" ", "")
            .replace("Š", "S")
            .replace("Č", "C")
            .replace("Ž", "Z")
            .replace("Ć", "C")
            .replace("Đ", "D");
    }

    public static String toTitleCase(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return sentence;
        }
        if (!sentence.contains(" ")) {
            return sentence;
        }
        return Arrays
            .stream(sentence.split(" "))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
