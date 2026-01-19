package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.Recipe;

public interface RecipeRepository {
	//레시피 등록
	void create(Recipe recipe);
	//레시피 조회
	List<Recipe> recipeList();
	//전체 레시피 카운트
	int countRecipes();
	//전체 레시피 페이징
	List<Recipe> findPage(int offset, int pageSize);
	//검색된 레시피 개수
	int countHomeSearch(String searchTab, String keyword);
	//홈 검색 + 페이징 조회
	List<Recipe> homeRecipeSearchPaging(String searchTab, String keyword, int offset, int pageSize);

	//마이 레시피 카운트
	int countMyRecipes(String mId);
	//마이 레시피 페이징 조회
	List<Recipe> findMyPage(String mId, int offset, int pageSize);
	//검색된 마이 레시피 개수
	int countSearchMyRecipes(String mId, String keyword);
	//마이 레시피 검색 + 페이징
    List<Recipe> findByMemberIdAndKeywordPaging(String mId, String keyword, int offset, int pageSize);
	//레시피 상세조회
	Recipe readOne(Integer rNum);
	//레시피 조회수
	void increaseViewCount(Integer rNum);
	// 조회순 레시피 페이징
	List<Recipe> findPageOrderByView(int offset, int pageSize);
	//레시피 수정
	void updateRecipe(Recipe recipe);
	//새로운 요리순서 추가	
	void insertNewCookingSteps(Recipe recipe);
	//요리순서 삭제
	void deleteCookingSteps(Integer rNum);
	//레시피 삭제
	void deleteRecipe(Integer rNum);
	/* 레시피 좋아요 상태 */
	boolean exists(int rNum, String mId);
    int insert(int rNum, String mId);
    int delete(int rNum, String mId);
    int countByRecipe(int rNum);
    boolean toggle(int rNum, String mId);
    
}
