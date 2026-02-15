package com.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.service.MemberService;

@Controller
public class LoginController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public String login () {
		
		return "loginForm";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("mId") String mId, @RequestParam("mPw") String mPw, HttpSession session, RedirectAttributes redirectAttrs) {
		
		Member loginMember = memberService.login(mId, mPw);
		
		if(loginMember != null) { //로그인 성공
			
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
		}else { //로그인 실패
			
			redirectAttrs.addFlashAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
			
			return "redirect:/login";
		}
		
		
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 세션 정보 폐기
		
		return"redirect:/";
	}
	
}
