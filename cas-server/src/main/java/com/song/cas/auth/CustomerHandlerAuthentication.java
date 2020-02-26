package com.song.cas.auth;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;

import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author anumbrella
 */
public class CustomerHandlerAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    public CustomerHandlerAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    public boolean supports(Credential credential) {
        //判断传递过来的Credential 是否是自己能处理的类型
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {

        UsernamePasswordCredential usernamePasswordCredentia = (UsernamePasswordCredential) credential;

        String username = usernamePasswordCredentia.getUsername();
        String password = usernamePasswordCredentia.getPassword();

        System.out.println("username : " + username);
        System.out.println("password : " + password);


        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dataSource.setUsername("AIPROJECT");
        dataSource.setPassword("AIPROJECT");

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql = "select * from sys_user where login_name = ?";

        User info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));

        System.out.println("database username : "+ info.getLogin_name());
        System.out.println("database password : "+ info.getPassword());

        //给数据进行md5加密
        MessageDigest md = MessageDigest.getInstance("MD5");
        //md.update((password).toString().getBytes());
        md.update((username + password + info.getSalt()).getBytes());
        String pwd = new BigInteger(1, md.digest()).toString(16);
        System.out.println("encode方法：加密前（" + password + "），加密后（" + pwd + "）");


        if (info == null) {
            throw new AccountException("Sorry, username not found!");
        }

        if (!info.getPassword().equals(pwd)) {
            throw new FailedLoginException("Sorry, password not correct!");
        } else {

            final List<MessageDescriptor> list = new ArrayList<>();

            return createHandlerResult(usernamePasswordCredentia,
                this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }


    }
}
