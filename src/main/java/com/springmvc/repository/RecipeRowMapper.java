package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.Recipe;

public class RecipeRowMapper implements RowMapper<Recipe> {

	@Override
	public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Recipe recipe = new Recipe();
		
		recipe.setrNum(rs.getInt("r_num"));
		recipe.setmId(rs.getString("m_id"));
		recipe.setmNickName(rs.getString("m_nick_name"));
		recipe.setrTitle(rs.getString("r_title"));
		recipe.setrDescription(rs.getString("r_description"));
		recipe.setrCenterImagePath(rs.getString("r_center_image"));
		recipe.setrCookType(rs.getString("r_cook_type"));
		recipe.setrCookTime(rs.getString("r_cook_time"));
		recipe.setrServings(rs.getString("r_servings"));
		recipe.setrLevel(rs.getString("r_level"));
		recipe.setrIngredients(rs.getString("r_ingredients"));
		recipe.setrCreationDate(rs.getObject("r_creation_date", LocalDateTime.class));
		recipe.setrUpdateDate(rs.getObject("r_update_date", LocalDateTime.class));
		recipe.setrViewCount(rs.getInt("r_view_count"));
		
		try {
		    recipe.setLikeCount(rs.getInt("like_count"));
		} catch (SQLException e) {
		    recipe.setLikeCount(0);
		}

		
		return recipe;
	}
	
	

}
