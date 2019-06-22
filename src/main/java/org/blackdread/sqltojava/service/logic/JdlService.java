package org.blackdread.sqltojava.service.logic;

import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.service.SqlJdlTypeService;
import org.blackdread.sqltojava.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/2/9.</p>
 *
 * @author Yoann CAPLAIN
 */
@Service
public class JdlService {

    private static final Logger log = LoggerFactory.getLogger(JdlService.class);

    private final SqlService sqlService;

    private final SqlJdlTypeService sqlJdlTypeService;

    public JdlService(final SqlService sqlService, final SqlJdlTypeService sqlJdlTypeService) {
        this.sqlService = sqlService;
        this.sqlJdlTypeService = sqlJdlTypeService;
    }

    public List<JdlEntity> buildEntities() {
        final List<SqlColumn> sqlColumns = sqlService.buildColumns();
        return SqlUtils.groupColumnsByTable(sqlColumns).entrySet().stream()
            .map(this::buildEntity)
            .sorted()
            .collect(Collectors.toList());
    }

    protected JdlEntity buildEntity(final Map.Entry<SqlTable, List<SqlColumn>> entry) {

        final List<JdlField> fields = entry.getValue().stream()
            .map(this::buildField)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        final List<JdlRelation> relations = entry.getValue().stream()
            .filter(SqlColumn::isForeignKey)
            .map((SqlColumn column) -> buildRelation(column, sqlService.getTableOfForeignKey(column)))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted()
            .collect(Collectors.toList());

        return new JdlEntityImpl(
            getEntityNameFormatted(entry.getKey().getName()),
            fields,
            entry.getKey().getComment().orElse(null),
            sqlService.isEnumTable(entry.getKey().getName()),
            sqlService.isPureManyToManyTable(entry.getKey().getName()),
            relations);
    }

    private static String getEntityNameFormatted(final String name) {
        return StringUtils.capitalize(SqlUtils.changeToCamelCase(name));
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
        String pattern = null;

        if (sqlService.isEnumTable(column.getTable().getName()))
            return Optional.empty();


        if (column.isForeignKey()) {
            // check if table referenced is an enum, otherwise, skip
            final SqlTable tableOfForeignKey = sqlService.getTableOfForeignKey(column);
            if (!sqlService.isEnumTable(tableOfForeignKey.getName())) {
                log.info("Skipped field of ({}) as ({}) is not an enum table", column, tableOfForeignKey);
                return Optional.empty();
            }
            jdlType = JdlFieldEnum.ENUM;
            name = SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(column.getName()));
            enumEntityName = StringUtils.capitalize(SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(tableOfForeignKey.getName())));
            comment = column.getComment()
                .map(comment1 -> tableOfForeignKey.getComment().map(c -> comment1 + ". " + c).orElse(comment1))
                .orElse(tableOfForeignKey.getComment().orElse(null));
        } else {
            jdlType = sqlJdlTypeService.sqlTypeToJdlType(column.getType());
            name = SqlUtils.changeToCamelCase(column.getName());
            enumEntityName = null;
            comment = column.getComment().orElse(null);
        }


        final Integer min;
        final Integer max;
        switch (jdlType) {
            // We always define max for string
            case STRING:
                min = null;
                max = SqlUtils.parseSqlSize(column.getType()).orElse(255);
                break;
            case TIME_AS_TEXT:
                pattern = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$";
                jdlType = JdlFieldEnum.STRING;
                max = 8;
                min = 8;
                break;
            case GEOMETRY_AS_TEXT:
                jdlType = JdlFieldEnum.STRING;
                max = null;
                min = null;
                break;
            default:
                min = null;
                max = null;
        }

        // Min and pattern are not set as we cannot guess it (unless we define it in comments -> parse)

        return Optional.of(new JdlFieldImpl(
            jdlType,
            name,
            !column.isNullable(),
            comment,
            min,
            max,
            pattern,
            enumEntityName
        ));
    }

    /**
     * @param column           Column from which to create relation, owner side of the relation
     * @param inverseSideTable The table referenced by the column of the owner side
     * @return The relation or empty if relation is to be ignored
     */
    protected Optional<JdlRelation> buildRelation(final SqlColumn column, final SqlTable inverseSideTable) {
        if (!column.isForeignKey())
            throw new IllegalArgumentException("Cannot create a relation from a non foreign key");

        final String tableName = column.getTable().getName();
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
        final String inverseSideRelationName = SqlUtils.changeToCamelCase(tableName);

        return Optional.of(new JdlRelationImpl(
            relationType,
            // We always make it bidirectional for auto-generated jdl (manually edit result after)
            true,
            !isNullable,
            false,
            getEntityNameFormatted(tableName),
            inverseSideEntityName,
            SqlUtils.changeToCamelCase(SqlUtils.removeIdFromEnd(columnName)),
            // Cannot know so has to be set manually after generation, default is ID by jHipster
            null,
            column.getComment().orElse(null),
            null,
            inverseSideRelationName,
            null,
            extraRelationComment
        ));
    }

}

