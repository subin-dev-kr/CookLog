package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Notice;
import com.springmvc.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeRepository noticeRepository;
	
	// 게시글 추가
	@Override
	public Notice create(Notice notice) {
		
		int result = noticeRepository.create(notice);
		
		if(result > 0) { //저장 성공시 (1개 이상 행이 삽입 되었을 경우)
			
			return notice;
		}else {
			
			return null;  
		}
	}
	// 공지사항 리스트
	@Override
	public List<Notice> list() {
		
		return noticeRepository.list();
	}
	//공지사항 최신순 5개
	@Override
	public List<Notice> findTop5() {
		
		return noticeRepository.findTop5();
	}
	//전체 공지사항 개수	
	@Override
	public int countAll() {
	    return noticeRepository.countAll();
	}
	//전체 페이지
	@Override
	public List<Notice> findPage(int offset, int limit) {
	    return noticeRepository.findPage(offset, limit);
	}
	//검색한 공지사항 개수
	@Override
	public int countSearch(String type, String keyword) {
	    return noticeRepository.countSearch(type, keyword);
	}
	//검색한 페이지
	@Override
	public List<Notice> searchPage(String type, String keyword, int offset, int limit) {
	    return noticeRepository.searchPage(type, keyword, offset, limit);
	}

	//공지사항 조회수
	@Override
	public void increaseViewCount(int nNum) {
		
		noticeRepository.increaseViewCount(nNum);
	}
	//게시글 상세조회
	@Override
	public Notice readOne(Integer nNum) {
		
		return noticeRepository.readOne(nNum);
	}
	// 게시글 수정
	@Override
	public void update(Notice notice) {
		
		noticeRepository.update(notice);
	}
	//게시글 삭제
	@Override
	public void delete(int nNum) {
		
		noticeRepository.delete(nNum);
	}
	
	
	
}
