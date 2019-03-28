package com.douzone.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
		
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {

		if (result.hasErrors()) {
//			// 에러 출력
//			List<ObjectError> list = result.getAllErrors();
//			for (ObjectError e : list) {
//				System.out.println(" ObjectError : " + e);
//			}
			
			model.addAllAttributes(result.getModel());
			return "user/join";
		}

		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

//	@RequestMapping(value="/login", method=RequestMethod.POST) 
//	public ModelAndView login(HttpSession session, 
//			@RequestParam("email") String email, @RequestParam("password") String password) {
//		UserVo loginUser = userService.login(email,password);
//		
//		ModelAndView mav = new ModelAndView();
//		if(loginUser == null)
//		{
//			mav.addObject("result","fail");
//			mav.setViewName("/user/login");
//		}
//		else {
//			session.setAttribute("loginuser", loginUser);
//			mav.setViewName("redirect:/");
//		}
//		return mav;
//	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logout(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		
//		if(session != null && session.getAttribute("loginuser") != null)
//		{
//			// logout 처리
//			session.removeAttribute("loginuser");
//			session.invalidate();
//		}
//		return "redirect:/";
//	}

	@Auth(Role.USER)
	@GetMapping(value = "/modify")
	public String modify(@AuthUser UserVo loginuser, Model model) {

		UserVo userVo = userService.getUser(loginuser.getNo());
		model.addAttribute("userVo", userVo);

		return "/user/modify";
	}

	@Auth(Role.USER)
	@PostMapping(value = "/modify")
	public String modify(@AuthUser UserVo loginuser, @ModelAttribute UserVo userVo) {

		userVo.setNo(loginuser.getNo());
		userService.modify(userVo);

		// session의 loginuser 변경
		loginuser.setName(userVo.getName());

		return "/user/modifysuccess";
	}

	@GetMapping("/modifysuccess")
	public String modifySuccess() {
		return "/user/modifysuccess";
	}
}