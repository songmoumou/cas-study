package com.song.cas.client.base;

import org.springframework.stereotype.Service;

/**
 * @author AJohn
 */
@Service
public class UserServeice {
  public SysUser login(String username, String password) throws Exception {
    if("admin".equals(username)&&"admin123".equals(password)){
      return new SysUser(1,"admin","admin123");
    }
    throw new Exception("登陆失败，用户名密码错误");
  }
}
