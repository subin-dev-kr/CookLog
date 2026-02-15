package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.domain.Paging;
import com.springmvc.service.MemberService;
import com.springmvc.service.RecipeService;
@RequestMapping("/member")
@Controller
public class MemberController {
	@Autowired
	private MemberService memberService; 
	@Autowired
	private RecipeService recipeService;
	
	//회원가입C
	@GetMapping("/joinForm")
	public String join() {
		return "memberJoinForm";
	}
	@PostMapping("/join")
	public String join(@ModelAttribute Member member, RedirectAttributes ra) {
		
		try {
			memberService.join(member);
			ra.addFlashAttribute("msg", "회원가입완료");
			return "redirect:/login";
		} catch(IllegalArgumentException e) {
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/member/joinForm";
		}
	
	}
	
	// 아이디 중복체크 (AJAX)
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("mId") String mId) {
    	
    	if (mId == null || mId.trim().isEmpty()) {
    		return "EMPTY";
    	}
        return memberService.isIdExist(mId.trim()) ? "DUP" : "OK";
    }
    
    // 닉네임 중복체크 (AJAX)
    @GetMapping("/checkNick")
    @ResponseBody
    public String checkNick(@RequestParam("nick") String nick) {

        if (nick == null || nick.trim().isEmpty()) return "EMPTY";

        boolean dup = memberService.isNickNameExist(nick.trim());
        return dup ? "DUP" : "OK";
    }

    // 이메일 중복체크 (AJAX)
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {

        if (email == null || email.trim().isEmpty()) return "EMPTY";

        boolean dup = memberService.isEmailExist(email.trim());
        return dup ? "DUP" : "OK";
    }
    
	//회원페이지(Readone)
    @GetMapping("/myPage")
    public String myPage() {
    	
        return "redirect:/recipe/myList?page=1";
    }
	//회원정보수정U
	@GetMapping("/updateForm")
	public String updateForm(HttpSession session, Model model) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null) {
			
			return "redirect:/login";
		}
		//로그인된 아이디의 회원 정보를 member 에 담는다
		Member member = memberService.myPage(loginMember.getmId());
		model.addAttribute("member", member);
		
		int recipeCount = recipeService.countMyRecipes(loginMember.getmId());
		model.addAttribute("recipeCount", recipeCount);
		
		return "memberUpdateForm";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Member member, HttpSession session, RedirectAttributes ra) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
	    if (loginMember == null) {
	        return "redirect:/login";
	    }

	    // 세션 ID 강제
	    member.setmId(loginMember.getmId());

	    try {
	        memberService.update(member);

	        // 업데이트 성공 후에 최신 정보로 세션 갱신
	        Member updateInfo = memberService.myPage(loginMember.getmId());
	        session.setAttribute("loginMember", updateInfo);

	        ra.addFlashAttribute("msg", "회원정보 수정 완료");

	        return "redirect:/recipe/myList?page=1";

	    } catch (IllegalArgumentException e) {
	        ra.addFlashAttribute("error", e.getMessage());
	        
	        return "redirect:/member/updateForm";
	    }
	}
	
	// 닉네임 중복체크 (수정용: 나 제외)
	@GetMapping("/checkNickUpdate")
	@ResponseBody
	public String checkNickUpdate(@RequestParam("nick") String mNickName, HttpSession session) {

	    Member loginMember = (Member) session.getAttribute("loginMember");
	    if (loginMember == null) return "LOGIN";

	    if (mNickName == null || mNickName.trim().isEmpty()) return "EMPTY";

	    boolean dup = memberService.isNickNameExcptMe(mNickName.trim(), loginMember.getmId());
	    return dup ? "DUP" : "OK";
	}

	// 이메일 중복체크 (수정용: 나 제외)
	@GetMapping("/checkEmailUpdate")
	@ResponseBody
	public String checkEmailUpdate(@RequestParam("email") String email, HttpSession session) {

	    Member loginMember = (Member) session.getAttribute("loginMember");
	    if (loginMember == null) return "LOGIN";

	    if (email == null || email.trim().isEmpty()) return "EMPTY";

	    boolean dup = memberService.isEmailExceptMe(email.trim(), loginMember.getmId());
	    return dup ? "DUP" : "OK";
	}

	//회원탈퇴D
	@PostMapping("/delete")
	public String delete(HttpSession session, RedirectAttributes ra) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");	
		
		if(loginMember == null) {
			
			return "redirect:/login";
		}
		
		try {
		        memberService.delete(loginMember.getmId());

		        // 일반 회원 탈퇴 성공 시 세션 종료
		        session.invalidate();
		        ra.addFlashAttribute("msg", "회원 탈퇴가 완료되었습니다.");
		        return "redirect:/";

		    } catch (IllegalStateException e) {
		        // 관리자 탈퇴 금지 등 비즈니스 예외 처리
		        ra.addFlashAttribute("error", e.getMessage());
		        return "redirect:/member/myPage";
		    }
	}
	/*============================================관리자========================================*/
	//전체회원조회Rall
	@GetMapping("/readAll")
	public String readAll(
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "memberSearchType", required = false) String type,
	        @RequestParam(value = "memberSearchKeyword", required = false) String keyword,
	        Model model) {

	    int pageSize = 20;

	    boolean isSearch = type != null && !type.isEmpty() && keyword != null && !keyword.trim().isEmpty();

	    int totalCount;
	    List<Member> list;
	    Paging paging;

	    if (isSearch) {
	        totalCount = memberService.countSearch(type, keyword);
	        paging = new Paging(page, pageSize, totalCount);
	        list = memberService.searchPage(type, keyword, paging.getOffset(), pageSize);
	    } else {
	        totalCount = memberService.countAll();
	        paging = new Paging(page, pageSize, totalCount);
	        list = memberService.findPage(paging.getOffset(), pageSize);
	    }

	    model.addAttribute("list", list);
	    model.addAttribute("paging", paging);
	    model.addAttribute("memberSearchType", type);
	    model.addAttribute("memberSearchKeyword", keyword);

	    return "memberList";
	}
	
}

