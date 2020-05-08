package com.song.cas.client.base;

import lombok.Data;

/**
 * @author AJohn
 */
@Data
public class SysUser {
  private Integer id;
  private String userName;
  private String password;
  public  SysUser(Integer id,String userName,String password){
    this.id=id;
    this.userName=userName;
    this.password=password;
  }
}
