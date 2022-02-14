package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.engine.AttributeName;

import com.example.demo.SecurityConfigure;
import com.example.demo.dao.UserService;
import com.example.demo.dto.ClassDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.UserBean;

@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder encode;

	@Autowired
	private UserService userService;

	@GetMapping("/welcome")
	public String welcome() {
		return "M00001";
	}

	// login - post
	// @RequestMapping(value = "/setuplogin", method = RequestMethod.POST)
//	public String setuplogin(@ModelAttribute("user") @Validated UserBean bean, BindingResult rs, ModelMap map,
//			HttpSession session) {
//
//		if (rs.hasErrors()) {
//
//			return "LGN001";
//		}
//		UserDto dto = new UserDto();
//		// System.out.println("lskdjf");
//		dto.setId(bean.getId());
//		Optional<UserDto> list = userService.getUserById(dto.getId());
//		// bean.setPassword(encode.encode(bean.getPassword()));
//		encode = config.encoder();
//		// dto.setPassword(bean.getPassword());
//		// dto.setPassword(list.get().getPassword());
//		if (list.isEmpty()) {
//			map.addAttribute("err", "User not found!!");
//			return "LGN001";
//		}
//		if (list.isPresent()) {
////			System.out.println(dto.getPassword().equals(list.get().getPassword()));
////			System.out.println(encode.matches(dto.getPassword(),list.get().getPassword()));
////			System.out.println(list.get().getPassword());
//			System.out.println(dto.getPassword());
//			if (dto.getPassword().equals(list.get().getPassword())) {
//
//				session.setAttribute("id", list.get().getId());
//				session.setAttribute("userName", list.get().getName());
//
//				return "M00001";
//
//			} else
//				map.addAttribute("err", "Password is incorrect!!");
//		}
//		return "LGN001";
//
//	}

	// logout
	@GetMapping("/logout")
	// @RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, SessionStatus status) {
		status.setComplete();
		session.invalidate();

		return "redirect:/";
	}

	// usersearch - get
	@GetMapping("/usersearch")
	// @RequestMapping(value = "/usersearch", method = RequestMethod.GET)

	public ModelAndView usersearch() {
		return new ModelAndView("USR001", "user", new UserBean());
	}

	// user register - get
	@GetMapping("useradd")
	// @RequestMapping(value = "useradd", method = RequestMethod.GET)
	public ModelAndView useradd() {
		return new ModelAndView("USR002", "user", new UserBean());
	}

	// user register - post
	@PostMapping("setupadduser")
	// @RequestMapping(value = "/setupadduser", method = RequestMethod.POST)
	public String setupuseradd(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {

			return "USR002";
		}
		System.out.println("aldkf");
		if (bean.getPassword().equals(bean.getConfirm())) {
			UserDto dto = new UserDto();
			dto.setId(bean.getId());
			Optional<UserDto> list = userService.getUserById(dto.getId());
			// Optional<UserDto> classBean=classService.getFindById(dto.getId());

			if (!list.isEmpty()) {
				map.addAttribute("err", "User Id has been already!!");

				// map.addAttribute("err","User Id has been already!!");
			} else {
				dto.setName(bean.getName());
				dto.setPassword(encode.encode(bean.getPassword()));
				dto.setRole("ROLE_admin");
				dto.setEnable(true);
				// return "USR002";
				int res = userService.save(dto);
				if (res > 0)
					map.addAttribute("msg", "Insert successfully");
				else
					map.addAttribute("err", "Insert fail");
				return "USR002";

			}

		} else
			map.addAttribute("err", "Password are not match");

		return "USR002";
	}

	// user register - usersearch
	@PostMapping("/setupusersearch")
	// @RequestMapping(value = "/setupusersearch", method = RequestMethod.POST)
	public String setupusersearch(@ModelAttribute("user") UserBean bean, ModelMap map) {
		UserDto dto = new UserDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		Optional<UserDto> opt = Optional.empty();
		List<UserDto> list = new ArrayList<UserDto>();
		if (!dto.getId().equals("") || !dto.getId().equals("")) {
			opt = userService.getUserById(dto.getId());
			if (opt.isEmpty())
				map.addAttribute("msg", "User not found");
			else if (opt.isPresent()) {
				map.addAttribute("userlist", opt.get());
			}
			return "USR001";
		} else {
			list = userService.getAllUser();
			if (list.size() == 0)
				map.addAttribute("msg", "User not found");
			else
				map.addAttribute("userlist", list);
			return "USR001";

		}
	}

	// user delete - get
	@GetMapping("userdelete")
	// @RequestMapping(value = "/userdelete", method = RequestMethod.GET)
	public String userdelete(@RequestParam("uid") String id, ModelMap map, HttpSession session) {
		UserDto dto = new UserDto();
		dto.setId(id);
		int res = userService.delete(dto.getId());
		if (dto.getId().equals(session.getAttribute("id"))) {
			map.addAttribute("err", "Canot delete this current user ");
		} else {
			if (res > 0)
				map.addAttribute("msg", "Delete successful!!");
			else
				map.addAttribute("err", "Delete fail");
		}
		return "redirect:usersearch";

	}

	@GetMapping("/userupdate")
	// @RequestMapping(value = "/userupdate", method = RequestMethod.GET)

	public ModelAndView userupdate(@RequestParam("uid") String id) {
		UserDto dto = new UserDto();

		dto.setId(id);

		return new ModelAndView("USR002-01", "user", userService.getUserById(id));
	}

	@PostMapping("/userupdate")
//	@RequestMapping(value = "/userupdate", method = RequestMethod.POST)
	public String updateuser(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {
			return "USR002-01";
		}
		if (bean.getPassword().equals(bean.getConfirm())) {
			UserDto dto = new UserDto();
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(encode.encode(bean.getPassword()));

			// List<UserResponseDto> list=userService.select(dto);

			int res = userService.update(dto, dto.getId());
			if (res > 0)
				map.addAttribute("msg", "Update successfully");
			else
				map.addAttribute("err", "Insert fail");
			return "USR002-01";
		}
		map.addAttribute("err", "Password are not match");

		return "USR002-01";
	}

	@GetMapping("/userreset")
	// @RequestMapping(value = "userreset", method = RequestMethod.GET)
	public String reset() {
		return "redirect:usersearch";
	}
}
