package com.springmvc.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class Recipe {

	private Integer rNum;                	//레시피 고유번호
	private String mId;                  	//회원 아이디
	private String mNickName;               //회원 닉네임
	private String rTitle;               	//레시피 제목
	private String rDescription;            //레시피 소개글
	private MultipartFile rCenterImageFile; //대표 이미지 파일
	private String rCenterImagePath;        //DB 저장용 경로 
	private String rCookType;            	//요리 종류
	private String rCookTime;            	//요리 시간
	private String rServings;            	//인분 
	private String rLevel;               	//난이도
	private String rIngredients;          	//재료  
	private String rCreationDateStr;        //작성일 문자열
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime rCreationDate; 	//작성일
	private String rUpdateDateStr;          //수정일 문자열
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime rUpdateDate;   	//수정일
	private Integer rViewCount;          	//조회수 
	private int likeCount; // 좋아요 개수
	private List<CookingStep> cookingSteps; //요리순서 리스트
	
	
	//기본 생성자
	public Recipe() {
		super();
	}
	
	//매개변수 생성자
	public Recipe(Integer rNum, String mId, String mNickName, String rTitle, String rDescription,
			MultipartFile rCenterImageFile, String rCenterImagePath, String rCookType, String rCookTime,
			String rServings, String rLevel, String rIngredients, String rCreationDateStr, LocalDateTime rCreationDate,
			String rUpdateDateStr, LocalDateTime rUpdateDate, Integer rViewCount, int likeCount,
			List<CookingStep> cookingSteps) {
		super();
		this.rNum = rNum;
		this.mId = mId;
		this.mNickName = mNickName;
		this.rTitle = rTitle;
		this.rDescription = rDescription;
		this.rCenterImageFile = rCenterImageFile;
		this.rCenterImagePath = rCenterImagePath;
		this.rCookType = rCookType;
		this.rCookTime = rCookTime;
		this.rServings = rServings;
		this.rLevel = rLevel;
		this.rIngredients = rIngredients;
		this.rCreationDateStr = rCreationDateStr;
		this.rCreationDate = rCreationDate;
		this.rUpdateDateStr = rUpdateDateStr;
		this.rUpdateDate = rUpdateDate;
		this.rViewCount = rViewCount;
		this.likeCount = likeCount;
		this.cookingSteps = cookingSteps;
	}
	
	//getter & setter
	public Integer getrNum() {
		return rNum;
	}

	public void setrNum(Integer rNum) {
		this.rNum = rNum;
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

	public String getrTitle() {
		return rTitle;
	}

	public void setrTitle(String rTitle) {
		this.rTitle = rTitle;
	}

	public String getrDescription() {
		return rDescription;
	}

	public void setrDescription(String rDescription) {
		this.rDescription = rDescription;
	}

	public MultipartFile getrCenterImageFile() {
		return rCenterImageFile;
	}

	public void setrCenterImageFile(MultipartFile rCenterImageFile) {
		this.rCenterImageFile = rCenterImageFile;
	}

	public String getrCenterImagePath() {
		return rCenterImagePath;
	}

	public void setrCenterImagePath(String rCenterImagePath) {
		this.rCenterImagePath = rCenterImagePath;
	}

	public String getrCookType() {
		return rCookType;
	}

	public void setrCookType(String rCookType) {
		this.rCookType = rCookType;
	}

	public String getrCookTime() {
		return rCookTime;
	}

	public void setrCookTime(String rCookTime) {
		this.rCookTime = rCookTime;
	}

	public String getrServings() {
		return rServings;
	}

	public void setrServings(String rServings) {
		this.rServings = rServings;
	}

	public String getrLevel() {
		return rLevel;
	}

	public void setrLevel(String rLevel) {
		this.rLevel = rLevel;
	}

	public String getrIngredients() {
		return rIngredients;
	}

	public void setrIngredients(String rIngredients) {
		this.rIngredients = rIngredients;
	}

	public String getrCreationDateStr() {
		return rCreationDateStr;
	}

	public void setrCreationDateStr(String rCreationDateStr) {
		this.rCreationDateStr = rCreationDateStr;
	}

	public LocalDateTime getrCreationDate() {
		return rCreationDate;
	}

	public void setrCreationDate(LocalDateTime rCreationDate) {
		this.rCreationDate = rCreationDate;
	}

	public String getrUpdateDateStr() {
		return rUpdateDateStr;
	}

	public void setrUpdateDateStr(String rUpdateDateStr) {
		this.rUpdateDateStr = rUpdateDateStr;
	}

	public LocalDateTime getrUpdateDate() {
		return rUpdateDate;
	}

	public void setrUpdateDate(LocalDateTime rUpdateDate) {
		this.rUpdateDate = rUpdateDate;
	}

	public Integer getrViewCount() {
		return rViewCount;
	}

	public void setrViewCount(Integer rViewCount) {
		this.rViewCount = rViewCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public List<CookingStep> getCookingSteps() {
		return cookingSteps;
	}

	public void setCookingSteps(List<CookingStep> cookingSteps) {
		this.cookingSteps = cookingSteps;
	}

	//toString
	@Override
	public String toString() {
		return "Recipe [rNum=" + rNum + ", mId=" + mId + ", mNickName=" + mNickName + ", rTitle=" + rTitle
				+ ", rDescription=" + rDescription + ", rCenterImageFile=" + rCenterImageFile + ", rCenterImagePath="
				+ rCenterImagePath + ", rCookType=" + rCookType + ", rCookTime=" + rCookTime + ", rServings="
				+ rServings + ", rLevel=" + rLevel + ", rIngredients=" + rIngredients + ", rCreationDateStr="
				+ rCreationDateStr + ", rCreationDate=" + rCreationDate + ", rUpdateDateStr=" + rUpdateDateStr
				+ ", rUpdateDate=" + rUpdateDate + ", rViewCount=" + rViewCount + ", likeCount=" + likeCount
				+ ", cookingSteps=" + cookingSteps + "]";
	}
	
}