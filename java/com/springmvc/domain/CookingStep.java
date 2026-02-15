package com.springmvc.domain;

import org.springframework.web.multipart.MultipartFile;

public class CookingStep {

	private Integer cNum;              //요리 고유번호
	private Integer rNum;              //레시피 고유번호
	private Integer cStep;             //요리 순서
	private String cInstructions;      //요리 설명
	private MultipartFile cImageFile;  //요리 사진
	private String cImage;             //이미지 경로
	
	//매개변수 생성자
	public CookingStep(Integer cNum, Integer rNum, Integer cStep, String cInstructions, MultipartFile cImageFile,
			String cImage) {
		super();
		this.cNum = cNum;
		this.rNum = rNum;
		this.cStep = cStep;
		this.cInstructions = cInstructions;
		this.cImageFile = cImageFile;
		this.cImage = cImage;
	}
	//기본 생성자
	public CookingStep() {
		super();
	}
	//getter & setter
	public Integer getcNum() {
		return cNum;
	}
	public void setcNum(Integer cNum) {
		this.cNum = cNum;
	}
	public Integer getrNum() {
		return rNum;
	}
	public void setrNum(Integer rNum) {
		this.rNum = rNum;
	}
	public Integer getcStep() {
		return cStep;
	}
	public void setcStep(Integer cStep) {
		this.cStep = cStep;
	}
	public String getcInstructions() {
		return cInstructions;
	}
	public void setcInstructions(String cInstructions) {
		this.cInstructions = cInstructions;
	}
	public MultipartFile getcImageFile() {
		return cImageFile;
	}
	public void setcImageFile(MultipartFile cImageFile) {
		this.cImageFile = cImageFile;
	}
	public String getcImage() {
		return cImage;
	}
	public void setcImage(String cImage) {
		this.cImage = cImage;
	}
	//toString
	@Override
	public String toString() {
		return "CookingStep [cNum=" + cNum + ", rNum=" + rNum + ", cStep=" + cStep + ", cInstructions=" + cInstructions
				+ ", cImageFile=" + cImageFile + ", cImage=" + cImage + "]";
	}
	
	
}
