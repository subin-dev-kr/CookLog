package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Member;
import com.springmvc.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;
	// 회원가입
	@Override
	public void join(Member member) {
		
		if(isNickNameExist(member.getmNickName())) {
			throw new IllegalArgumentException("이미 사용중인 닉네임 입니다.");
		}
		
		if(isEmailExist(member.getEmail())) {
			throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
		}
		
		memberRepository.join(member);
		
	}
	//아이디 중복체크
	@Override
	public boolean isIdExist(String mId) {
		
		return  memberRepository.isIdExist(mId);
	}
	//닉네임 중복체크
	@Override
	public boolean isNickNameExist(String mNickName) {
		
		return memberRepository.isNickNameExist(mNickName);
	}
	//이메일 중복체크
	@Override
	public boolean isEmailExist(String email) {
		
		return memberRepository.isEmailExist(email);
	}
	// 로그인
	@Override
	public Member login(String mId, String mPw) {
		
		return memberRepository.login(mId, mPw);
	}
	// 내정보 조회
	@Override
	public Member myPage(String mId) {
		
		return memberRepository.myPage(mId);
	}
	//정보수정
	@Override
	public void update(Member member) {
		
		String mId = member.getmId();
		
		if(memberRepository.isNickNameExcptMe(member.getmNickName(), mId)) {
			throw new IllegalArgumentException("이미 사용중인 닉네임 입니다.");
		}
		
		if(memberRepository.isEmailExceptMe(member.getEmail(), mId)) {
			throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
		}
		
		// 비밀번호가 비어있으면 null로 만들어서 "업데이트 제외"
	    if (member.getmPw() != null && member.getmPw().trim().isEmpty()) {
	        member.setmPw(null);
	    }
		
		memberRepository.update(member);
	}
	// 수정용 닉네임 중복 체크(나 제외)
	@Override
	public boolean isNickNameExcptMe(String mNickName, String mId) {
		
		return memberRepository.isNickNameExcptMe(mNickName, mId);
	}
	
	// 수정용 이메일 중복 체크(나 제외)
	@Override
	public boolean isEmailExceptMe(String email, String mId) {
		
		return memberRepository.isEmailExceptMe(email, mId);
	}

	//회원탈퇴
	@Override
	public void delete(String mId) {
		
		int role = memberRepository.findRoleById(mId);
		
		if(role == 1) {
			
			throw new IllegalStateException("관리자 계정은 탈퇴할 수 없습니다.");
		}

		memberRepository.delete(mId);
	}
	
	/*========== 여기서 부터 관리자 ===========*/
	@Override
	public int countAll() {
	    return memberRepository.countAll();
	}

	@Override
	public List<Member> findPage(int offset, int limit) {
	    return memberRepository.findPage(offset, limit);
	}

	@Override
	public int countSearch(String type, String keyword) {
	    return memberRepository.countSearch(type, keyword);
	}

	@Override
	public List<Member> searchPage(String type, String keyword, int offset, int limit) {
	    return memberRepository.searchPage(type, keyword, offset, limit);
	}

	
}
