-- INSERT USERS
INSERT INTO TSYAU0001 (C_CD, LOGIN_ID, USER_ID, PASSWORD, STA_YMD, END_YMD) VALUES ('00','hami','hami','1234','19000101','99991231');

-- INSERT AUTH_GROUP
INSERT INTO TSYAU0104 (C_CD, PROFL_ID, PROFL_NM, ENG_NM, STA_YMD, END_YMD) VALUES ('00','USER','일반','USER','19000101','99991231');
INSERT INTO TSYAU0104 (C_CD, PROFL_ID, PROFL_NM, ENG_NM, STA_YMD, END_YMD) VALUES ('00','ADMIN','어드민','ADMINISTRATOR','19000101','99991231');

-- INSERT AUTHORITIES
INSERT INTO TSYAU0105 (C_CD,USER_ID, PROFL_ID, STA_YMDHMS, END_YMDHMS) VALUES ('00','hami','USER','19000101000000','99991231235959');
INSERT INTO TSYAU0105 (C_CD,USER_ID, PROFL_ID, STA_YMDHMS, END_YMDHMS) VALUES ('00','hami','ADMIN','19000101000000','99991231235959');

-- 공통코드
INSERT INTO TSYST0001 (GRP_CD, COM_CD, STA_YMD, END_YMD, COM_NM, ENG_NM) VALUES ('GRP_CD','COM_CD','19000101','99991231','회사코드','COMPANY_CD');
INSERT INTO TSYST0001 (GRP_CD, COM_CD, STA_YMD, END_YMD, COM_NM, ENG_NM) VALUES ('COM_CD','00','19000101','99991231','회사명','COMPANY');

-- 모듈
INSERT INTO TSYAU0101 (C_CD, MDUL_ID, STA_YMD, END_YMD, MDUL_NM, ENG_NM, ODR_NO, USE_YN) VALUES ('00','SYS','19000101','99991231','시스템','SYSTEM',9,'Y');
INSERT into TSYAU0101 (C_CD, MDUL_ID, STA_YMD, END_YMD, MDUL_NM, ENG_NM, ODR_NO, USE_YN) values ('00','EXP','19000101','99991231','예제','EXAMPLE',8,'Y');

-- 메뉴
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','10000000','ADMIN','SYS','19000101000000','99991231235959','사용자관리','USERS','NODE',NULL,NULL,1,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','11000000','ADMIN','SYS','19000101000000','99991231235959','로그인정보','ACCOUNTS','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'10000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','12000000','ADMIN','SYS','19000101000000','99991231235959','리멤버미','REMEMBERME','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'10000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','20000000','ADMIN','SYS','19000101000000','99991231235959','권한관리','AUTHORITYS','NODE',NULL,NULL,2,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','21000000','ADMIN','SYS','19000101000000','99991231235959','권한그룹','GROUPS','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'20000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','22000000','ADMIN','SYS','19000101000000','99991231235959','권한사용자','GROUP USERS','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'20000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','23000000','ADMIN','SYS','19000101000000','99991231235959','예외권한','AUTH EXCEPTION','MENU',NULL,NULL,3,'Y',NULL,NULL,NULL,'20000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','30000000','ADMIN','SYS','19000101000000','99991231235959','메뉴관리','MENU','NODE',NULL,NULL,3,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','31000000','ADMIN','SYS','19000101000000','99991231235959','모듈(상단메뉴)','CATEGORYS','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'30000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','32000000','ADMIN','SYS','19000101000000','99992313235959','프로그램','PROGRAMS','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'30000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','33000000','ADMIN','SYS','19000101000000','99991231235959','권한별프로그램','AUTH/PROGRAMS','MENU',NULL,NULL,3,'Y',NULL,NULL,NULL,'30000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','40000000','ADMIN','SYS','19000101000000','99991231235959','코드관리','CONSTANTS','NODE',NULL,NULL,4,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','41000000','ADMIN','SYS','19000101000000','99991231235959','공통코드','CODE','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'40000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','42000000','ADMIN','SYS','19000101000000','99991231235959','시스템코드','SYSTEM CONST','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'40000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','43000000','ADMIN','SYS','19000101000000','99991231235959','사업장코드','COMPANYINFO','MENU',NULL,NULL,3,'Y',NULL,NULL,NULL,'40000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','50000000','ADMIN','SYS','19000101000000','99991231235959','게시판관리','BBS','NODE',NULL,NULL,5,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','51000000','ADMIN','SYS','19000101000000','99991231235959','게시판기준','BBS CATAGORYS','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'50000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','52000000','ADMIN','SYS','19000101000000','99991231235959','게시글관리','BBS CONTENTS','MEMU',NULL,NULL,2,'Y',NULL,NULL,NULL,'50000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','60000000','ADMIN','SYS','19000101000000','99991231235959','예제','EXAMPLES','NODE',NULL,NULL,6,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','61000000','ADMIN','SYS','19000101000000','99991231235959','DB테스트','DB TEST','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','62000000','ADMIN','SYS','19000101000000','99991231235959','AJAX','AJAX','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','63000000','ADMIN','SYS','19000101000000','99991231235959','RESTFUL','RESTFUL','MENU',NULL,NULL,3,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','64000000','ADMIN','SYS','19000101000000','99991231235959','FW테스트','FW TEST','MENU',NULL,NULL,4,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','65000000','ADMIN','SYS','19000101000000','99991231235959','DB테스트','DB TEST','MENU',NULL,NULL,5,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','66000000','ADMIN','SYS','19000101000000','99991231235959','SLICKGRID','SLICKGRID','MENU',NULL,NULL,6,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','67000000','ADMIN','SYS','19000101000000','99991231235959','HANDSONTABLE','HANDSONTABLE','MENU',NULL,NULL,7,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','68000000','ADMIN','SYS','19000101000000','99991231235959','TREEGRID','TREEGRID','MENU',NULL,NULL,8,'Y',NULL,NULL,NULL,'60000000','Y','N');

INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','60000000','ADMIN','EXP','19000101000000','99991231235959','예제','EXAMPLES','NODE',NULL,NULL,1,'Y',NULL,NULL,NULL,'00000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','61000000','ADMIN','EXP','19000101000000','99991231235959','DB테스트','DB TEST','MENU',NULL,NULL,1,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','62000000','ADMIN','EXP','19000101000000','99991231235959','AJAX','AJAX','MENU',NULL,NULL,2,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','63000000','ADMIN','EXP','19000101000000','99991231235959','RESTFUL','RESTFUL','MENU',NULL,NULL,3,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','64000000','ADMIN','EXP','19000101000000','99991231235959','FW테스트','FW TEST','MENU',NULL,NULL,4,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','65000000','ADMIN','EXP','19000101000000','99991231235959','DB테스트','DB TEST','MENU',NULL,NULL,5,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','66000000','ADMIN','EXP','19000101000000','99991231235959','SLICKGRID','SLICKGRID','MENU',NULL,NULL,6,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','67000000','ADMIN','EXP','19000101000000','99991231235959','HANDSONTABLE','HANDSONTABLE','MENU',NULL,NULL,7,'Y',NULL,NULL,NULL,'60000000','Y','N');
INSERT INTO TSYAU0107 (C_CD,MENU_ID,PROFL_ID,MDUL_ID,STA_YMDHMS,END_YMDHMS,MENU_NM,ENG_NM,MENU_TYPE_CD,PGM_ID,SQL_ID,ODR_NO,EXP_YN,AUTH_TP_CD,CUD_CD,PRT_CD,PAR_MENU_ID,USE_YN,RE_CERT_YN)
VALUES ('00','68000000','ADMIN','EXP','19000101000000','99991231235959','TREEGRID','TREEGRID','MENU',NULL,NULL,8,'Y',NULL,NULL,NULL,'60000000','Y','N');
