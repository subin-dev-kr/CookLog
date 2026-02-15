package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.Notice;

public class NoticeRowMapper implements RowMapper<Notice> {

	public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Notice notice = new Notice();
		
		notice.setnNum(rs.getInt("n_num"));      
		notice.setmId(rs.getString("m_id")); 
		notice.setmNickName(rs.getString("m_nick_name"));
		notice.setnTitle(rs.getString("n_title"));
		notice.setnContent(rs.getString("n_content"));
		notice.setnCreationDate(rs.getObject("n_creation_date",LocalDateTime.class));
		notice.setnUpdateDate(rs.getObject("n_update_date",LocalDateTime.class));
		notice.setnViewCount(rs.getInt("n_view_count"));
		
		return notice;
	}
}
