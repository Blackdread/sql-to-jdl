package org.blackdread.sqltojava.view;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.stream.Collectors;
import org.blackdread.sqltojava.config.UndefinedJdlTypeHandlingEnum;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.slf4j.Logger;

/**
 * Methods for JDL entities in the view
 */
public interface JdlEntityView extends JdlEntity, JdlCommentView {
    UndefinedJdlTypeHandlingEnum getUndefinedJdlTypeHandling();

    default String getType() {
        return (isEnumEntity()) ? "enum" : "entity";
    }

    default String tableName() {
        return (getTableName() != null) ? String.format("(%s)", getTableName()) : null;
    }

    default List<JdlField> filteredFields() {
        return getFields().stream().filter(f -> filterUnsupported(getUndefinedJdlTypeHandling(), f)).collect(Collectors.toList());
    }

    /**
     * Filters fields and throws error based on UndefinedJdlTypeHandling
     * @return
     */
    default boolean filterUnsupported(UndefinedJdlTypeHandlingEnum undefinedJdlTypeHandling, JdlField field) {
        switch (field.getType()) {
            case UNSUPPORTED -> {
                switch (undefinedJdlTypeHandling) {
                    case UNSUPPORTED -> {
                        return true;
                    }
                    case SKIP -> {
                        log().warn("Skipping unsupportd field {}", field);
                        return false;
                    }
                    //                    case ERROR -> throw new RuntimeException(String.format("Unsupported jdl type %s", field));
                    case ERROR -> {
                        log().error("Unsupported jdl type {}", field);
                        return false;
                    }
                    default -> throw new RuntimeException(
                        String.format("Unhandled UndefinedJdlTypeHandlingEnum.{}", undefinedJdlTypeHandling)
                    );
                }
            }
            default -> {
                return true;
            }
        }
    }

    private static Logger log() {
        final class LogHolder {

            private static final Logger LOGGER = getLogger(JdlEntityView.class);
        }
        return LogHolder.LOGGER;
    }
}
