with w_columns as (
    select c.table_schema,
           c.table_name,
           c.column_name,
           case
               when c.data_type='USER-DEFINED' then 'enum('||c.udt_name||')'
               when c.character_maximum_length is null then c.data_type
               else c.data_type || '(' || c.character_maximum_length || ')'
           end as data_type,
           c.column_default,
           case
               when vsc.is_nullable is null then c.is_nullable
               else vsc.is_nullable
           end as is_nullable,
           pg_catalog.col_description(cl.oid, c.ordinal_position) as comment,
           c.ordinal_position
    from information_schema.columns c
    join pg_catalog.pg_class cl on  cl.oid=concat(c.table_schema, '.', c.table_name)::regclass::oid
    left join information_schema.view_column_usage vcu on vcu.table_schema=c.table_schema
                                                      and vcu.view_name=c.table_name
                                                      and vcu.column_name=c.column_name
    left join information_schema.columns vsc on vsc.table_schema=vcu.table_schema
                                            and vsc.table_name=vcu.table_name
                                            and vsc.column_name=vcu.column_name
    where c.table_schema=:schemaName
    order by c.table_schema,
             c.table_name,
             c.ordinal_position
), w_constraints as (
    select distinct -- Duplicates come from joining information_schema.view_column_usage.  Not sure it this is even useful.
           c.table_schema,
           c.table_name,
           c.column_name,
           left(tc.constraint_type,3) as key
    from information_schema.columns c
    join information_schema.constraint_column_usage cu on cu.column_name = c.column_name
    join information_schema.table_constraints tc on tc.table_schema=c.table_schema
                                                and tc.table_name=c.table_name
                                                and cu.constraint_name = tc.constraint_name
                                                and tc.constraint_type in ('UNIQUE', 'PRIMARY KEY')
    where c.table_schema=:schemaName
    order by c.table_schema,
             c.table_name,
             c.column_name
) select --distinct -- Duplicates come from joining information_schema.view_column_usage.  Not sure it this is even useful.
         cd.table_schema,
         cd.table_name,
         cd.column_name,
         cd.data_type,
         cd.column_default,
         cd.is_nullable,
         cd.comment,
         cc.key,
         cd.ordinal_position
from w_columns cd
left join w_constraints cc on cc.table_schema=cd.table_schema
                          and cc.table_name=cd.table_name
                          and cc.column_name=cd.column_name
where cd.table_schema=:schemaName
  and cd.table_name=:tableName
order by cd.table_schema,
         cd.table_name,
         cd.ordinal_position;
