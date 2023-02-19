SELECT a.constraint_name,
       c.owner as      table_schema,
       a.table_name,
       a.column_name,
       -- referenced pk
       c.R_OWNER       foreign_table_schema,
       c_pk.table_name foreign_table_name,
       c_pk.table_name foreign_table_name,
       b.COLUMN_NAME   foreign_column_name
FROM all_cons_columns a
         JOIN all_constraints c ON a.owner = c.owner
    AND a.constraint_name = c.constraint_name
         JOIN all_constraints c_pk ON c.r_owner = c_pk.owner
    AND c.r_constraint_name = c_pk.constraint_name
         JOIN all_cons_columns b ON b.OWNER = c_pk.OWNER
    AND b.constraint_name = c_pk.constraint_name
WHERE c.constraint_type = 'R'
  AND A.OWNER =:schemaName
