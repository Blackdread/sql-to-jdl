select tc.constraint_name,
       tc.table_schema,
       tc.table_name,
       kcu.column_name,
       ccu.table_schema as foreign_table_schema,
       ccu.table_name   as foreign_table_name,
       ccu.column_name  as foreign_column_name
from information_schema.table_constraints as tc
join information_schema.key_column_usage as kcu on tc.constraint_name = kcu.constraint_name
                                        and tc.table_schema = kcu.table_schema
                                        and tc.table_name=kcu.table_name
join information_schema.constraint_column_usage as ccu on ccu.constraint_name = tc.constraint_name
                                                      and ccu.table_schema = tc.table_schema
where tc.constraint_type = 'FOREIGN KEY'
  and tc.table_schema=:schemaName
order by tc.table_schema,
         tc.table_name,
         kcu.column_name;
