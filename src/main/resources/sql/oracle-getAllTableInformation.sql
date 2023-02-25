select o.OWNER       as schema,
       o.OBJECT_NAME as table_name,
       case
           WHEN v.READ_ONLY = 'Y' THEN 'NO'
           when v.READ_ONLY = 'N' or v.READ_ONLY is null THEN 'YES'
           END       as is_updatable,
       case
           when (o.OBJECT_TYPE = 'VIEW' and tc.COMMENTS is null) then 'view'
           else tc.COMMENTS
           end          "comment",
       case o.OBJECT_TYPE
           when 'VIEW' then 'VIEW'
           when 'TABLE' then 'BASE TABLE'
           end          table_type
from ALL_OBJECTS o
         LEFT JOIN ALL_TABLES t
                   on (o.OWNER = t.OWNER and o.OBJECT_NAME = t.TABLE_NAME and o.OBJECT_TYPE = 'TABLE' AND
                       t.NESTED = 'NO')
         LEFT JOIN ALL_VIEWS v on (o.OWNER = v.OWNER and o.OBJECT_NAME = v.VIEW_NAME and o.OBJECT_TYPE = 'VIEW')
         LEFT JOIN ALL_TAB_COMMENTS tc
                   ON (o.OWNER = tc.OWNER AND
                       o.OBJECT_NAME = tc.TABLE_NAME AND
                       o.OBJECT_TYPE = tc.TABLE_TYPE)
WHERE o.owner =:schemaName
  and (o.OBJECT_TYPE = 'VIEW'
    or o.OBJECT_TYPE = 'TABLE')
order by t.OWNER, is_updatable, o.OBJECT_NAME
