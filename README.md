# sql-to-jdl
Tool to translate SQL databases to JDL format of jHipster (Created due to existing databases to be generated with jHipster and build angular-java web)

# Why not use tools like UML provided on jHipster?
- JDL from web is ok for a few entities but not for more than 100 entities and relations
- UML software and xml exporters could have worked (other tools on jHipster) but:
  - already many databases in production to be exported in JDL (faster to generate the JDL from it)
  - already working UML design with MySQL Workbench

# How to use
Set properties file:
- Schema name to export
- Tables names to be ignored
- Path of export file

# After JDL file is generated
Still have some manual steps to do:
- review relations:
  - ManyToMany
  - Owner side display field
  - Inverse side field name and display field
  - Bidirectional or not
- add values to enums
- review validations of entities

# Use of
- jOOQ
- Spring boot
