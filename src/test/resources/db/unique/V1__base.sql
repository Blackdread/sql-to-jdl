CREATE TABLE student
(
    id        INTEGER auto_increment NOT NULL,
    ssnNumber varchar(100)           NOT NULL,
    CONSTRAINT student_un UNIQUE KEY (ssnNumber),
    CONSTRAINT student_pk PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;
