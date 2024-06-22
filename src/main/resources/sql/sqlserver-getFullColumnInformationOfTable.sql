WITH w_columns AS (
    SELECT
        c.TABLE_SCHEMA,
        c.TABLE_NAME,
        c.COLUMN_NAME,
        CASE
            WHEN c.DATA_TYPE = 'USER-DEFINED' THEN 'enum(' + c.COLUMN_NAME + ')'
            WHEN c.CHARACTER_MAXIMUM_LENGTH IS NULL THEN c.DATA_TYPE
            ELSE c.DATA_TYPE + '(' + CAST(c.CHARACTER_MAXIMUM_LENGTH AS VARCHAR) + ')'
        END AS data_type,
        c.COLUMN_DEFAULT,
        c.IS_NULLABLE,
        c.ORDINAL_POSITION
    FROM
        INFORMATION_SCHEMA.COLUMNS c
    WHERE
        c.TABLE_SCHEMA = :schemaName
), w_constraints AS (
    SELECT DISTINCT
        c.TABLE_SCHEMA,
        c.TABLE_NAME,
        c.COLUMN_NAME,
        LEFT(tc.CONSTRAINT_TYPE, 3) AS keye
    FROM
        INFORMATION_SCHEMA.COLUMNS c
    JOIN
        INFORMATION_SCHEMA.KEY_COLUMN_USAGE cu ON cu.COLUMN_NAME = c.COLUMN_NAME
    JOIN
        INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc ON tc.TABLE_SCHEMA = c.TABLE_SCHEMA
                                                AND tc.TABLE_NAME = c.TABLE_NAME
                                                AND cu.CONSTRAINT_NAME = tc.CONSTRAINT_NAME
                                                AND tc.CONSTRAINT_TYPE IN ('UNIQUE', 'PRIMARY KEY')
    WHERE
        c.TABLE_SCHEMA = :schemaName

)
SELECT * FROM w_columns cd
left join w_constraints cc on cc.table_schema=cd.table_schema
                          and cc.table_name=cd.table_name
                          and cc.column_name=cd.column_name
where cd.table_schema=:schemaName
  and cd.table_name=:tableName


