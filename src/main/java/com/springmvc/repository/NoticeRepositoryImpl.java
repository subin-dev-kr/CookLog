package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Notice;

@Repository
public class NoticeRepositoryImpl implements NoticeRepository {
	@Autowired
	private JdbcTemplate template;
	//게시글 추가
	@Override
	public int create(Notice notice) {
		
		String SQL = "INSERT INTO notice(m_id, m_nick_name, n_title, n_content) VALUE(?,?,?,?)";
		return template.update(SQL, notice.getmId(), notice.getmNickName(), notice.getnTitle(), notice.getnContent());
	}
	//공지사항 리스트
	@Override
	public List<Notice> list() {
		
		String SQL = "SELECT * FROM notice ORDER BY n_num DESC";
		
		return template.query(SQL, new NoticeRowMapper());
	}
	//공지사항 롤링
	@Override
	public List<Notice> findTop5() {
		
		String SQL = "SELECT * FROM notice ORDER BY n_creation_date DESC LIMIT 5";
		return template.query(SQL, new NoticeRowMapper());
	}
	//게시글 조회수
	@Override
	public void increaseViewCount(int nNum) {
		
		String SQL = "UPDATE notice SET n_view_count = n_view_count + 1 WHERE n_num = ?";
		template.update(SQL, nNum);
	}
	//게시글 상세조회
	@Override
	public Notice readOne(Integer nNum) {
		
		String SQL = "SELECT * FROM notice WHERE n_num = ?";
		
        try {
            
        	return template.queryForObject(SQL, new NoticeRowMapper(), nNum);  
        } catch (Exception e) {
            return null;
        }
	}
	// 공지사항 전체 개수
	@Override
	public int countAll() {
	    String SQL = "SELECT COUNT(*) FROM notice";
	    return template.queryForObject(SQL, Integer.class);
	}
	//전체 페이징
	@Override
	public List<Notice> findPage(int offset, int limit) {
	    String SQL = "SELECT * FROM notice ORDER BY n_num DESC LIMIT ? OFFSET ?";
	    return template.query(SQL, new NoticeRowMapper(), limit, offset);
	}
	//검색된 공지사항 개수
	@Override
	public int countSearch(String type, String keyword) {

	    String column = "";
	    if ("nTitle".equals(type)) {
	        column = "n_title";
	    } else if ("nContent".equals(type)) {
	        column = "n_content";
	    } else if ("mNickName".equals(type)) {
	        column = "m_nick_name";
	    }

	    String SQL = "SELECT COUNT(*) FROM notice WHERE " + column + " LIKE ?";
	    return template.queryForObject(SQL, Integer.class, "%" + keyword + "%");
	}
	//검색된 페이징
	@Override
	public List<Notice> searchPage(String type, String keyword, int offset, int limit) {

	    String column = "";
	    if ("nTitle".equals(type)) {
	        column = "n_title";
	    } else if ("nContent".equals(type)) {
	        column = "n_content";
	    } else if ("mNickName".equals(type)) {
	        column = "m_nick_name";
	    }

	    String SQL =
	        "SELECT * FROM notice WHERE " + column +
	        " LIKE ? ORDER BY n_num DESC LIMIT ? OFFSET ?";

	    return template.query(SQL, new NoticeRowMapper(), "%" + keyword + "%", limit, offset);
	}
	
	//게시글 수정
	@Override
	public void update(Notice notice) {
		
		String SQL = "UPDATE notice SET n_title = ?, n_content = ?, m_id = ?, n_update_date = NOW() WHERE n_num = ?";
		template.update(SQL, notice.getnTitle(), notice.getnContent(), notice.getmId(), notice.getnNum());
	}
	//게시글 삭제
	@Override
	public void delete(int nNum) {
		
		String SQL = "DELETE FROM notice WHERE n_num = ?";
		template.update(SQL, nNum);
	}

	
}
