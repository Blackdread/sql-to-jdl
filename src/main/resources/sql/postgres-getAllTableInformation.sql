select s.schema_name,
       t.table_name,
       obj_description(c.oid, 'pg_class') as comment
from information_schema.schemata s
         join information_schema.tables t on t.table_schema=s.schema_name
         join pg_catalog.pg_class c on c.relname=t.table_name
         join pg_catalog.pg_namespace ns on ns.nspname=s.schema_name
where s.schema_name not in ('pg_catalog', 'information_schema')
order by s.schema_name,
         t.table_name
