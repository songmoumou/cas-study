package com.song.cas.client.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

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


}
