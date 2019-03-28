package com.douzone.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping({"/hello", "/a/b/c/d"})
	public String hello() {
		return "hello";
	}
}
