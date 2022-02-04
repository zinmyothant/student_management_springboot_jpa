package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.engine.AttributeName;

import com.example.demo.dao.UserDAOImpl;
import com.example.demo.dto.UserDto;
import com.example.demo.model.UserBean;

@Controller

public class LoginController {
	@Autowired
	private UserDAOImpl dao;
	//login - get
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		
		return new ModelAndView("LGN001", "user", new UserBean());
	}
	//login - post
	@RequestMapping(value = "/setuplogin", method = {RequestMethod.POST,RequestMethod.GET})
	public String setuplogin(@ModelAttribute("user") @Validated UserBean bean, BindingResult rs, ModelMap map,
			HttpSession session) {

	if(rs.hasErrors()) {
		return "LGN001";
	}
	UserDto dto = new UserDto();

	dto.setId(bean.getId());
	List<UserDto> list=dao.select(dto);
	
	if(!dto.getId().equals("")||!dto.getName().equals("")) {
		dao.select(dto);
	}else {
		dao.selectAll();
	}
	if (list.size() == 0) {
		map.addAttribute("err", "User not found!!");
		return "LGN001";
	} else if (bean.getPassword().equals(list.get(0).getPassword())) {
		session.setAttribute("id", list.get(0).getId());
		session.setAttribute("userName", list.get(0).getName());
		
		return "M00001";
	} else {
		map.addAttribute("err", "Password is incorrect!!");
		return "LGN001";
	}


	}
	@RequestMapping(value="welcome",method=RequestMethod.GET)
	public String welcome() {
		return "redirect:/setuplogin";
	}
	//logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session,SessionStatus status) {
		status.setComplete();
		session.invalidate();
		
		return "redirect:/";
	}
	//usersearch - get
	@RequestMapping(value="/usersearch",method=RequestMethod.GET)

	public ModelAndView usersearch() {
		return new ModelAndView("USR001","user",new UserBean());
	}
	//user register - get
	@RequestMapping(value="useradd",method=RequestMethod.GET)
	public ModelAndView useradd() {
		return new ModelAndView("USR002","user",new UserBean());
	}
	//user register - post
	@RequestMapping(value="/setupadduser",method=RequestMethod.POST)
	public String setupuseradd(@ModelAttribute("user")@Validated UserBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
		
			return "USR002";
		}
				if(bean.getPassword().equals(bean.getConfirm())) {
				UserDto dto=new UserDto();
				dto.setId(bean.getId());
				List<UserDto> list=dao.select(dto);
			
			if(list.size()!=0) {
				map.addAttribute("err","User Id has been already!!");
				
					//map.addAttribute("err","User Id has been already!!");
			}else 
			{
				dto.setName(bean.getName());
				dto.setPassword(bean.getPassword());
					//return "USR002";
				int res=dao.insertData(dto);
				if(res>0) 
					map.addAttribute("msg", "Insert successfully");
				else
					map.addAttribute("err","Insert fail");
					return "USR002";
				
			}
			
			}else
				map.addAttribute("err","Password are not match");
		
		return "USR002";
	}
	//user register - usersearch
	@RequestMapping(value="/setupusersearch",method = RequestMethod.POST)
	public String setupusersearch(@ModelAttribute("user")UserBean bean,ModelMap map) {
		UserDto dto=new UserDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<UserDto> list=new ArrayList<UserDto>();
		if(!dto.getId().equals("")||!dto.getId().equals("")) {
			list=dao.select(dto);
		}else {
			list=dao.selectAll();
		}
		if(list.size()==0)
			map.addAttribute("msg","User not found");
		else
			map.addAttribute("userlist",list);
	return "USR001";
	}
	//user delete - get
	@RequestMapping(value="/userdelete",method = RequestMethod.GET)
	public String userdelete(@RequestParam("uid") String id ,ModelMap map,HttpSession session) {
		UserDto dto=new UserDto();
		dto.setId(id);
		int res=dao.deleteData(dto);
		if(dto.getId().equals(session.getAttribute("id"))) {
			map.addAttribute("err","Canot delete this current user ");
		}else {
		if(res>0) 
			map.addAttribute("msg","Delete successful!!");
		else
			map.addAttribute("err", "Delete fail");
		}
		return "redirect:/usersearch";
	
	}
	@RequestMapping(value="/userupdate",method = RequestMethod.GET)
	
	public ModelAndView userupdate(@RequestParam("uid") String id) {
		UserDto dto=new UserDto();
	
		dto.setId(id);
		List<UserDto> res=dao.select(dto);
		UserBean bean=new UserBean();
		for(UserDto result : res) {
			bean.setId(result.getId());
			bean.setName(result.getName());
			bean.setPassword(result.getPassword());
			bean.setConfirm(result.getPassword());
		}
	
		return new ModelAndView("USR002-01","user",bean);
	}
	@RequestMapping(value="/userupdate",method=RequestMethod.POST)
	public String updateuser(@ModelAttribute("user")@Validated UserBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
			return "USR002-01";
		}
				if(bean.getPassword().equals(bean.getConfirm())) {
				UserDto dto=new UserDto();
				dto.setId(bean.getId());
				dto.setName(bean.getName());
				dto.setPassword(bean.getPassword());
			//List<UserResponseDto> list=dao.select(dto);
			
				int res=dao.updateData(dto);
				if(res>0) 
					map.addAttribute("msg", "Update successfully");
				else                           
					map.addAttribute("err","Insert fail");
					return "USR002-01";
			}
					map.addAttribute("err","Password are not match");
		
		return "USR002-01";
	}
	
	@RequestMapping(value="userreset",method=RequestMethod.GET)
	public String reset() {
		return "redirect:/usersearch";
	}
}
