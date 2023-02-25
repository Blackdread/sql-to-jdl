select a.OWNER              as                                                      table_schema,
       a.TABLE_NAME,
       LOWER(a.COLUMN_NAME) as                                                      column_name,
       a.CHAR_LENGTH,

       case
           -- NUMBER
           when a.data_type = 'NUMBER' and a.DATA_PRECISION > 0 and a.DATA_SCALE > 0
               then 'NUMBER' || '(' || a.DATA_PRECISION || ',' || a.DATA_SCALE || ')'
           when a.data_type = 'NUMBER' and a.DATA_PRECISION > 0
               then 'NUMBER' || '(' || a.DATA_PRECISION || ')'

           -- CHAR, VARCHAR2, NCHAR, NVARCHAR2
           when (a.data_type in ('CHAR', 'VARCHAR2', 'NCHAR', 'NVARCHAR2')) and a.CHAR_LENGTH > 0
               then a.data_type || '(' || a.CHAR_LENGTH || ')'

           -- FLOAT
           when a.data_type = 'FLOAT' and a.DATA_PRECISION > 0
               then a.data_type || '(' || a.DATA_PRECISION || ')'

           else a.DATA_TYPE
           end                                                                      data_type,

       a.DATA_DEFAULT as                                                            column_default,
       DECODE(a.NULLABLE, 'Y', 'YES', 'N', 'NO')                                    is_nullable,
       c.COMMENTS           as                                                      "COMMENT",
       DECODE((select pc.CONSTRAINT_TYPE
               from all_constraints pc,
                    all_cons_columns pcc
               where pcc.column_name = a.column_name
                 and pcc.constraint_name = pc.constraint_name
                 and pc.constraint_type in ('P', 'U')
                 and pcc.owner = upper(a.OWNER)
                 and pcc.table_name = upper(a.TABLE_NAME)), 'P', 'PRI', 'U', 'UNI') KEY,
       a.COLUMN_ID          as                                                      ordinal_position

from all_tab_columns a,
     ALL_TAB_COMMENTS b,
     ALL_COL_COMMENTS c

WHERE a.OWNER =:schemaName
  AND A.TABLE_NAME =:tableName
  AND A.OWNER = B.OWNER
  AND A.OWNER = c.OWNER
  AND A.TABLE_NAME = B.TABLE_NAME
  AND A.TABLE_NAME = c.TABLE_NAME
  AND A.COLUMN_NAME = c.COLUMN_NAME
ORDER BY a.COLUMN_ID
