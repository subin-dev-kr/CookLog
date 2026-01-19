package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Member;
@Repository
public class MemberRepositoryImpl implements MemberRepository {
	@Autowired
	private JdbcTemplate template;
	
	//회원가입
	@Override
	public void join(Member member) {
		
		String SQL ="INSERT INTO member(m_id, m_pw, m_nick_name, email, phone, m_role) VALUE(?,?,?,?,?,?)";
		template.update(SQL, member.getmId(), member.getmPw(), member.getmNickName(), 
				member.getEmail(), member.getPhone() , member.getmRole());
	}
	//아이디 중복체크
	@Override
	public boolean isIdExist(String mId) {
		
		String SQL = "SELECT COUNT(*) FROM member WHERE m_id = ?";
        Integer count = template.queryForObject(SQL, Integer.class, mId);
        
        return count != null && count > 0;
	}

	//닉네임 중복체크
	@Override
	public boolean isNickNameExist(String mNickName) {
		
		String SQL = "SELECT COUNT(*) FROM member WHERE m_nick_name = ?";
        Integer count = template.queryForObject(SQL, Integer.class, mNickName);
        
        return count != null && count > 0;
	}
	
	//이메일 중복체크
	@Override
	public boolean isEmailExist(String email) {
		
		String SQL = "SELECT COUNT(*) FROM member WHERE email = ?";
		Integer count = template.queryForObject(SQL, Integer.class, email);
		
		return count != null && count > 0;
	}
	//로그인
	@Override
	public Member login(String mId, String mPw) {
		
		String SQL = " SELECT * FROM member WHERE m_id = ? AND m_pw = ? AND m_status = 'ACTIVE'";
		try {
			return template.queryForObject(SQL, new MemberRowMapper(), mId, mPw );
			
		}catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	// 내 정보 조회
	@Override
	public Member myPage(String mId) {
		
		String SQL = "SELECT * FROM member WHERE m_id = ?";
		try {	
			return template.queryForObject(SQL, new MemberRowMapper(), mId);
			
		} catch(Exception e) {

			return null;
		}
	}
	//회원정보 수정
	@Override
	public void update(Member member) {
		
		String SQL = "UPDATE member SET m_pw = ?, m_nick_name = ?, email = ?, phone = ? WHERE m_id = ?";
		
		template.update(SQL, member.getmPw(), member.getmNickName(), member.getEmail(), member.getPhone(), member.getmId());
	}
	//회원탈퇴
	@Override
	public void delete(String mId) {
		
		String SQL = "UPDATE member SET m_status = 'DELETED' WHERE m_id = ?";
		
		template.update(SQL, mId);
	}
	// =================================여기서 부터 관리자 ==================================
	// 전체 회원수
	@Override
	public int countAll() {
	    String SQL = "SELECT COUNT(*) FROM member";
	    return template.queryForObject(SQL, Integer.class);
	}
	//전체 페이지
	@Override
	public List<Member> findPage(int offset, int limit) {
		
	    String SQL = "SELECT * FROM member ORDER BY m_id DESC LIMIT ? OFFSET ?";
	    
	    return template.query(SQL, new MemberRowMapper(), limit, offset);
	}
	//검색된 회원수 
	@Override
	public int countSearch(String type, String keyword) {

	    String column = "";
	    if ("mId".equals(type)) {
	        column = "m_id";
	    } else if ("mNickName".equals(type)) {
	        column = "m_nick_name";
	    } else if ("email".equals(type)) {
	        column = "email";
	    } else if ("phone".equals(type)) {
	        column = "phone";
	    }

	    String SQL = "SELECT COUNT(*) FROM member WHERE " + column + " LIKE ?";
	    
	    return template.queryForObject(SQL, Integer.class, "%" + keyword + "%");
	}
	//검색된 페이징 
	@Override
	public List<Member> searchPage(String type, String keyword, int offset, int limit) {

	    String column = "";
	    if ("mId".equals(type)) {
	        column = "m_id";
	    } else if ("mNickName".equals(type)) {
	        column = "m_nick_name";
	    } else if ("email".equals(type)) {
	        column = "email";
	    } else if ("phone".equals(type)) {
	        column = "phone";
	    }

	    String SQL = "SELECT * FROM member WHERE " + column + " LIKE ? ORDER BY m_id DESC LIMIT ? OFFSET ?";

	    return template.query(SQL, new MemberRowMapper(), "%" + keyword + "%", limit, offset);
	}
	@Override
	public int findRoleById(String mId) {
		
		String SQL = "SELECT m_role FROM member WHERE m_id = ?";
		try {
			Integer role = template.queryForObject(SQL, Integer.class, mId);
			return role == null ? 0 : role;
		} catch (EmptyResultDataAccessException e) {
			return -1; //없는 아이디
		}
	}

}
