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
	@GetMapping("/join")
	public String join() {
		return "memberJoinForm";
	}
	@PostMapping("/join")
	public String join(@ModelAttribute Member member ,Model model) {
		
		if(memberService.isIdExist(member.getmId())) {
	        model.addAttribute("idError", "이미 사용중인 아이디입니다.");
	        return "joinForm"; // 다시 회원가입 페이지
	    }
		
		if (memberService.isNickNameExist(member.getmNickName())) {
	        model.addAttribute("nickError", "이미 사용중인 닉네임입니다.");
	        return "joinForm";
	    }
		
		memberService.join(member);
		
		return "redirect:/";
	}
	
	// 아이디 중복체크 (AJAX)
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("mId") String mId) {
    	
    	if (memberService.isIdExist(mId)) {
          
    		return "DUP";
        }
        
    	return "OK";
    }
    //닉네임 중복체크 (AJAX)
    @GetMapping("/checkNickName")
    @ResponseBody
    public String checkNickName(@RequestParam("mNickName") String mNickName) {
    	
    	 if (memberService.isNickNameExist(mNickName)) {
    	
    		 return "DUP";
    	    }
	    
    	 return "OK";
    }
    
    //이메일 중복체크 (AJAX)
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {
    	
    	if (memberService.isEmailExist(email)) {
    		
    		return "DUP";
    	}
    	return "OK";
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
		}//로그인된 아이디의 회원 정보를 member 에 담는다
		Member member = memberService.myPage(loginMember.getmId());
		model.addAttribute("member", member);
		
		int recipeCount = recipeService.countMyRecipes(loginMember.getmId());
		model.addAttribute("recipeCount", recipeCount);
		
		return "memberUpdateForm";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Member member, HttpSession session) {
		
		Member updateMember = (Member) session.getAttribute("loginMember");
		if(updateMember == null) {
			
			return "redirect:/login";
		}
		// 세션의 ID를 member에 적용(mId가 null이 되는거를 방지하고, 로그인된 사용자 본인만 정보수정 가능!)
		member.setmId(updateMember.getmId());
		// DB업데이트
		memberService.update(member);
		
		// 수정된 회원 정보를 updateInfo 에 담음(회원정보 초기화)
		Member updateInfo =  memberService.myPage(updateMember.getmId());
		session.setAttribute("loginMember", updateInfo);
		
		return "redirect:/member/myPage";
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
	
	@PostMapping("/adminDeleteRecipe")
	public String adminDeleteRecipe(@RequestParam("recipeId") int recipeId, HttpSession session) {
	    Member loginMember = (Member) session.getAttribute("loginMember");
	    
	    // 관리자 권한 체크 (예: m_role 또는 m_id가 'admin'인지 확인)
	    if (loginMember != null && loginMember.getmRole() == 1) {
	        recipeService.delete(recipeId); // 레시피 삭제 서비스 호출
	        return "redirect:/admin/recipeList";
	    }
	    
	    return "redirect:/login"; // 권한 없으면 로그인으로
	}
}

