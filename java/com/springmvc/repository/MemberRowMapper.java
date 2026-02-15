package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.Member;

public class MemberRowMapper implements RowMapper<Member> {
	
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		Member member = new Member();
		
		member.setmNum(rs.getInt("m_num"));
		member.setmId(rs.getString("m_id"));
	    member.setmPw(rs.getString("m_pw"));
	    member.setmNickName(rs.getString("m_nick_name"));
	    member.setEmail(rs.getString("email"));
	    member.setPhone(rs.getString("phone"));
		member.setmRole(rs.getInt("m_role"));
		member.setmStatus(rs.getString("m_status"));
		
		return member;
	}
	
}
