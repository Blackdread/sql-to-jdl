select
    c.table_schema,
    c.table_name,
    c.column_name,
    case
        when c.character_maximum_length is null then c.data_type
        else concat(c.data_type, '(', c.character_maximum_length, ')')
    end as data_type,
    c.data_type,
    c.column_default,
    c.is_nullable,
    c.column_comment comment,
    c.column_key 'key',
    c.ordinal_position
from information_schema.columns c
where c.table_schema=:schemaName
  and c.table_name=:tableName
order by c.ordinal_position;
