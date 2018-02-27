package org.blackdread.sqltojava.util;

import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.JdlRelation;

import javax.validation.constraints.NotNull;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class JdlUtils {

    private JdlUtils() {
    }

    @NotNull
    public static String validationMax(final int max) {
        return "max(" + max + ")";
    }

    @NotNull
    public static String validationMin(final int min) {
        return "min(" + min + ")";
    }

    public static String validationMaxLength(final int max) {
        return "maxlength(" + max + ")";
    }

    @NotNull
    public static String validationMinLength(final int min) {
        return "minlength(" + min + ")";
    }

    @NotNull
    public static String validationRequired() {
        return "required";
    }

    @NotNull
    public static String validationPattern(final String pattern) {
        return "pattern(/" + pattern + "/)";
    }

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

    @NotNull
    public static String writeEntity(final JdlEntity entity) {
        final StringBuilder builder = new StringBuilder(500);

        if (entity.getComment().isPresent()) {
            builder.append("/** ");
            builder.append(entity.getComment().get());
            builder.append(" */\n");
        }
        if (entity.isEnum())
            builder.append("enum ");
        else
            builder.append("entity ");
        builder.append(entity.getName());
        builder.append(" {\n");
        for (final JdlField field : entity.getFields()) {
            builder.append(writeField(field));
            builder.append(",\n");
        }
        if (!entity.isEnum()) {
            // remove the last ','
            builder.deleteCharAt(builder.length() - 2);
        }
        builder.append("}");
        return builder.toString();
    }

    @NotNull
    public static String writeField(final JdlField field) {
        final StringBuilder builder = new StringBuilder(200);
        if (field.getComment().isPresent()) {
            builder.append("\t");
            builder.append("/** ");
            builder.append(field.getComment().get());
            builder.append(" */\n");
        }
        builder.append("\t");
        builder.append(field.getName());
        builder.append(" ");
        switch (field.getType()) {
            case ENUM:
                builder.append(field.getEnumEntityName().orElseThrow(() -> new IllegalStateException("An enum field must have its enum entity name set")));
                break;
            default:
                builder.append(field.getType().toCamelUpper());
        }

        if (field.isRequired()) {
            builder.append(" ");
            builder.append(validationRequired());
        }
        if (field.getMin().isPresent()) {
            builder.append(" ");
            if (field.getType().equals(JdlFieldEnum.STRING))
                builder.append(validationMinLength(field.getMin().get()));
            else
                builder.append(validationMin(field.getMin().get()));
        }
        if (field.getMax().isPresent()) {
            builder.append(" ");

            if (field.getType().equals(JdlFieldEnum.STRING))
                builder.append(validationMaxLength(field.getMax().get()));
            else
                builder.append(validationMax(field.getMax().get()));
        }
        if (field.getPattern().isPresent()) {
            builder.append(" ");
            builder.append(validationPattern(field.getPattern().get()));
        }
        return builder.toString();
    }


    /**
     * Specific for pure ManyToMany relation table
     *
     * @param relation Relation MtM
     * @return Jdl representation of the relation
     */
    @NotNull
    public static String writeRelationPureManyToMany(final JdlRelation relation) {
        return "// TODO This is a pure ManyToMany relation (delete me and decide owner side)\n" + writeRelation(relation);
    }

    @NotNull
    public static String writeRelation(final JdlRelation relation) {
//         relationship ManyToOne {
//              /** comment */
//              TeamMember{user(login) required} to
//              /** comment */
//              User{teamMember(field)}
//         }
        final StringBuilder builder = new StringBuilder(200);
        relation.getExtraRelationComment().ifPresent(s -> builder.append("// ").append(s).append("\n"));
        builder.append("relationship ");
        builder.append(relation.getRelationType().toJdl());
        builder.append(" {\n");
        relation.getOwnerComment().ifPresent(ownerComment -> {
            builder.append("\t");
            builder.append("/** ");
            builder.append(relation.getOwnerComment().get());
            builder.append(" */\n");
        });
        builder.append("\t");
        builder.append(relation.getOwnerEntityName());
        builder.append("{");
        builder.append(relation.getOwnerRelationName());
        relation.getOwnerDisplayField().ifPresent(ownerDisplayField -> {
            builder.append("(");
            builder.append(ownerDisplayField);
            builder.append(")");
        });
        if (relation.isOwnerRequired()) {
            builder.append(" required");
        }
        builder.append("} to");

        if (relation.getInverseSideComment().isPresent()) {
            builder.append("\n\t/** ");
            builder.append(relation.getInverseSideComment().get());
            builder.append(" */\n");
            builder.append("\t");
        } else
            builder.append(" ");

        builder.append(relation.getInverseSideEntityName());
        if (relation.isBidirectional()) {
            builder.append("{");
            builder.append(relation.getInverseSideRelationName().orElseThrow(() -> new IllegalStateException("A bidirectional relation has to have a inverse side relation name")));
            relation.getInverseSideDisplayField().ifPresent(inverseSideDisplayField -> {
                builder.append("(");
                builder.append(inverseSideDisplayField);
                builder.append(")");
            });
            if (relation.isInverseSideRequired()) {
                builder.append(" required");
            }
            builder.append("}");
        }
        builder.append("\n");
        builder.append("}");

        return builder.toString();
    }

}
