# sql-to-jdl
Tool to translate SQL databases to JDL format of jHipster (Created due to existing databases to be generated with jHipster and build angular-java web)

# Why not use tools like UML provided on jHipster?
- JDL from web is ok for a few entities but not for more than 100 entities and relations
- UML software and xml exporters could have worked (other tools on jHipster) but:
  - already many databases in production to be exported in JDL (faster to generate the JDL from it)
  - already working UML design with MySQL Workbench

# How to use
Propertie file contains:
- Schema name to export
- Tables names to be ignored
- Path of export file

# Use of
- jOOQ
- Spring boot
