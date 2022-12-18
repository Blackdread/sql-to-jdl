package org.blackdread.sqltojava.view;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;

/**
 * Methods for JDL fields in the view
 */
public interface JdlFieldView extends JdlField, JdlCommentView {
    //TODO This validation should be handled in JdlService
    default String getTypeName() {
        JdlFieldEnum type = getType();
        return switch (type) {
            case ENUM -> getEnumEntityName()
                .orElseThrow(() -> new IllegalStateException("An enum field must have its enum entity name set"));
            default -> (("UUID".equals(type.name())) ? type.name() : type.toCamelUpper());
        };
    }

    default String constraints() {
        List<String> constraints = new ArrayList<>();
        if (renderRequired()) constraints.add(requiredConstraint());
        if (isUnique()) constraints.add(uniqueConstraint());
        getMin().ifPresent(min -> constraints.add(minConstraint(min)));
        getMax().ifPresent(max -> constraints.add(maxConstraint(max)));
        getPattern().ifPresent(pattern -> constraints.add(patternConstraint(pattern)));
        return (!constraints.isEmpty()) ? " ".concat(Joiner.on(" ").join(constraints)) : null;
    }

    // TODO do not write required when field is primary key
    default boolean renderRequired() {
        // if (!isPrimaryKey() && isRequired() && ) constraints.add(JdlUtils.validationRequired());
        return isRequired() && !(getName().equals("id") && getType().equals(JdlFieldEnum.UUID));
    }

    @NotNull
    default String requiredConstraint() {
        return "required";
    }

    @NotNull
    default String uniqueConstraint() {
        return "unique";
    }

    default String minConstraint(int min) {
        return switch (getType()) {
            case STRING -> validationMinLength(min);
            default -> validationMin(min);
        };
    }

    default String maxConstraint(int max) {
        return switch (getType()) {
            case STRING -> validationMaxLength(max);
            default -> validationMax(max);
        };
    }

    //TODO This validation should be handled in JdlService
    default String patternConstraint(String pattern) {
        return switch (getType()) {
            case STRING -> validationPattern(pattern);
            default -> throw new RuntimeException("Only String can have a pattern");
        };
    }

    @NotNull
    default String validationMax(final int max) {
        return "max(" + max + ")";
    }

    @NotNull
    default String validationMin(final int min) {
        return "min(" + min + ")";
    }

    default String validationMaxLength(final int max) {
        return "maxlength(" + max + ")";
    }

    @NotNull
    default String validationMinLength(final int min) {
        return "minlength(" + min + ")";
    }

    @NotNull
    default String validationPattern(final String pattern) {
        return "pattern(/" + pattern + "/)";
    }
}
