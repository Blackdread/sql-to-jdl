select vsc.is_nullable as view_is_nullable,
       s.schema_name,
       t.table_name,
       c.column_name,
       case
           when c.data_type='USER-DEFINED' then 'enum('||c.udt_name||')'
           when c.character_maximum_length is null then c.data_type
           else c.data_type || '(' || c.character_maximum_length || ')'
           end as data_type,
       case
           when vsc.is_nullable is null then c.is_nullable
           else vsc.is_nullable
           end as is_nullable,
       case when sum(case when tc.constraint_type='PRIMARY KEY'then 1 else 0 end)=1 then 'PRI'
            when sum(case when tc.constraint_type='UNIQUE'then 1 else 0 end)=1 then 'UNI'
            else ''
       end as key,
       c.column_default,
       pg_catalog.col_description(cl.oid, c.ordinal_position) as comment
from information_schema.columns c
    join information_schema.tables t on t.table_name=c.table_name
    join information_schema.schemata s on s.schema_name=t.table_schema
    join pg_catalog.pg_class cl on cl.relname=t.table_name
    join pg_catalog.pg_namespace ns on ns.nspname=s.schema_name
    left join information_schema.constraint_column_usage cu on cu.column_name = c.column_name
    left join information_schema.table_constraints tc on tc.table_schema=s.schema_name
    and tc.table_name=t.table_name
    and cu.constraint_name = tc.constraint_name
    and tc.constraint_type in ('UNIQUE', 'PRIMARY KEY')
    left join information_schema.view_column_usage vcu on vcu.view_name=c.table_name
    and vcu.table_schema=c.table_schema
    and vcu.column_name=c.column_name
    left join information_schema.columns vsc on vsc.table_name=vcu.table_name
where s.schema_name='public'  and t.table_name={0}
group by s.schema_name,
    t.table_name,
    c.is_nullable,
    c.column_name,
    c.column_default,
    c.is_nullable,
    cl.oid,
    c.data_type,
    c.ordinal_position,
    c.character_maximum_length,
    c.udt_name,
    vsc.is_nullable
order by s.schema_name,
    t.table_name,
    c.ordinal_position
