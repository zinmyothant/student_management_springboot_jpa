package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.ClassService;

import com.example.demo.dao.StudentService;
import com.example.demo.dto.ClassDto;
import com.example.demo.dto.ClassDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.StudentBean;
import com.example.demo.model.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class studentController {
	@Autowired
	private StudentService dao;

	@Autowired
	private ClassService cdao;

	@GetMapping("addstudent")
	// @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
	public ModelAndView addstudent(ModelMap map, HttpSession session) {

		ClassDto dto = new ClassDto();
		dto.setId("");
		dto.setName("");
		List<ClassDto> list = cdao.getAllClass();
		session.setAttribute("clist", list);
		return new ModelAndView("BUD002", "student", new StudentBean());
	}

	@PostMapping("setupaddstudent")
	// @RequestMapping(value = "/setupaddstudent", method = RequestMethod.POST)
	public String setupaddstudent(@ModelAttribute("student") @Validated StudentBean bean, BindingResult bs,
			ModelMap map) {
//		if(bs.hasErrors()) {
//			return "BUD002";
//		}
		StudentDto dto = new StudentDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		dto.setRegister(bean.getRegister());
		dto.setStatus(bean.getStatus());
		Optional<StudentDto> list = dao.getStudentById(dto.getStudentId());
		if (!list.isEmpty())
			map.addAttribute("err", "StudentId has been already");
		else {
			int res = dao.save(dto);
			if (res > 0)
				map.addAttribute("msg", "Insert successfully!!");
			else
				map.addAttribute("err", "Insert fail");
		}
		return "BUD002";
	}

	@GetMapping("searchstudent")
	// @RequestMapping(value = "searchstudent", method = RequestMethod.GET)
	public ModelAndView searchstudent() {

		return new ModelAndView("BUD001", "student", new StudentBean());
	}

	@PostMapping("setupsearchstudent")
	// @RequestMapping(value = "setupsearchstudent", method = RequestMethod.POST)
	public String setupsearchstudent(@ModelAttribute("student") StudentBean bean, ModelMap map) {
		List<StudentDto> list = new ArrayList<StudentDto>();
		Optional<StudentDto> opt = Optional.empty();
		StudentDto dto = new StudentDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setRegister(bean.getRegister());
		dto.setClassName(bean.getClassName());
		dto.setStatus(bean.getStatus());
		if (!dto.getStudentId().equals("") || !dto.getStudentName().equals("") || !dto.getClassName().equals("")) {
			opt = dao.getStudentById(dto.getStudentId());
			if (opt.isEmpty())
				map.addAttribute("msg", "student not foung!!");
			else if (opt.isPresent()) {
				map.addAttribute("stulist", opt.get());
			}
			// opt.ifPresent(o->map.addAttribute("stulist",));
		} else {
			list = dao.getAllStudent();
			if (list.size() == 0)
				map.addAttribute("msg", "Student not found!!");
			else
				map.addAttribute("stulist", list);

		}

		return "BUD001";
	}

	@GetMapping("studentupdate")
	// @RequestMapping(value = "/studentupdate", method = RequestMethod.GET)
	public ModelAndView updatestudent(@RequestParam("Id") String id, HttpSession session) {
		ClassDto cdto = new ClassDto();
		cdto.setId("");
		cdto.setName("");
		List<ClassDto> list = cdao.getAllClass();
		session.setAttribute("clist", list);

		StudentDto dto = new StudentDto();
		dto.setStudentId(id);
		Optional<StudentDto> res = dao.getStudentById(dto.getStudentId());

		return new ModelAndView("BUD002-01", "student", res.get());
	}

	@PostMapping("/setupupdatestudent")
	// @RequestMapping(value = "/setupupdatestudent", method = RequestMethod.POST)
	public String setupupdatestudent(@ModelAttribute("student") @Validated StudentBean bean, BindingResult bs,
			ModelMap map) {

		StudentDto dto = new StudentDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		dto.setRegister(bean.getRegister());
		dto.setStatus(bean.getStatus());
		// List<StudentDto> list=dao.select(dto);

		int res = dao.update(dto, dto.getStudentId());
		if (res > 0)
			map.addAttribute("msg", "Update successfully!!");
		else
			map.addAttribute("err", "Update fail");

		return "BUD001";
	}

	@GetMapping("deletestudent")
	// @RequestMapping(value = "/deletestudent", method = RequestMethod.GET)
	public String deletestudent(@RequestParam("id") String Id, ModelMap map) {
		StudentDto dto = new StudentDto();
		dto.setStudentId(Id);
		int res = dao.delete(dto.getStudentId());
		if (res > 0) {
			map.addAttribute("msg", "Delete successful!!");
		} else {
			map.addAttribute("err", "Delete fail");
			return "BUS002-01";
		}
		return "redirect:searchstudent";
	}

	@GetMapping("studentreset")
	// @RequestMapping(value = "studentreset", method = RequestMethod.GET)
	public String reset() {
		return "redirect:searchstudent";
	}

}
