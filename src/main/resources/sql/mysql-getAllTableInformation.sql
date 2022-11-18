select t.table_schema,
       t.table_name,
       t.table_comment as comment
from information_schema.tables t
where t.table_schema=:schemaName;
