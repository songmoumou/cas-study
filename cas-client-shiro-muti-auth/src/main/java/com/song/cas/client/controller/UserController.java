package com.song.cas.client.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Value("${server.port}")
	private String port;

	@GetMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("role","VISITOR!");
		return mav;
	}

	@GetMapping("/hello")
	@ResponseBody
	public String index11(){
		return "hello";
	}

	@RequiresPermissions({"admin"})
	@GetMapping("/admin")
	public ModelAndView admin(){
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("role","ADMIN!");
		return mav;
	}

	@GetMapping("/logouttips")
	public ModelAndView logoutTips(){
		ModelAndView mav = new ModelAndView("tips");
		return mav;
	}

	@GetMapping("/logout")
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:http://localhost:8080/cas/logout?service=http://localhost:8081/logouttips";
	}
	@GetMapping("/casFailure")
	@ResponseBody
	public String logout11(){
		return "失败了";
	}
	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public JSONObject ajaxLogin(HttpServletResponse response, String username, String password)
	{

		UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
		Subject subject = SecurityUtils.getSubject();
		try
		{
			subject.login(token);
			JSONObject user=new JSONObject();
			user.put("success",true);
			user.put("username",username);
			user.put("password",password);
			Cookie cookie = new Cookie("user", URLEncoder.encode(JSON.toJSONString(user)));
			cookie.setPath("/");// 这个要设置
			//cookie.setDomain(".aotori.com");//这样设置，能实现两个网站共用
			cookie.setMaxAge(365 * 24 * 60 *  60);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
			response.addCookie(cookie);
			return user;
		}
		catch (Exception e)
		{
			System.out.println("错误12："+e);

			return (JSONObject) new JSONObject().put("success",false);
		}
	}


}
