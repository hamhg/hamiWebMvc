package com.hami.biz.system.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : FlyWayMigrate
 * <li>Description  :
 * <li>History      : 2017. 7. 22.
 * </pre>
 *
 * @author HHG
 */
public class FlyWayMigrate {

    public static void main(String[] args) {

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        //flyway.clean();
        flyway.setTarget(MigrationVersion.LATEST);
        flyway.migrate();

        //Password encoding
        if(true){
            String password;
            String userid;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
            List<Map<String, Object>> userLists = jdbcTemplate.queryForList("SELECT USER_ID, PASSWORD FROM TSYAU0001");
            
            if (!userLists.isEmpty()) {
                for (Map user : userLists) {
                    userid = String.valueOf(user.get("user_id"));
                    password = passwordEncoder.encode(String.valueOf(user.get("password")));
                    jdbcTemplate.update("UPDATE TSYAU0001 SET PASSWORD = ? WHERE USER_ID = ?", password, userid);
                }
            }
        }

    }

    public static DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521/ORCL");
        dataSource.setUsername("hami");
        dataSource.setPassword("hami1234");

        return dataSource;
    }

}
