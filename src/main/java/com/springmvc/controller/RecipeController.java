package com.springmvc.controller;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.domain.CookingStep;
import com.springmvc.domain.LikeResult;
import com.springmvc.domain.LikeToggleResponse;
import com.springmvc.domain.Member;
import com.springmvc.domain.Paging;
import com.springmvc.domain.Recipe;
import com.springmvc.service.RecipeService;

@RequestMapping("/recipe")
@Controller
public class RecipeController {
	@Autowired
	private RecipeService recipeService;

	// 레시피 등록
	@GetMapping("/create")
	public String create() {

		return "recipeForm";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Recipe recipe, HttpSession session, HttpServletRequest request) throws Exception {

		
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {

			return "redirect:/login";
		}
		recipe.setmId(loginMember.getmId()); //세션에서 멤버ID를 불러와서 레시피에 저장
		recipe.setmNickName(loginMember.getmNickName()); //세션에서 멤버 닉네임을 불러와서 레시피에 저장
		
		final String uploadDir = "/home/hosting_users/cd01349/uploadedImages/"; //이미지 저장할 경로
		System.out.println(uploadDir+":uploadDir");
		
		File dir = new File(uploadDir); 
		if(!dir.exists()) { //exists() = 존재하는지 확인 ,mkdirs() = 지정한 경로의 디렉토리를 생성하는 메서드 (지정한 경로에 폴더가 빠져있을 경우)
			dir.mkdirs();
		}

		MultipartFile centerImage = recipe.getrCenterImageFile();

		if (centerImage != null && !centerImage.isEmpty()) {
			// 파일명만 생성 (DB에는 파일명만 저장해야 브라우저에서 읽힘)
			String fileName = System.currentTimeMillis() + "_" + centerImage.getOriginalFilename();
			// 실제 파일 저장
			File saveFile = new File(uploadDir, fileName); // 최종 경로 객체
			centerImage.transferTo(saveFile); // 실제 이미지 파일 --> 최종 경로 위치로 이동
			// DB에는 파일명만 저장
			recipe.setrCenterImagePath(fileName);
			System.out.println(fileName + "fileName데이터 들어옴");
		}

		// 요리 순서 이미지 업로드 처리
		List<CookingStep> steps = recipe.getCookingSteps();
		
		if (steps != null) {
			for (int i = 0; i < steps.size(); i++) {
				MultipartFile stepFile = steps.get(i).getcImageFile();

				if (stepFile != null && !stepFile.isEmpty()) {

					String stepFileName = System.currentTimeMillis() + "_" + stepFile.getOriginalFilename();
					// 실제 파일 저장
					File saveFile = new File(uploadDir, stepFileName);
					stepFile.transferTo(saveFile);
					// DB에는 파일명만 저장
					steps.get(i).setcImage(stepFileName);
					System.out.println(stepFileName + "stepFileName , db에 데이터 들어옴");
				}
			}
		}

		recipeService.create(recipe);

		return "redirect:/member/myPage";
	}

	// 레시피 리스트(readAll)
	@GetMapping("/myList")
	public String recipeList(@RequestParam(defaultValue="1") int page, @RequestParam(value="keyword", required=false, defaultValue="") String searchId, HttpSession session, Model model) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login";
		}
		// searchId(keyword)가 있으면 그 아이디를 사용하고, 없으면 본인의 아이디(loginMember)를 사용합니다.
		String mId = (searchId != null && !searchId.isEmpty())? searchId : loginMember.getmId();
		
		// 관리자가 다른 회원의 레시피를 보러 온 경우를 위해 모델에 대상 mId를 다시 담아줍니다 (페이징 유지용)
	    model.addAttribute("targetId", mId);
		
		if (page < 1) {
			page = 1;
		}
		
		int pageSize = 15;

	    int totalCount = recipeService.countMyRecipes(mId);

	    Paging paging = new Paging(page, pageSize, totalCount);

	    List<Recipe> myRecipeList = recipeService.getMyRecipePage(mId, paging.getOffset(), paging.getPageSize());

	    model.addAttribute("recipeList", myRecipeList);
	    model.addAttribute("paging", paging);
		
		return "memberPage";
	}

	// home 레시피 상세조회(readOne)
	@GetMapping("/view") // 모든 사용자가 볼수있는 페이지
	public String recipeView(@RequestParam("rNum") Integer rNum, Model model) {

		recipeService.increaseViewCount(rNum);
		
		Recipe recipe = recipeService.readOne(rNum);
		model.addAttribute("recipe", recipe);	

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (recipe.getrCreationDate() != null) {
		    recipe.setrCreationDateStr(recipe.getrCreationDate().format(formatter));
		}
		if (recipe.getrUpdateDate() != null) {
		    recipe.setrUpdateDateStr(recipe.getrUpdateDate().format(formatter));
		}

		
		return "recipeViewPublic";

	}
	// memberPage 레시피 상세조회(readOne)
	@GetMapping("/myView") // 본인 레시피만 볼수있는 페이지
	public String myRecipeView(@RequestParam("rNum") Integer rNum, Model model) {

		recipeService.increaseViewCount(rNum);
		
		Recipe recipe = recipeService.readOne(rNum);
		model.addAttribute("recipe", recipe);		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (recipe.getrCreationDate() != null) {
		    recipe.setrCreationDateStr(recipe.getrCreationDate().format(formatter));
		}
		if (recipe.getrUpdateDate() != null) {
		    recipe.setrUpdateDateStr(recipe.getrUpdateDate().format(formatter));
		}

		
		return "recipeViewPrivate";

	}
	//레시피 검색(페이징 연결)
	@GetMapping("/readSome")
	public String readSome(@RequestParam(name="recipeSearchKeyword", required=false) String keyword, @RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		if (loginMember == null) {
		    return "redirect:/login";
		}
		model.addAttribute("member", loginMember); //닉네임 보이게 하기위해서 
		
		String mId = loginMember.getmId(); // 현재 로그인된 회원 아이디를 myId에 담음
		
		if(page < 1) {
			page = 1;
		}
		
		int pageSize = 15;
		
		int totalCount;
		List<Recipe> result;
		
		if(keyword != null && !keyword.trim().isEmpty()) {// 검색한 레시피만 조회
			totalCount = recipeService.countSearchMyRecipes(mId, keyword);
			Paging paging = new Paging(page, pageSize, totalCount);
			
			result = recipeService.searchMyRecipesPaging(mId, keyword, paging.getOffset(),paging.getPageSize());
			
			model.addAttribute("paging", paging);
		} else { //내용없이 검색을 누르면 마이 레시피 전체조회
			
			totalCount = recipeService.countMyRecipes(mId);
			Paging paging = new Paging(page, pageSize, totalCount);
			
			result = recipeService.getMyRecipePage(mId, paging.getOffset(), paging.getPageSize());

			model.addAttribute("paging", paging);
		}
		
		model.addAttribute("recipeList" ,result);
						
		return "memberPage";
		
	}
	// 레시피 수정
	@GetMapping("/updateForm")
	public String updateForm(@RequestParam("rNum") Integer rNum, HttpSession session, Model model) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login";
		}

		Recipe recipeInfo = recipeService.readOne(rNum);
		if (recipeInfo == null) {

			return "redirect:/recipe/myList";
		}

		model.addAttribute("recipe", recipeInfo);

		return "recipeUpdateForm";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Recipe recipe, HttpSession session)
			throws Exception {

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login";
		}
		// ⭐️ 1. 기존 Recipe 정보를 조회하여 기존 이미지 경로를 가져옵니다.
		// (이미지를 변경하지 않았을 경우 기존 경로를 유지해야 함)

		Recipe existingRecipe = recipeService.readOne(recipe.getrNum());

		// ⭐️ 2. 대표 이미지 처리
		MultipartFile centerImage = recipe.getrCenterImageFile();

		// 파일을 새로 업로드 했을 경우
		final String uploadDir = "/home/hosting_users/cd01349/uploadedImages/";
		
		File dir = new File(uploadDir); 
		if(!dir.exists()) {
		    dir.mkdirs();
		}

		if (centerImage != null && !centerImage.isEmpty()) {
			// [파일 업로드 로직] (create 메서드와 동일)
			String fileName = System.currentTimeMillis() + "_" + centerImage.getOriginalFilename();
			File saveFile = new File(uploadDir, fileName);
			centerImage.transferTo(saveFile);

			// DB에 새 파일명 저장
			recipe.setrCenterImagePath(fileName);
		} else {
			// 파일을 새로 업로드하지 않았을 경우, 기존 이미지 경로를 유지합니다.
			recipe.setrCenterImagePath(existingRecipe.getrCenterImagePath());
		}

		List<CookingStep> steps = recipe.getCookingSteps();

		if (steps != null) {
			for (int i = 0; i < steps.size(); i++) {
				MultipartFile stepFile = steps.get(i).getcImageFile();

				if (stepFile != null && !stepFile.isEmpty()) {

					String stepFileName = System.currentTimeMillis() + "_" + stepFile.getOriginalFilename();
					// 실제 파일 저장
					File saveFile = new File(uploadDir, stepFileName);
					stepFile.transferTo(saveFile);
					// DB에는 파일명만 저장
					steps.get(i).setcImage(stepFileName);
				}
			}
		}

		recipeService.updateRecipeAndSteps(recipe);

		return "redirect:/member/myPage";
	}

	// 레시피 삭제
	@PostMapping("/delete")
	public String delete(@RequestParam("rNum") Integer rNum, @RequestParam(value="targetId", required=false) String targetId,HttpSession session) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login";
		}
		
		if(targetId != null && !targetId.trim().isEmpty()) {
			
			if(loginMember.getmRole() != 1) {//관리자만 남의 레시피 삭제 가능!
				
				return "redirect:/member/myPage";
			}
		}

		recipeService.delete(rNum);
		
		// 관리자가 특정 회원 목록에서 삭제한 경우: 그 회원 목록으로 복귀
		if(targetId != null && !targetId.trim().isEmpty()) {
			
			return "redirect:/recipe/myList?page=1&keyword="+targetId.trim();
		}
		// 그외 마이 페이지로
		return "redirect:/member/myPage";
	}
	
	@PostMapping("/like/toggle")
	@ResponseBody
	public LikeToggleResponse toggleLike(@RequestParam("rNum") int rNum, HttpSession session) {

		 Member loginMember = (Member) session.getAttribute("loginMember");
	        if (loginMember == null) {
	            return new LikeToggleResponse(false, "NOT_LOGIN");
	        }

	        LikeResult result = recipeService.toggleLike(rNum, loginMember.getmId());

	        return new LikeToggleResponse(true, result.isLiked(), result.getLikeCount());
	    }

}
