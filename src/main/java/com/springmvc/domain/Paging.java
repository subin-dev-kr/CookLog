package com.springmvc.domain;

public class Paging {
	
	private int currentPage;    // 현재 페이지
    private int pageSize;       // 한 페이지 글 수 (15)
    private int totalCount;     // 전체 글 개수
    private int totalPages;     // 전체 페이지 수
    private int startPage;      // 보여줄 시작 페이지
    private int endPage;        // 보여줄 끝 페이지
    private int offset;         // LIMIT 시작점
    private int pageBlock = 10; // 페이지 번호 몇 개씩 보여줄지(1~10)
    
    //매개변수 생성자
	public Paging(int currentPage, int pageSize, int totalCount) {
		
		if (currentPage < 1) {
		    currentPage = 1;
		}
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		
		// 전체 페이지 수
		this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
		if (this.totalPages == 0) {
	        this.totalPages = 1;
	    }
		
		// 페이지 번호 블록 계산
		int blockIndex = (currentPage - 1) / pageBlock;
		this.startPage = blockIndex * pageBlock + 1;
		this.endPage = startPage + pageBlock - 1;
		
		if(endPage > totalPages ) {
			endPage = totalPages;
		}
		
		// LIMIT offset 계산
		this.offset = (currentPage - 1) * pageSize;
		
	}
	//기본 생성자
	public Paging() {
		super();
	}
	
	//getter & setter
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
    
}
