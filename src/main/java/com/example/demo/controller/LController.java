package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LController {
	@GetMapping("/")
	// @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String login() {
		return "LGN001";
		// return new ModelAndView("LGN001", "user", new UserBean());
	}
}
