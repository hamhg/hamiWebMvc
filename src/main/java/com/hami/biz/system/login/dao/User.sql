<?xml version='1.0' encoding='utf-8'?>
<Statement version='1'>

<sql id='search01'><![CDATA[
SELECT T1.USERNAME NAME,
       T1.PASSWORD PASS,
       T2.AUTHORITY ROLE
  FROM USERS T1
 INNER JOIN AUTHORITIES T2
    ON T1.USERNAME = T2.USERNAME
 WHERE T1.ENABLED = 1
   AND T1.USERNAME = :USERNAME
;]]></sql>

</Statement>