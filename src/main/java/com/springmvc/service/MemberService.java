package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.Member;

public interface MemberService {

	void join(Member member);             				//회원가입
	boolean isIdExist(String mId);                      //아이디 중복체크
	boolean isNickNameExist(String mNickName);          //닉네임 중복체크
	boolean isEmailExist(String email);                 //이메일 중복체크
	Member login(String mId, String mPw);       		//로그인
	Member myPage(String mId);           				//회원페이지
	void update(Member member);           			    //회원수정
	void delete(String mId);           				    //회원탈퇴
	/***** 여기서 부터 관리자 *****/
	//전체 회원수
	int countAll();
	//전체회원 페이지
	List<Member> findPage(int offset, int limit);
	//검색된 회원
	int countSearch(String type, String keyword);
	//검색된 회원 페이지
	List<Member> searchPage(String type, String keyword, int offset, int limit);
}
