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

import com.springmvc.domain.Member;
import com.springmvc.domain.Notice;
import com.springmvc.domain.Paging;
import com.springmvc.service.NoticeServiceImpl;

@RequestMapping("/notice")
@Controller
public class NoticeController {
	@Autowired
	private NoticeServiceImpl noticeService;
	
	//게시글 작성
	@GetMapping("/create")
	public String create() {
	
		return "noticeForm";
	}
	@PostMapping("/create")
	public String create(@ModelAttribute Notice notice, HttpSession session, Model model) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		
		notice.setmId(loginMember.getmId()); //세션에서 로그인한 ID를 불러와서 공지사항 회원ID에 저장함
		notice.setmNickName(loginMember.getmNickName()); //세션에서 로그인한 닉네임을 불러와서 공지사항 닉네임에 저장함
		
		Notice savedNotice = noticeService.create(notice);
		if(savedNotice != null) {
			
			model.addAttribute("notice", savedNotice);
			return "redirect:/notice/readSome?page=1";			
		} else {
			
			model.addAttribute("errorMsg", "공지사항 저장에 실패했습니다");
			return "errorPage";
		}
		
	}
	//공지사항 리스트R_all
	@GetMapping("/list")
	public String list(Model model) {
		
		return "redirect:/notice/readSome?page=1";
	}
	
	@GetMapping("/setNoticeSession")
	public String setNoticeSession(@RequestParam("num") int nNum, HttpSession session) {
	    session.setAttribute("currentNoticeNum", nNum);
	    return "redirect:/notice/readOne"; // 세션에서 번호를 꺼내서 상세보기
	}
	
	//게시글 검색 & 페이징
	@GetMapping("/readSome")
	public String readSome(
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "noticeSearchType", required = false) String type,
	        @RequestParam(value = "noticeSearchKeyword", required = false) String keyword,
	        Model model) {

	    int pageSize = 10;
	    boolean isSearch =
	            type != null && !type.isEmpty() &&
	            keyword != null && !keyword.trim().isEmpty();

	    int totalCount;
	    List<Notice> list;
	    Paging paging;

	    if (isSearch) {
	        totalCount = noticeService.countSearch(type, keyword);
	        paging = new Paging(page, pageSize, totalCount);
	        list = noticeService.searchPage(type, keyword, paging.getOffset(), pageSize);
	    } else {
	        totalCount = noticeService.countAll();
	        paging = new Paging(page, pageSize, totalCount);
	        list = noticeService.findPage(paging.getOffset(), pageSize);
	    }

	    model.addAttribute("list", list);
	    model.addAttribute("paging", paging);
	    model.addAttribute("noticeSearchType", type);
	    model.addAttribute("noticeSearchKeyword", keyword);

	    return "noticeList";
	}

	
	//게시글 상세보기R_one
	@GetMapping("/readOne")
	public String readOne(@RequestParam("nNum") Integer nNum , Model model) {
		
		// 조회수 증가 서비스 메서드 호출
	    noticeService.increaseViewCount(nNum);
		
		Notice notice = noticeService.readOne(nNum);
		
		if (notice == null) {
			return "redirect:/notice/list";
		}
		
		
		model.addAttribute("notice", notice);
		
		return "noticeView";
	}
	//게시글 수정
	@GetMapping("/updateNoticeForm")
	public String updateNoticeForm(@RequestParam("nNum") Integer nNum, HttpSession session, Model model) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		
		if (nNum == null) {
	        return "redirect:/notice/list";
	    }
		// 해당 게시글 정보를 DB에서 조회
		Notice noticeInfo = noticeService.readOne(nNum);
		
		if (noticeInfo == null) {
	        return "redirect:/notice/list";
	    }
		
		model.addAttribute("notice", noticeInfo);
		
		return "noticeUpdateForm";
	}
	@PostMapping("/update")
	public String update(@ModelAttribute Notice notice, HttpSession session) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			
			return "redirect:/login";
		}
		//작성자 ID 설정 (Member의 ID를 사용)
		notice.setmId(loginMember.getmId());
		
		noticeService.update(notice);
		
		return "redirect:/notice/readOne?nNum=" + notice.getnNum();
	}
	
	//공지사항 삭제D
	@PostMapping("/delete")
	public String delete(@RequestParam("nNum") int nNum)	{
		
		noticeService.delete(nNum);
		
		return"redirect:/notice/list";
	}
}
