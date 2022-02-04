package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.example.demo.dao.ClassDAOImpl;
import com.example.demo.dto.ClassDto;
import com.example.demo.model.ClassBean;


@Controller
public class ClassController {
	@Autowired
	private ClassDAOImpl dao;
	@RequestMapping(value = "/classform", method = RequestMethod.GET)
	public ModelAndView addclass() {
		return new ModelAndView("BUD003", "classes", new ClassBean());
	}
	@RequestMapping(value="/setupaddclass",method=RequestMethod.POST)
	public String setupaddclass(@ModelAttribute("classes")@Validated ClassBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
			return "BUD003";
		}
		ClassDto dto=new ClassDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<ClassDto> list=dao.select(dto);
		if(list.size()!=0)
			map.addAttribute("err", "Class has been already exist!!");
		else {
			int res=dao.insertData(dto);
			if(res>0)
				map.addAttribute("msg","Insert successful");
			else
				map.addAttribute("err","Insert fail");
		}
			return "BUD003";
	}
}
