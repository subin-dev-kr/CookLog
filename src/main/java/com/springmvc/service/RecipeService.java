package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.LikeResult;
import com.springmvc.domain.Recipe;

public interface RecipeService {
	//레시피 등록
	void create(Recipe recipe);
	//전체 레시피 조회
	List<Recipe> recipeList();
	//전체레시피 페이징 
	List<Recipe> getRecipePage(int offset, int pageSize);
	//전체 레시피 개수
	int countRecipes();
	//검색된 레시피 개수
	int countHomeSearch(String searchTab, String keyword);
	//홈 검색 + 페이징 조회
	List<Recipe> homeRecipeSearchPaging(String searchTab, String keyword, int offset, int pageSize);
	//마이 레시피 전체 개수
	int countMyRecipes(String mId);
	//마이 레시피 페이징 조회
	List<Recipe> getMyRecipePage(String mId, int offset, int pageSize);
	//검색된 마이 레시피 개수
	int countSearchMyRecipes(String mId, String keyword);
	//마이 레시피 검색 + 페이징
	List<Recipe> searchMyRecipesPaging(String mId, String keyword, int offset, int pageSize);
	//레시피 상세조회
	Recipe readOne(Integer rNum);
	//레시피조회수
	void increaseViewCount(Integer rNum);
	// 조회순 페이징
	List<Recipe> getRecipePageByView(int offset, int pageSize);
	//레시피 수정
	void updateRecipeAndSteps(Recipe recipe);
	//레시피 삭제
	void delete(Integer rNum);
	//레시피 좋아요 상태
	LikeResult toggleLike(int rNum, String mId);
}
