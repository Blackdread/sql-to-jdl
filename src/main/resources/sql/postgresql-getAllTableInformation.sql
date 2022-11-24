with w_tables as (
  select t.table_schema,
         t.table_name,
         t.is_insertable_into as is_updatable,
         obj_description(c.oid, 'pg_class') as table_comment,
         t.table_type
  from information_schema.tables t
  join pg_catalog.pg_class c on c.oid = cast(concat(t.table_schema, '.', t.table_name) as regclass)
  where t.table_schema=:schemaName
--  and t.table_type='BASE TABLE'
  order by t.table_schema,
           t.table_type,
           t.table_name
 ) select w.*,
          case
              when w.table_type='VIEW' and w.table_comment is null then 'view'
              else w.table_comment
          end as comment
   from w_tables w;
