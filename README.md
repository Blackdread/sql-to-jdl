[![build](https://github.com/Blackdread/sql-to-jdl/actions/workflows/maven.yml/badge.svg)](https://github.com/Blackdread/sql-to-jdl/actions/workflows/maven.yml)
[![StackShare](https://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/Blackdread/sql-to-jdl)

# sql-to-jdl
Tool to translate SQL databases to JDL format of jHipster (Created due to existing databases to be generated with jHipster and build angular-java web)

# Usage

```bash
#Download
git clone https://github.com/Blackdread/sql-to-jdl.git

# Modify configuration file application.yml at https://github.com/Blackdread/sql-to-jdl/blob/master/src/main/resources/application.yml

#Build, skip the tests for lack of time
mvn clean package -DskipTests

#Run
java -jar target/sql-to-java-*-SNAPSHOT.jar
```

Direct link to [application.yml](https://github.com/Blackdread/sql-to-jdl/blob/master/src/main/resources/application.yml)

_Don't forget to install git, maven 3 and java 17 before launching_

# Compatibility
This implementation works with 
  - mysql 5.7.x, 8.x
  - mariadb 10.x
  - postgresql 9.x+
  - oracle 21
  - MsSQL 2019

Help is requested on MsSQL and Oracle support.  This will require implementation of SqlJdlTypeService and creating the raw SQL files.  See the MySQL, MariaDB, and PosrgreSQL implemtaitons for examples.

# Testing
Setting up tests for new databases types or versions is easy.  Have a look existing tests.  
MsSQL and Oracle have tests created that are disabled because their implementations are incomplete.

Tests use liquibase so that most of the setup for tests is database agnostic.  The main exception is for the all types test that is different for each database type.

Each test needs the following files
  - {{test.name}}-liquibase-changeset.yaml
  - {{test.name}}-expected.jdl
  
To override the default name the files as
  - {{test.name}}-liquibase-changeset-{{db.typ}}.yaml
  - {{test.name}}-expected-{{db.type}}.jdl

If you need to change the tests run for a specific database type or version, use method hiding by implementing 

private static Stream<String> provideTestNames() in the subclass of SqlToJdlTransactionPerTestTest

In order to avoid starting a new database and spring boot container for every test, liquibase is used to roll back the database to an empty state.  This can be done by just rolling back the transaction with Spring, but MySQL does not support rollback of DDL changes.  This works great with PostgreSQL, but liquibase is used to roll back the changes for all database types currently.

Currently the full test suite runs 11 test per database version which total nearly 80 tests.  
 
# Why not use tools like UML provided on jHipster?
- JDL from web is ok for a few entities but not for more than 100 entities and relations
- UML software and xml exporters could have worked (other tools on jHipster) but:
  - already many databases in production to be exported in JDL (faster to generate the JDL from it)
  - already working UML design with MySQL Workbench

# How to use
Just execute the code from IDE or use "mvn" to run the code, it will connect to your DB (see application config yml) and it will generate the JDL.

Set properties file:
- Schema name to export
- Tables names to be ignored
- Path of export file
- Database object prefixes to remove from entity name
- Include table name is JDL
- Undefined JDL type handling to ERROR, SKIP, or UNSUPPORTED
- Add JDL type overrides if necessary.

# After JDL file is generated
Still have some manual steps to do:
- review relations:
  - ManyToMany
  - Owner side display field
  - Inverse side field name and display field
  - Bidirectional or not
- add values to enums
- review validations of entities

# Default specific rules
Table is treated as enum if only 2 columns and both are: "id" AND ("code" OR "name")

Table is treated as ManyToMany if only 2 columns and both are foreign keys

# Links
[jHipster JDL](https://www.jhipster.tech/jdl/intro)

# An alternative for REST filter and sort
Different criterias, support for JPA and jOOQ dynamic filtering and sorting.

https://github.com/Blackdread/rest-filter
