package org.blackdread.sqltojava.service.logic;

import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;
import static org.blackdread.sqltojava.util.NamingConventionUtil.*;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.*;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationGroupImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.service.SqlJdlTypeService;
import org.blackdread.sqltojava.util.JdlUtils;
import org.blackdread.sqltojava.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JdlService {

    private static final Logger log = LoggerFactory.getLogger(JdlService.class);

    private final SqlService sqlService;

    private final SqlJdlTypeService sqlJdlTypeService;

    private final ApplicationProperties properties;

    public JdlService(final SqlService sqlService, final ApplicationProperties properties, final SqlJdlTypeService sqlJdlTypeService) {
        this.sqlService = sqlService;
        this.sqlJdlTypeService = sqlJdlTypeService;
        this.properties = properties;
    }

    public List<JdlEntity> buildEntities() {
        final List<SqlColumn> sqlColumns = sqlService.buildColumns();
        // todo build entities for columns of native enums so we can later export to JDL the native enum and its values
        return SqlUtils
            .groupColumnsByTable(sqlColumns)
            .entrySet()
            .stream()
            .map(this::buildEntity)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted()
            .collect(Collectors.toList());
    }

    public List<JdlRelation> getRelations(List<JdlEntity> entities) {
        return entities.stream().flatMap(e -> e.getRelations().stream()).collect(Collectors.toList());
    }

    public List<JdlRelation> getOneToOneRelations(List<JdlEntity> entities) {
        return entities
            .stream()
            .flatMap(e -> e.getRelations().stream())
            .filter(f -> RelationType.OneToOne.equals(f.getRelationType()))
            .collect(Collectors.toList());
    }

    public List<JdlRelation> getManyToOneRelations(List<JdlEntity> entities) {
        return entities
            .stream()
            .flatMap(e -> e.getRelations().stream())
            .filter(f -> RelationType.ManyToOne.equals(f.getRelationType()))
            .collect(Collectors.toList());
    }

    public JdlRelationGroupImpl getGroupManyToOneRelations(List<JdlEntity> entities) {
        List<JdlRelation> idRelations = entities
            .stream()
            .flatMap(e -> e.getRelations().stream())
            .filter(f -> RelationType.ManyToOne.equals(f.getRelationType()))
            .collect(Collectors.toList());

        return new JdlRelationGroupImpl(RelationType.ManyToOne, idRelations);
    }

    public JdlRelationGroupImpl getGroupOneToOneRelations(List<JdlEntity> entities) {
        List<JdlRelation> idRelations = entities
            .stream()
            .flatMap(e -> e.getRelations().stream())
            .filter(f -> RelationType.OneToOne.equals(f.getRelationType()))
            .collect(Collectors.toList());

        return new JdlRelationGroupImpl(RelationType.OneToOne, idRelations);
    }

    public List<JdlRelation> getManyToManyRelations(List<JdlEntity> entities) {
        return entities
            .stream()
            .flatMap(e -> e.getRelations().stream())
            .filter(e -> RelationType.ManyToMany.equals(e.getRelationType()))
            .collect(Collectors.toList());
    }

    private boolean nonDefaultPrimaryKeyFields(final JdlField f) {
        return !isDefaultPrimaryKey(f);
    }

    private boolean isDefaultPrimaryKey(JdlField f) {
        return f.isPrimaryKey() && f.getType().equals(LONG) && f.getName().equals("id");
    }

    protected Optional<JdlEntity> buildEntity(final Map.Entry<SqlTable, List<SqlColumn>> entry) {
        final List<JdlField> fields = entry
            .getValue()
            .stream()
            .map(this::buildField)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(this::nonDefaultPrimaryKeyFields)
            .collect(Collectors.toList());

        final List<JdlRelation> existingRelations = new ArrayList<>();

        final List<JdlRelation> relations = entry
            .getValue()
            .stream()
            .filter(SqlColumn::isForeignKey)
            .map((SqlColumn column) -> buildRelation(column, sqlService.getTableOfForeignKey(column), existingRelations))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted()
            .collect(Collectors.toList());

        String tableName = entry.getKey().getName();
        String entityName = getEntityNameFormatted(tableName);
        List<String> reserved = properties.getReservedList();

        if (reserved.contains(entityName.toUpperCase())) {
            String msg =
                "Skipping processing table [" +
                entry.getKey().getName() +
                "] because " +
                " the transformed entity name [" +
                entityName +
                "] matches with one of the keywords " +
                reserved;
            log.error(msg);
            return Optional.empty();
        }

        JdlEntity jdlEntity = new JdlEntityImpl(
            entityName,
            properties.getAddTableNameJdl() ? tableName : null,
            fields,
            entry.getKey().getComment().orElse(null),
            sqlService.isEnumTable(entry.getKey().getName()),
            !entry.getKey().isUpdatable(),
            sqlService.isPureManyToManyTable(entry.getKey().getName()),
            relations
        );
        return Optional.of(jdlEntity);
    }

    private String getEntityNameFormatted(final String name) {
        return JdlUtils.getEntityName(name, properties.getDatabaseObjectPrefix());
    }

    /**
     * @param column Column from which to create field
     * @return The field or empty if field is to be ignored
     */
    protected Optional<JdlField> buildField(final SqlColumn column) {
        final String name;
        JdlFieldEnum jdlType;
        final String enumEntityName;
        final String comment;
        final boolean isNativeEnum = column.isNativeEnum();
        String pattern = null;

        if (sqlService.isEnumTable(column.getTable().getName())) return Optional.empty();

        if (column.isForeignKey()) {
            // check if table referenced is an enum, otherwise, skip
            final SqlTable tableOfForeignKey = sqlService.getTableOfForeignKey(column);
            if (!sqlService.isEnumTable(tableOfForeignKey.getName())) {
                log.info("Skipped field of ({}) as ({}) is not an enum table", column, tableOfForeignKey);
                return Optional.empty();
            }
            jdlType = ENUM;
            name = SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(column.getName()));
            enumEntityName = StringUtils.capitalize(SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(tableOfForeignKey.getName())));
            comment =
                column
                    .getComment()
                    .map(comment1 -> tableOfForeignKey.getComment().map(c -> comment1 + ". " + c).orElse(comment1))
                    .orElse(tableOfForeignKey.getComment().orElse(null));
        } else {
            if (isNativeEnum) {
                jdlType = ENUM;
                name = SqlUtils.changeToCamelCase(toTitleCase(column.getName()).replace(" ", ""));
                // todo name of enumEntityName is not great but never mind
                enumEntityName = StringUtils.capitalize(SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(column.getName())));
            } else {
                jdlType = sqlJdlTypeService.sqlTypeToJdlType(column.getType());
                name = SqlUtils.changeToCamelCase(replaceSlavenChars(toTitleCase(column.getName())));
                log.info("column name change sql to jdl format: {}, {}", column.getName(), name);
                enumEntityName = null;
            }
            if (jdlType == UNSUPPORTED) {
                comment = String.join(column.getComment().orElse(""), " ", column.getType());
            } else {
                comment = column.getComment().orElse(null);
            }
            //    comment = (jdlType=UNSUPPORTED) column.getComment().orElse(null);
        }

        final Integer min;
        final Integer max;
        switch (jdlType) {
            // We always define max for string
            case STRING -> {
                min = null;
                max = sqlJdlTypeService.calculateStringMaxLength(column);
            }
            case TIME_AS_TEXT -> {
                pattern = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$";
                jdlType = STRING;
                max = 8;
                min = 8;
            }
            case YEAR_AS_TEXT -> {
                pattern = "^-?(\\d+)$";
                jdlType = STRING;
                max = null;
                min = null;
            }
            case GEOMETRY_AS_TEXT, JSON_AS_TEXT, STRING_UNBOUNDED -> {
                jdlType = STRING;
                max = null;
                min = null;
            }
            default -> {
                min = null;
                max = null;
            }
        }

        // Min and pattern are not set as we cannot guess it (unless we define it in comments -> parse)

        return Optional.of(
            new JdlFieldImpl(
                jdlType,
                name,
                !column.isNullable(),
                comment,
                min,
                max,
                pattern,
                enumEntityName,
                isNativeEnum,
                column.isUnique(),
                column.isPrimaryKey()
            )
        );
    }

    /**
     * @param column            Column from which to create relation, owner side of the relation
     * @param inverseSideTable  The table referenced by the column of the owner side
     * @param existingRelations Cache for checking already registered relations, needed to avoid name duplication
     * @return The relation or empty if relation is to be ignored
     */
    protected Optional<JdlRelation> buildRelation(
        final SqlColumn column,
        final SqlTable inverseSideTable,
        final List<JdlRelation> existingRelations
    ) {
        if (!column.isForeignKey()) throw new IllegalArgumentException("Cannot create a relation from a non foreign key");

        final SqlTable ownerSideTable = column.getTable();
        final String tableName = ownerSideTable.getName();
        final String columnName = column.getName();

        final boolean isNullable = column.isNullable();

        final boolean isUnique = column.isUnique();

        if (sqlService.isEnumTable(inverseSideTable.getName())) {
            log.info("Skipped relation of ({}) as ({}) is an enum table", column, inverseSideTable);
            return Optional.empty();
        }

        final boolean isPureManyToManyTable = sqlService.isPureManyToManyTable(tableName);

        // it allows to have a clearer idea when reading the generated file
        final String extraRelationComment = column.getTable().getComment().orElse(null);

        final RelationType relationType;
        if (isPureManyToManyTable) {
            relationType = RelationType.ManyToMany;
        } else {
            if (isUnique) {
                relationType = RelationType.OneToOne;
            } else {
                relationType = RelationType.ManyToOne;
            }
        }

        // TeamMember{user(login) required} to User
        final String inverseSideEntityName = getEntityNameFormatted(inverseSideTable.getName());

        // We put always bidirectional but we have no way to generate good inverse name so we put the owner side name
        final String ownerEntityName = getEntityNameFormatted(tableName);
        final String inverseSideRelationName = buildInverseSideRelationName(
            inverseSideEntityName,
            ownerEntityName,
            columnName,
            existingRelations
        );
        boolean required = !isNullable;
        if (required) {
            if (ownerEntityName.equals(inverseSideEntityName)) {
                String msg =
                    "Detected a Self Reference in the table " +
                    tableName +
                    ". JHipster JDL currently does not support Reflexive relationships. " +
                    "Set [nullable] as [true] for column [" +
                    columnName +
                    "] to fix errors when using the JDL with JHipster";
                log.warn(msg);
                required = false;
            }
        }

        final JdlRelationImpl relation = new JdlRelationImpl(
            relationType,
            properties.isAssumeBidirectional(),
            required,
            false,
            ownerEntityName,
            inverseSideEntityName,
            SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(columnName)),
            sqlService.getDisplayFieldOfTable(inverseSideTable),
            column.getComment().orElse(null),
            null,
            inverseSideRelationName,
            sqlService.getDisplayFieldOfTable(ownerSideTable),
            extraRelationComment
        );

        existingRelations.add(relation);

        return Optional.of(relation);
    }

    /**
     * @param inverseSideEntityName The table name referenced by the column of the owner side
     * @param ownerEntityName       Table name of column from which to create relation, owner side of the relation
     * @param columnName            Column name from which to create relation, owner side of the relation
     * @param existingRelations     Cache for checking already registered relations, needed to avoid name duplication
     * @return Modified name of ownerEntity or full name consisting of ownerEntity and columnName
     */
    private String buildInverseSideRelationName(
        String inverseSideEntityName,
        String ownerEntityName,
        String columnName,
        List<JdlRelation> existingRelations
    ) {
        String possibleRelationName = JdlUtils.decapitalize(ownerEntityName);
        // Looking for a full match of names within 1 entity
        final boolean relationAlreadyExist = existingRelations
            .stream()
            .anyMatch(jdlRelation ->
                ownerEntityName.equals(jdlRelation.getOwnerEntityName()) &&
                possibleRelationName.equals(jdlRelation.getInverseSideRelationName().orElse(null)) &&
                inverseSideEntityName.equals(jdlRelation.getInverseSideEntityName())
            );

        if (relationAlreadyExist) {
            //As stated earlier, we have no way to generate a good reverse name, so we put the owner's side name.
            //So we simply combine the relationship name and the column name
            return possibleRelationName + "Of" + StringUtils.capitalize(SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(columnName)));
        } else {
            return possibleRelationName;
        }
    }
}
