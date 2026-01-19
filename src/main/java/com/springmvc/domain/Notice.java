package com.springmvc.domain;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Notice {

	private Integer nNum;                //공지사항 고유번호
	private String mId;                  //관리자 아이디
	private String mNickName;            //관리자 닉네임 
	private String nTitle;               //제목    
	private String nContent;             //내용
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime nCreationDate; //작성일
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime nUpdateDate;   //수정일
	private Integer nViewCount;          //조회수 
	
	//매개변수 생성자
	public Notice(Integer nNum, String mId, String mNickName, String nTitle, String nContent,
			LocalDateTime nCreationDate, LocalDateTime nUpdateDate, Integer nViewCount) {
		super();
		this.nNum = nNum;
		this.mId = mId;
		this.mNickName = mNickName;
		this.nTitle = nTitle;
		this.nContent = nContent;
		this.nCreationDate = nCreationDate;
		this.nUpdateDate = nUpdateDate;
		this.nViewCount = nViewCount;
	}
	
	//기본생성자
	public Notice() {
		super();
	}
	//getter & setter
	public Integer getnNum() {
		return nNum;
	}
	public void setnNum(Integer nNum) {
		this.nNum = nNum;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmNickName() {
		return mNickName;
	}

	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}

	public String getnTitle() {
		return nTitle;
	}
	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	public String getnContent() {
		return nContent;
	}
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	public LocalDateTime getnCreationDate() {
		return nCreationDate;
	}
	public void setnCreationDate(LocalDateTime nCreationDate) {
		this.nCreationDate = nCreationDate;
	}
	public LocalDateTime getnUpdateDate() {
		return nUpdateDate;
	}
	public void setnUpdateDate(LocalDateTime nUpdateDate) {
		this.nUpdateDate = nUpdateDate;
	}
	public Integer getnViewCount() {
		return nViewCount;
	}
	public void setnViewCount(Integer nViewCount) {
		this.nViewCount = nViewCount;
	}
	
	//toString
	@Override
	public String toString() {
		return "Notice [nNum=" + nNum + ", mId=" + mId + ", mNickName=" + mNickName + ", nTitle=" + nTitle
				+ ", nContent=" + nContent + ", nCreationDate=" + nCreationDate + ", nUpdateDate=" + nUpdateDate
				+ ", nViewCount=" + nViewCount + "]";
	}
	
}
