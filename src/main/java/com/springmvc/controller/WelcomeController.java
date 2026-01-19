package com.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Notice;
import com.springmvc.domain.Paging;
import com.springmvc.domain.Recipe;
import com.springmvc.service.NoticeService;
import com.springmvc.service.RecipeService;


@Controller
public class WelcomeController {
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/")
	public String recipeList(@RequestParam(value = "page", defaultValue = "1") int page, 
			 @RequestParam(value = "sort", defaultValue = "recent") String sort, Model model) {		
		
		int pageSize = 15;

	    // 전체 개수 조회
	    int totalCount = recipeService.countRecipes();
	    
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    if (page < 1) page = 1;
	    if (page > totalPages) page = totalPages;

	    // 페이징 DTO 생성
	    Paging paging = new Paging(page, pageSize, totalCount);

	    List<Recipe> recipeList;

	    if ("view".equals(sort)) {
	        recipeList = recipeService.getRecipePageByView(
	                paging.getOffset(), paging.getPageSize());
	    } else {
	        recipeList = recipeService.getRecipePage(
	                paging.getOffset(), paging.getPageSize());
	    }
	    // JSP로 전달
	    model.addAttribute("recipeList", recipeList);
	    model.addAttribute("paging", paging);
	    model.addAttribute("sort", sort);
		
		// 공지사항 롤링 home.jsp에 뿌림
		List<Notice> top5NoticeList = noticeService.findTop5();
		model.addAttribute("top5List", top5NoticeList);
		
		return "home";
	}
	//모든 회원 레시피 검색
	@GetMapping("/homeRecipeSearch")
	public String homeRecipeSearch(@RequestParam("searchTab") String searchTab,@RequestParam("searchKeyword") String keyword, @RequestParam(defaultValue = "1") int page, Model model) {
	    int pageSize = 15;

	    if (page < 1) page = 1;

	    int totalCount;
	    List<Recipe> recipeList;

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        // 검색 개수
	        totalCount = recipeService.countHomeSearch(searchTab, keyword);

	        Paging paging = new Paging(page, pageSize, totalCount);

	        // 검색 + 페이징
	        recipeList = recipeService.homeRecipeSearchPaging(searchTab, keyword, paging.getOffset(), paging.getPageSize());

	        model.addAttribute("paging", paging);
	    } else {
	        // 검색어 없으면 홈으로
	        return "redirect:/";
	    }

	    model.addAttribute("recipeList", recipeList);
	    model.addAttribute("searchTab", searchTab);
	    model.addAttribute("searchKeyword", keyword);

	    return "home";
	}

	
}
