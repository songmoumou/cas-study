package com.song.cas.config;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 *
 * @author AJohn
 */
public class Global {
  //private static String NAME = "application.yml";
  //
  ///**
  // * 当前对象实例
  // */
  //private static Global global = null;
  //
  ///**
  // * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
  // */
  //
  //public static synchronized Global getInstance() {
  //  if (global == null) {
  //    synchronized (Global.class) {
  //      if (global == null) {
  //        global = new Global();
  //      }
  //
  //    }
  //  }
  //  return global;
  //}


  /**
   * 获取当前请求session
   * @return
   */
  public static HttpServletRequest getHttpServletRequest(){
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
        .getRequestAttributes())
        .getRequest();
    return request;
  }
  /**
   * 获取当前请求session
   * @return
   */
  public static HttpSession getHttpSession(){
    return getHttpServletRequest().getSession();
  }

  public static String getConfig(String fileName, String key) {
    Locale locale = new Locale("zh", "CN");
    ResourceBundle bundle = ResourceBundle.getBundle(fileName, locale);

    return bundle.getString(key);
  }
}
