package com.song.cas.auth;

import com.song.cas.config.Global;
import com.song.cas.factory.AuthFactory;
import com.song.cas.factory.BaseAuth;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;

import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
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

        HttpServletRequest httpServletRequest = Global.getHttpServletRequest();
        UsernamePasswordCredential usernamePasswordCredentia = (UsernamePasswordCredential) credential;

        String username = usernamePasswordCredentia.getUsername();
        String password = usernamePasswordCredentia.getPassword();

        //System.out.println("username : " + username);
        //System.out.println("password : " + password);

        AuthFactory factory=new AuthFactory();
        BaseAuth baseAuth= factory.produce(httpServletRequest.getParameter("client"));
        UserInfo info=baseAuth.getUserInfo(username,password);

        if (info == null) {
            throw new AccountException("Sorry, username not found!");
        }

        String pwd=baseAuth.getPassword(username,password,info);

        //System.out.println("encode方法：加密前（" + password + "），加密后（" + pwd + "）");

        if (!info.getPassword().equals(pwd)) {
            throw new FailedLoginException("Sorry, password not correct!");
        } else {

            final List<MessageDescriptor> list = new ArrayList<>();

            return createHandlerResult(usernamePasswordCredentia,
                this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }


    }
}
