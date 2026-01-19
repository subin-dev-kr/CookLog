package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.Notice;

public interface NoticeRepository {
	
	//게시글 추가
	int create(Notice notice);    
	//공지사항 리스트
	List<Notice> list();    
	//공지사항 롤링
	List<Notice> findTop5();                						            
	// 전체 공지사항 개수
    int countAll();
    // 전체 페이징
    List<Notice> findPage(int offset, int limit);
    // 검색한 공지사항 개수
    int countSearch(String type, String keyword);
    // 검색한 공지사항 페이징
    List<Notice> searchPage(String type, String keyword, int offset, int limit);
    //조회수 증가
	void increaseViewCount(int nNum);  											
	//게시글 상세보기
	Notice readOne(Integer nNum);      											
	//게시글수정
	void update(Notice notice);        											
	//게시글 삭제 
	void delete(int nNum);             											
	
}
