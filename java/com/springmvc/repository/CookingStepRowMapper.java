package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.CookingStep;

public class CookingStepRowMapper implements RowMapper<CookingStep> {

	@Override
	public CookingStep mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CookingStep cookingStep = new CookingStep();
		
		cookingStep.setcNum(rs.getInt("c_num"));
		cookingStep.setrNum(rs.getInt("r_num"));
		cookingStep.setcStep(rs.getInt("c_step"));
		cookingStep.setcInstructions(rs.getString("c_instructions"));
		cookingStep.setcImage(rs.getString("c_image"));
		
		return cookingStep;
	}

	
}
