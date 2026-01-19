package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.domain.CookingStep;
import com.springmvc.domain.LikeResult;
import com.springmvc.domain.Recipe;
import com.springmvc.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	private RecipeRepository recipeRepository;
	//레시피 추가
	@Override
	@Transactional
	public void create(Recipe recipe) {
		
		if(recipe.getCookingSteps() != null) {  //요리순서가 null이 아니면
			
			int stepCount = 1; 
			for(CookingStep step : recipe.getCookingSteps()) {
				if(step.getcInstructions() != null && !step.getcInstructions().isEmpty()) { //요리순서 설명이 null이 아니면
					
					step.setcStep(stepCount++); 
				}
			}
			recipeRepository.create(recipe);
		}
	}
	//레시피 리스트
	@Override
	public List<Recipe> recipeList() {
		
		return recipeRepository.recipeList();
	}
	//전체 레시피 페이징
	@Override
	public List<Recipe> getRecipePage(int offset, int pageSize) {
	
		return recipeRepository.findPage(offset, pageSize);
	}
	// 전체 레시피 카운트
	@Override
	public int countRecipes() {
		
		return recipeRepository.countRecipes();
	}
	//마이 레시피 전체 개수
	@Override
	public int countMyRecipes(String mId) {
		
		return recipeRepository.countMyRecipes(mId);
	}
	//마이 레시피 페이징 조회
	@Override
	public List<Recipe> getMyRecipePage(String mId, int offset, int pageSize) {
		
		return recipeRepository.findMyPage( mId, offset, pageSize);
	}
	//검색된 마이 레시피 개수
	@Override
	public int countSearchMyRecipes(String mId, String keyword) {
		
		return recipeRepository.countSearchMyRecipes(mId ,keyword);
	}
	//마이 레시피 검색 + 페이징
	@Override
	public List<Recipe> searchMyRecipesPaging(String mId, String keyword, int offset, int pageSize) {
		
		return recipeRepository.findByMemberIdAndKeywordPaging(mId, keyword, offset, pageSize);
	}
	//레시피 상세조회
	@Override
	public Recipe readOne(Integer rNum) {
		
		return recipeRepository.readOne(rNum);
	}
	//모든회원 레시피 검색
	@Override
	public int countHomeSearch(String searchTab, String keyword) {
		
		return recipeRepository.countHomeSearch(searchTab, keyword);
	}
	//검색 + 페이징 조회
	@Override
	public List<Recipe> homeRecipeSearchPaging(String searchTab, String keyword, int offset, int pageSize) {
		
		 return recipeRepository.homeRecipeSearchPaging(searchTab, keyword, offset, pageSize);
	}
	//레시피 조회수
	@Override
	public void increaseViewCount(Integer rNum) {
		
		recipeRepository.increaseViewCount(rNum);
	}
	// 레시피 조회순 페이징
	@Override
	public List<Recipe> getRecipePageByView(int offset, int pageSize) {
		
		return recipeRepository.findPageOrderByView(offset, pageSize);
	}
	//레시피 수정
	@Override
	public void updateRecipeAndSteps(Recipe recipe) {
	    
	    // 1. 레시피 기본 정보 업데이트
	    recipeRepository.updateRecipe(recipe);
	    
	    // 2. 기존의 모든 요리 순서 삭제 (DELETE ALL)
	    recipeRepository.deleteCookingSteps(recipe.getrNum());
	    
	    List<CookingStep> submittedSteps = recipe.getCookingSteps(); // 폼에서 넘어온 전체 단계
	    
	    // ⭐️⭐️ [수정 핵심] 유효한 단계만 저장할 새로운 리스트 생성 ⭐️⭐️
	    List<CookingStep> validSteps = new ArrayList<>();
	    
	    if (submittedSteps != null && !submittedSteps.isEmpty()) {
	        
	        for (CookingStep step : submittedSteps) {
	            
	            // ⭐️ 유효성 검사: cInstructions가 null이거나 공백만 있는 단계는 삽입 대상에서 제외
	            if (step.getcInstructions() != null && !step.getcInstructions().trim().isEmpty()) {
	                validSteps.add(step);
	            }
	        }
	        
	        // 유효한 단계가 있을 경우에만 재정렬 및 삽입 (INSERT ALL)
	        if (!validSteps.isEmpty()) {
	            for (int i = 0; i < validSteps.size(); i++) {
	                CookingStep step = validSteps.get(i);
	                
	                // 순서 재부여: 리스트의 인덱스를 사용하여 cStep을 1부터 순차적으로 설정
	                step.setcStep(i + 1); 
	                
	                // cNum 초기화: 새로 삽입할 단계이므로 cNum을 null로 설정
	                step.setcNum(null); 
	                
	                // RNum 설정: INSERT 쿼리를 위해 레시피 번호를 설정
	                step.setrNum(recipe.getrNum());
	            }
	            
	            // 4. ⭐️ Recipe 객체의 List를 유효한 리스트로 교체 후 DB에 일괄 삽입 ⭐️
	            recipe.setCookingSteps(validSteps); 
	            recipeRepository.insertNewCookingSteps(recipe); 
	        } else {
	            // 유효한 단계가 하나도 없을 경우 (모두 삭제되거나 내용이 비어있는 경우) 아무것도 삽입하지 않음.
	        }
	    }
	}
	//레시피 삭제
	@Override
	public void delete(Integer rNum) {
		
		recipeRepository.deleteCookingSteps(rNum);
		recipeRepository.deleteRecipe(rNum);
	}
	//레시피 좋아요 상태
	@Override
	@Transactional
	public LikeResult toggleLike(int rNum, String mId) {
		
		boolean liked = recipeRepository.toggle(rNum, mId);
        int likeCount = recipeRepository.countByRecipe(rNum);
        return new LikeResult(liked, likeCount);
	}
	
	
}
