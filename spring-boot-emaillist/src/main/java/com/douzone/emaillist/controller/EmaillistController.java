package com.douzone.emaillist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.emaillist.dao.EmaillistDao;
import com.douzone.emaillist.vo.EmaillistVo;

@Controller
public class EmaillistController {

	// 자동주입
	@Autowired
	private EmaillistDao dao;
	
//	@ResponseBody
//	@RequestMapping("")
//	public ModelAndView list() {
//		
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(dao.getList());
//		mav.setViewName("/WEB-INF/views/list.jsp");
//		
//		return mav;
//	}
	
	@RequestMapping({"","/list"})
	public String list(Model model) {
		
		model.addAttribute("list", dao.getList());
				
		return "list";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "form";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute EmaillistVo vo) {
		dao.insert(vo);
		
		return "redirect:/";
	}
}
