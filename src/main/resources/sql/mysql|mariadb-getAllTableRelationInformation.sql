select kku.constraint_name,
       kku.table_schema,
       kku.table_name,
       kku.column_name,
       kku.referenced_table_schema as foreign_table_schema,
       kku.referenced_table_name   as foreign_table_name,
       kku.referenced_column_name  as foreign_column_name
from information_schema.key_column_usage kku
where kku.referenced_table_schema=:schemaName and kku.referenced_table_name is not null
order by kku.table_name,
         kku.column_name;
