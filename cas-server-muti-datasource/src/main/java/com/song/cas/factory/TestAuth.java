package com.song.cas.factory;

import com.song.cas.auth.UserInfo;

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
public class TestAuth implements BaseAuth {
  @Override
  public UserInfo getUserInfo(String username, String password) throws NoSuchAlgorithmException, AccountException, FailedLoginException {
    // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
    dataSource.setUsername("root");
    dataSource.setPassword("abc123");

    // 创建JDBC模板
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource);

    String sql = "select * from user where userName = ?";

    UserInfo info = (UserInfo) jdbcTemplate.queryForObject(sql, new Object[]{username}, new
        BeanPropertyRowMapper(UserInfo.class));
    return info;
  }

  @Override
  public String getPassword(String username, String password, UserInfo info) throws NoSuchAlgorithmException {
    //给数据进行md5加密
    MessageDigest md = MessageDigest.getInstance("MD5");
    //md.update((password).toString().getBytes());
    md.update((username + password).getBytes());
    String pwd = new BigInteger(1, md.digest()).toString(16);
    return pwd;
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    //md.update((password).toString().getBytes());
    md.update(("admin" + "111111" ).getBytes());
    String pwd = new BigInteger(1, md.digest()).toString(16);
    System.out.println(pwd);

  }
}
