package com.song.cas.factory;

import com.song.cas.auth.UserInfo;
import com.song.cas.config.Global;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author AJohn
 */

public class DefaultAuth implements BaseAuth {

  @Override
  public UserInfo getUserInfo(String username, String password) throws NoSuchAlgorithmException,
      AccountException, FailedLoginException {
    // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
    dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
    dataSource.setUsername("AIPROJECT");
    dataSource.setPassword("AIPROJECT");
    //TODO 获取配置，替换写死内容
   String data= Global.getConfig("application","cas.logout.redirectParameter");

    // 创建JDBC模板
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource);

    String sql = "select u.login_name as userName, u.* from sys_user u where u.login_name = ?";

    UserInfo info = (UserInfo) jdbcTemplate.queryForObject(sql, new Object[]{username}, new
        BeanPropertyRowMapper(UserInfo.class));
    return info;
  }

  @Override
  public String getPassword(String username,String password,UserInfo info) throws
      NoSuchAlgorithmException {
    //给数据进行md5加密
    MessageDigest md = MessageDigest.getInstance("MD5");
    //md.update((password).toString().getBytes());
    md.update((username + password + info.getSalt()).getBytes());
    String pwd = new BigInteger(1, md.digest()).toString(16);
    return pwd;
  }
}
