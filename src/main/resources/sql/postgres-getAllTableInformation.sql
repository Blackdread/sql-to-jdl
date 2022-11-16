select t.table_schema,
       t.table_name,
       obj_description(c.oid, 'pg_class') as comment
from information_schema.tables t
join pg_catalog.pg_class c on  c.oid=cast(concat(t.table_schema, '.', t.table_name) as regclass)
where t.table_schema=:schemaName
--  and t.table_type='BASE TABLE'
order by t.table_schema,
         t.table_name;
