package com.example.demo.controller;


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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.ClassDAOImpl;
import com.example.demo.dao.StudentDAOImpl;
import com.example.demo.dto.ClassDto;
import com.example.demo.dto.ClassDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.StudentBean;
import com.example.demo.model.UserBean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
@Controller

public class studentController {
	@Autowired
	private StudentDAOImpl dao;
	
	@Autowired
	private ClassDAOImpl cdao;
	@RequestMapping(value="/addstudent",method=RequestMethod.GET)
	public ModelAndView addstudent(ModelMap map,HttpSession session) {
		
		ClassDto dto = new ClassDto();
		dto.setId("");
		dto.setName("");
		List<ClassDto> list = cdao.selectAll();
		session.setAttribute("clist", list);
		return new ModelAndView("BUD002","student",new StudentBean());
	}
	@RequestMapping(value="/setupaddstudent",method = RequestMethod.POST)
	public String setupaddstudent(@ModelAttribute("student")@Validated StudentBean bean,BindingResult bs,ModelMap map) {
//		if(bs.hasErrors()) {
//			return "BUD002";
//		}
		StudentDto dto=new StudentDto();
	 dto.setStudentId(bean.getStudentId());
	 dto.setStudentName(bean.getStudentName());
	 dto.setClassName(bean.getClassName());
	 dto.setRegister(bean.getRegister());
	 dto.setStatus(bean.getStatus());
	 List<StudentDto> list=dao.select(dto);
	 if(list.size()!=0)
		 map.addAttribute("err","StudentId has been already");
	 else {
		 int res=dao.insertData(dto);
		 if(res>0)
			 map.addAttribute("msg","Insert successfully!!");
		 else
			 map.addAttribute("err","Insert fail");
	 }
	 	 return "BUD002";
	}
	@RequestMapping(value="searchstudent",method = RequestMethod.GET)
	public ModelAndView searchstudent() {
		
		return new ModelAndView("BUD001","student",new StudentBean());
	}
	@RequestMapping(value="setupsearchstudent",method = RequestMethod.POST)
	public String setupsearchstudent(@ModelAttribute("student")StudentBean bean,ModelMap map) {
		List<StudentDto> list=new ArrayList<StudentDto>();
		
		StudentDto dto=new StudentDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setRegister(bean.getRegister());
		dto.setClassName(bean.getClassName());
		dto.setStatus(bean.getStatus());
		if(!dto.getStudentId().equals("")||!dto.getStudentName().equals("")||!dto.getClassName().equals("")) {
			list=dao.select(dto);
		}else {
			list=dao.selectAll();
		}
		if(list.size()==0)
			map.addAttribute("msg","Student not found!!");
		else
			map.addAttribute("stulist", list);
		return "BUD001";
		
	}
	@RequestMapping(value="/studentupdate",method = RequestMethod.GET)
	public ModelAndView updatestudent(@RequestParam("Id") String id,HttpSession session) {
		ClassDto cdto = new ClassDto();
		cdto.setId("");
		cdto.setName("");
		List<ClassDto> list = cdao.selectAll();
		session.setAttribute("clist", list);

		StudentDto dto=new StudentDto();
		dto.setStudentId(id);
		List<StudentDto> res=dao.select(dto);
		StudentBean bean=new StudentBean();
		for(StudentDto result : res) {
			bean.setStudentId(result.getStudentId());
			bean.setStudentName(result.getStudentName());
			bean.setClassName(result.getClassName());
			bean.setRegister(result.getRegister());
			bean.setStatus(result.getStatus());
				}
		return new ModelAndView("BUD002-01","student",bean);
	}
	@RequestMapping(value="/setupupdatestudent",method = RequestMethod.POST)
	public String setupupdatestudent(@ModelAttribute("student")@Validated StudentBean bean,BindingResult bs,ModelMap map) {
		
		StudentDto dto=new StudentDto();
	 dto.setStudentId(bean.getStudentId());
	 dto.setStudentName(bean.getStudentName());
	 dto.setClassName(bean.getClassName());
	 dto.setRegister(bean.getRegister());
	 dto.setStatus(bean.getStatus());
	// List<StudentDto> list=dao.select(dto);
		
		 int res=dao.updateData(dto);
		 if(res>0)
			 map.addAttribute("msg","Update successfully!!");
		 else
			 map.addAttribute("err","Update fail");
	 
	 	 return "BUD001";
	}
	@RequestMapping(value="deletestudent",method = RequestMethod.GET)
	public String deletestudent(@RequestParam("id") String Id,ModelMap map) {
		StudentDto dto=new StudentDto();
		dto.setStudentId(Id);
		int res=dao.deleteData(dto);
		if(res>0) {
			map.addAttribute("msg","Delete successful!!");
		}else {
			map.addAttribute("err", "Delete fail");
		return "BUS002-01";
		}
		return "redirect:/searchstudent";
	}
	@RequestMapping(value="studentreset",method=RequestMethod.GET)
	public String reset() {
		return "redirect:/searchstudent"; 
	}
	
}

