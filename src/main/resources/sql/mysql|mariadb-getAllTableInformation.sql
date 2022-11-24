select t.table_schema,
       t.table_name,
       case
           when v.is_updatable='NO' then false
           else true
           end as is_updatable,
       case
           when t.table_type='VIEW' and t.table_comment='VIEW' then 'view'
           else t.table_comment
       end as comment,
      t.table_type
from information_schema.tables t
left join information_schema.views v on v.table_schema=t.table_schema
                                    and v.table_name=t.table_name
where t.table_schema=:schemaName
order by t.table_schema,
         v.is_updatable,
         t.table_name;
