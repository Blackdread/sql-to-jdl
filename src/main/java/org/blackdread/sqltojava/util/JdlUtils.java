package org.blackdread.sqltojava.util;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JdlUtils {

    private static final Logger log = LoggerFactory.getLogger(JdlUtils.class);

    private JdlUtils() {}

    @NotNull
    public static String paginationAll() {
        return "paginate * with pagination";
    }

    @NotNull
    public static String serviceClassAll() {
        return "service * with serviceClass";
    }

    @NotNull
    public static String mapStructAll() {
        return "dto * with mapstruct";
    }

    @NotNull
    public static String angularSuffixAll(final String suffix) {
        return "angularSuffix * with " + suffix;
    }

    @NotNull
    public static String filterAll() {
        return "filter *";
    }

    public static String getEntityName(String name, List<String> prefixes) {
        String entityName = prefixes
            .stream()
            .filter(prefix -> name.startsWith(prefix))
            .findFirst()
            .map(s -> name.substring(s.length()))
            .orElse(name);
        return StringUtils.capitalize(SqlUtils.changeToCamelCase(entityName));
    }

    public static String decapitalize(String string) {
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static List<String> getOptions() {
        List<String> options = new ArrayList<>();
        options.add(JdlUtils.serviceClassAll());
        options.add(JdlUtils.paginationAll());
        options.add(JdlUtils.mapStructAll());
        options.add(JdlUtils.filterAll());
        return options;
    }
}
