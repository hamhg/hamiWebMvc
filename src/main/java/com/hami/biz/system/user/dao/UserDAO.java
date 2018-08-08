package com.hami.biz.system.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.hami.biz.system.user.model.User;
import com.hami.sys.jdbc.sql.QueryLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
/**
 * <pre>
 * <li>Program Name : UserDAO
 * <li>Description  :
 * <li>History      : 2018. 1. 8.
 * </pre>
 *
 * @author HHG
 */
@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public User getUserInfo(String userid){
        String sql = QueryLoader.getInstance().getElement(this, "search01", null);
        User userInfo = (User)jdbcTemplate.queryForObject(sql, new Object[]{userid},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setCcd(rs.getString("c_cd"));
                        user.setUserid(rs.getString("userid"));
                        user.setPassword(rs.getString("password"));
                        user.setAuthority(rs.getString("authority"));
                        return user;
                    }
                });
        return userInfo;
    }
}