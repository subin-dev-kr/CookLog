package com.springmvc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.domain.CookingStep;
import com.springmvc.domain.Recipe;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {
	@Autowired
	private JdbcTemplate template;
	
	// 레시피 추가
	@Override
	@Transactional
	public void create(Recipe recipe) {
		
		String recipeSQL = "INSERT INTO recipe (m_id, m_nick_name, r_title, r_description, r_center_image, r_cook_type, "
				+ "r_cook_time, r_servings, r_level, r_ingredients) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		
		template.update(recipeSQL, recipe.getmId(), recipe.getmNickName(), recipe.getrTitle(), recipe.getrDescription(), recipe.getrCenterImagePath(),
				recipe.getrCookType(), recipe.getrCookTime(), recipe.getrServings(),recipe.getrLevel(), recipe.getrIngredients());
		Integer rNum = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class); //LAST_INSERT_ID() 가장 최근에 생성된 ID 반환
		
		if(rNum == null) {
			throw new RuntimeException("레시피 삽입 후 기본 키(r_num)를 가져올 수 없습니다.");
		}
		recipe.setrNum(rNum);
		
		String stepSQL = "INSERT INTO cooking_step (r_num, c_step, c_instructions, c_image) VALUES(?,?,?,?)";
		
		for(CookingStep step : recipe.getCookingSteps()) {
			
			template.update(stepSQL, rNum, step.getcStep(), step.getcInstructions(), step.getcImage());
		}
	}
	
	//레시피 리스트
	@Override
	public List<Recipe> recipeList() {
		String SQL = "SELECT r.*, m.m_nick_name FROM recipe r LEFT JOIN member m ON r.m_id = m.m_id";
		List<Recipe> recipeList = template.query(SQL, new RecipeRowMapper());
		
		List<CookingStep> listSteps = listCookingSteps();
		
		if(recipeList != null) {
			
			for(Recipe recipe : recipeList) {
				List<CookingStep> stepsByRecipe = new ArrayList<>();
				
				for(CookingStep step : listSteps) {
					if(step.getrNum().equals(recipe.getrNum())) {
						stepsByRecipe.add(step);
					}
				}
				recipe.setCookingSteps(stepsByRecipe);
			}
		}
		
		return recipeList;
	}

	private List<CookingStep> listCookingSteps(){
		
		String SQL = "SELECT * FROM cooking_step";
		
		return template.query(SQL, new CookingStepRowMapper());
	}
	//전체 레시피 카운트
	@Override
	public int countRecipes() {
		
		String SQL = "SELECT COUNT(*) FROM recipe";
        
		return template.queryForObject(SQL, Integer.class);		
	}
	//전체 레시피 페이징
	@Override
	public List<Recipe> findPage(int offset, int pageSize) {
		
		String SQL =
				  "SELECT r.*, COALESCE(l.cnt, 0) AS like_count " +
				  "FROM recipe r " +
				  "LEFT JOIN ( " +
				  "  SELECT r_num, COUNT(*) AS cnt " +
				  "  FROM recipe_like " +
				  "  GROUP BY r_num " +
				  ") l ON l.r_num = r.r_num " +
				  "ORDER BY r.r_num DESC " +
				  "LIMIT ? OFFSET ?";

		 return template.query(SQL, new RecipeRowMapper(), pageSize, offset);
	}
	//검색된 레시피 개수
	@Override
	public int countHomeSearch(String searchTab, String keyword) {
		
		String column = "";
		if("title".equals(searchTab)) {
			column = "r_title";
		} else if("nickName".equals(searchTab)) {
			column = "m_nick_name";
		}
		String SQL = "SELECT COUNT(*) FROM recipe WHERE " + column + " LIKE ? ORDER BY r_creation_date DESC";
		
		return template.queryForObject(SQL, Integer.class, "%" + keyword + "%");
	}
	//홈검색 + 페이징 조회
	@Override
	public List<Recipe> homeRecipeSearchPaging(String searchTab, String keyword, int offset, int pageSize) {
		
		String column = "";
		if("title".equals(searchTab)) {
			column = "r_title";
		} else if("nickName".equals(searchTab)) {
			column = "m_nick_name";
		}
		
		String SQL =
				  "SELECT r.*, COALESCE(l.cnt, 0) AS like_count " +
				  "FROM recipe r " +
				  "LEFT JOIN ( " +
				  "  SELECT r_num, COUNT(*) AS cnt " +
				  "  FROM recipe_like " +
				  "  GROUP BY r_num " +
				  ") l ON l.r_num = r.r_num " +
				  "WHERE r." + column + " LIKE ? " +
				  "ORDER BY r.r_creation_date DESC " +
				  "LIMIT ? OFFSET ?";
		
		return template.query(SQL, new RecipeRowMapper(), "%" + keyword + "%", pageSize, offset);
	}
	//마이 레시피 전체 개수
	@Override
	public int countMyRecipes(String mId) {
		
		String SQL = "SELECT COUNT(*) FROM recipe WHERE m_id = ?";
		
		return template.queryForObject(SQL, Integer.class, mId);
	}
	//마이 레시피 페이징 조회
	@Override
	public List<Recipe> findMyPage(String mId, int offset, int pageSize) {
		
		String SQL =
			    "SELECT r.*, COALESCE(l.cnt, 0) AS like_count " +
			    "FROM recipe r " +
			    "LEFT JOIN ( " +
			    "  SELECT r_num, COUNT(*) AS cnt " +
			    "  FROM recipe_like " +
			    "  GROUP BY r_num " +
			    ") l ON l.r_num = r.r_num " +
			    "WHERE r.m_id = ? " +
			    "ORDER BY r.r_creation_date DESC " +
			    "LIMIT ? OFFSET ?";

			return template.query(SQL, new RecipeRowMapper(), mId, pageSize, offset);
	}
	
	//검색된 마이 레시피 개수
	@Override
	public int countSearchMyRecipes(String mId, String keyword) {
		
		String SQL = "SELECT COUNT(*) FROM recipe WHERE m_id = ? AND r_title LIKE ? ORDER BY r_creation_date DESC";
		
		return template.queryForObject(SQL, Integer.class, mId, "%" + keyword + "%");
	}
	//마이 레시피 검색 + 페이징
	@Override
	public List<Recipe> findByMemberIdAndKeywordPaging(String mId, String keyword, int offset, int pageSize) {

	    String SQL =
	        "SELECT r.*, COALESCE(l.cnt, 0) AS like_count " +
	        "FROM recipe r " +
	        "LEFT JOIN ( " +
	        "  SELECT r_num, COUNT(*) AS cnt " +
	        "  FROM recipe_like " +
	        "  GROUP BY r_num " +
	        ") l ON l.r_num = r.r_num " +
	        "WHERE r.m_id = ? AND r.r_title LIKE ? " +
	        "ORDER BY r.r_creation_date DESC " +
	        "LIMIT ? OFFSET ?";

	    return template.query(SQL, new RecipeRowMapper(), mId, "%" + keyword + "%", pageSize, offset);
	}


	//레시피 상세조회
	@Override
	public Recipe readOne(Integer rNum) {
		
		String SQL = "SELECT r.*, m.m_nick_name FROM recipe r LEFT JOIN member m ON r.m_id = m.m_id WHERE r_num = ?";
		Recipe recipe = template.queryForObject(SQL, new RecipeRowMapper(), rNum);
		
		List<CookingStep> steps = readCookingSteps(rNum);
		
		if(recipe != null) { // 리스트를 Recipe객체에 설정
			recipe.setCookingSteps(steps);
		}
		return recipe;
	}

	//레시피 조회수
	@Override
	public void increaseViewCount(Integer rNum) {
		
		String SQL = "UPDATE recipe SET r_view_count = r_view_count + 1 WHERE r_num = ?";
		template.update(SQL, rNum);
	}
	private List<CookingStep> readCookingSteps(Integer rNum){
		
		String SQL = "SELECT * FROM cooking_step WHERE r_num = ? ORDER BY c_step";
		
		return template.query(SQL, new CookingStepRowMapper(), rNum);
	}
	//레시피 조회순 페이징
	@Override
	public List<Recipe> findPageOrderByView(int offset, int pageSize) {
		
		String SQL =
				  "SELECT r.*, COALESCE(l.cnt, 0) AS like_count " +
				  "FROM recipe r " +
				  "LEFT JOIN ( " +
				  "  SELECT r_num, COUNT(*) AS cnt " +
				  "  FROM recipe_like " +
				  "  GROUP BY r_num " +
				  ") l ON l.r_num = r.r_num " +
				  "ORDER BY r.r_view_count DESC, r.r_num DESC " +
				  "LIMIT ? OFFSET ?";


		    return template.query(SQL, new RecipeRowMapper(), pageSize, offset);
	}

	//새로운 요리순서 목록 추가
	@Override
	public void insertNewCookingSteps(Recipe recipe) {
	    List<CookingStep> steps = recipe.getCookingSteps();
	    if(steps != null) {
	        for(CookingStep step : steps) {
	            // INSERT 쿼리 실행
	            String SQL = "INSERT INTO cooking_step (r_num, c_step, c_instructions, c_image) VALUES (?, ?, ?, ?)";
	            
	            template.update(SQL, recipe.getrNum(), step.getcStep(), step.getcInstructions(), step.getcImage());
	        }
	    }
	}

	// 레시피 수정
	@Override
	public void updateRecipe(Recipe recipe) {
	    String SQL = "UPDATE recipe SET r_title = ?, r_description = ?, r_center_image = ?, r_cook_type = ?,"
	            + "r_cook_time = ?, r_servings = ?, r_level = ?, r_ingredients = ? WHERE r_num = ?";
	    template.update(SQL, recipe.getrTitle(), recipe.getrDescription(), recipe.getrCenterImagePath(),
	            recipe.getrCookType(), recipe.getrCookTime(), recipe.getrServings(), recipe.getrLevel(),
	            recipe.getrIngredients(), recipe.getrNum());
	}
	//레시피 삭제
	@Override
	public void deleteRecipe(Integer rNum) {
		
		String SQL = "DELETE FROM recipe WHERE r_num = ?";
		template.update(SQL, rNum);
	}
	// 레시피 요리순서 삭제
	@Override
	public void deleteCookingSteps(Integer rNum) {
		
		String SQL = "DELETE FROM cooking_step WHERE r_num = ?";
		template.update(SQL, rNum);
	}
	/* 레시피 좋아요 라인 */
	
	// 특정 레시피(rNum)에 대해 회원(mId)이 이미 좋아요를 눌렀는지 확인
	@Override
	public boolean exists(int rNum, String mId) {
		
		String SQL = "SELECT COUNT(*) FROM recipe_like WHERE r_num=? AND m_id=?";
        Integer cnt = template.queryForObject(SQL, Integer.class, rNum, mId);
        
        return cnt != null && cnt > 0;
	}
	// 좋아요 등록
	@Override
	public int insert(int rNum, String mId) {
		
		String SQL = "INSERT INTO recipe_like(r_num, m_id) VALUES(?, ?)";
		
        return template.update(SQL, rNum, mId);
	}
	// 좋아요 취소
	@Override
	public int delete(int rNum, String mId) {
		
		String SQL = "DELETE FROM recipe_like WHERE r_num=? AND m_id=?";
		
        return template.update(SQL, rNum, mId);
	}
	// 해당 레시피 좋아요 개수
	@Override
	public int countByRecipe(int rNum) {

		String SQL = "SELECT COUNT(*) FROM recipe_like WHERE r_num=?";
        Integer cnt = template.queryForObject(SQL, Integer.class, rNum);
        
        return (cnt == null) ? 0 : cnt;
	}
	// 좋아요 상태를 토글 (이미 좋아요면 취소, 없으면 등록)
	@Override
	public boolean toggle(int rNum, String mId) {

		if (exists(rNum, mId)) {
			
            delete(rNum, mId);
            
            return false; // 취소 상태
        } else {
        	
            insert(rNum, mId);
            
            return true; // 좋아요 상태
        }
    }
	
}
