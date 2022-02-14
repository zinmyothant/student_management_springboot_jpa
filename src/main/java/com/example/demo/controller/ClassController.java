package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.ClassService;
import com.example.demo.dto.ClassDto;
import com.example.demo.model.ClassBean;

@Controller
@RequestMapping("/admin")
public class ClassController {
	@Autowired
	private ClassService classService;

	@GetMapping("/classform")
	// @RequestMapping(value = "/classform", method = RequestMethod.GET)
	public ModelAndView addclass() {
		return new ModelAndView("BUD003", "classes", new ClassBean());
	}

	@PostMapping("/setupaddclass")
	// @RequestMapping(value = "/setupaddclass", method = RequestMethod.POST)
	public String setupaddclass(@ModelAttribute("classes") @Validated ClassBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {
			return "BUD003";
		}
		ClassDto dto = new ClassDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		Optional<ClassDto> classBean = classService.getFindById(dto.getId());
		if (!classBean.isEmpty())
			map.addAttribute("err", "Class has been already exist!!");
		else {
			int res = classService.save(dto);
			if (res > 0)
				map.addAttribute("msg", "Insert successful");
			else
				map.addAttribute("err", "Insert fail");
		}
		return "BUD003";
	}
}
