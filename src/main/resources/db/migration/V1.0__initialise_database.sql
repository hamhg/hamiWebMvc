--ALTER PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
--ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE; --Oracle12c 계정생성 이전방식 사용

/* Tablesapce, User ???? ???? ??
-- HAMI (Tablespace) DELETE
DROP TABLESPACE HAMI INCLUDING CONTENTS AND DATAFILES;

-- HAMI (Tablespace) CREATE
CREATE TABLESPACE HAMI DATAFILE
  'C:\app\oradata\orcl\HAMI.DBF' size 1000M AUTOEXTEND ON NEXT 20M MAXSIZE UNLIMITED
LOGGING
ONLINE
  PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT MANUAL
FLASHBACK ON;

-- USER HAMI DELETE
DROP USER HAMI CASCADE;
-- USER HAMI CREATE
CREATE USER HAMI
IDENTIFIED BY hami1234
DEFAULT TABLESPACE HAMI
TEMPORARY TABLESPACE TEMP
QUOTA UNLIMITED ON HAMI;

GRANT CONNECT TO HAMI;
GRANT RESOURCE TO HAMI;
GRANT CREATE VIEW TO HAMI;
*/
