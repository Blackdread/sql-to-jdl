package org.blackdread.sqltojava.view.mapper;

import java.util.Optional;

/**
 * Used to handle Optional with Mapstruct
 */
public class OptionalUtils {

    private OptionalUtils() {}

    public static <T> T fromOptional(Optional<T> optional) {
        return optional.orElse(null);
    }
}
