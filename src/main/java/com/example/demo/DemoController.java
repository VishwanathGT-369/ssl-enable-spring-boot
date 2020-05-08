package com.example.demo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class DemoController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String getGreet(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = new Cookie("test", "testing");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
	//	cookie.setMaxAge(7 * 24 * 60 * 60);
		res.addCookie(cookie);
		return "Hello";
	}

}
