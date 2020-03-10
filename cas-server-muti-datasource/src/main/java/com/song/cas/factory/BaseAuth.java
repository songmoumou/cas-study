package com.song.cas.factory;

import com.song.cas.auth.UserInfo;

import java.security.NoSuchAlgorithmException;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;

/**
 *
 * @author AJohn
 */
public interface BaseAuth {
  UserInfo getUserInfo(String username, String password) throws NoSuchAlgorithmException,
      AccountException, FailedLoginException;

  String getPassword(String username,String password,UserInfo info) throws NoSuchAlgorithmException;
}
