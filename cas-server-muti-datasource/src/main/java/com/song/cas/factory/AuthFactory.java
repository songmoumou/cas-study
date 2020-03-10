package com.song.cas.factory;

/**
 *
 * @author AJohn
 */
public class AuthFactory {
  public BaseAuth produce(String type){
    if("test".equals(type)){
      return new TestAuth();
    }else{
      return new DefaultAuth();
    }
  }
}
