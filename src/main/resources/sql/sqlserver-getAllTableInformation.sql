WITH w_tables AS (
SELECT
    t.TABLE_SCHEMA,
    t.TABLE_NAME,
    t.TABLE_TYPE,
    CASE
        WHEN t.TABLE_TYPE = 'BASE TABLE' THEN 1
        ELSE 0
    END AS updateable,
    ep.value AS table_comment
FROM
    INFORMATION_SCHEMA.TABLES t
LEFT JOIN
    sys.tables st ON st.name = t.TABLE_NAME
LEFT JOIN
    sys.extended_properties ep ON ep.major_id = st.object_id AND ep.minor_id = 0 AND ep.name = 'MS_Description'
WHERE
    t.TABLE_SCHEMA = 'dbo'
--ORDER BY
--    t.TABLE_SCHEMA,
--    t.TABLE_TYPE,
--    t.TABLE_NAME;
)
SELECT
    w.*,
    CASE
        WHEN w.TABLE_TYPE = 'VIEW' AND w.table_comment IS NULL THEN 'view'
        ELSE w.table_comment
    END AS comment
FROM
    w_tables w
--ORDER BY
--    w.TABLE_SCHEMA, w.TABLE_TYPE, w.TABLE_NAME;
