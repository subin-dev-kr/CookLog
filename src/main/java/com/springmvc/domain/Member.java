package com.springmvc.domain;

public class Member {
	
	private Integer mNum;      //회원고유번호
	private String mId;        //회원ID
	private String mPw;        //회원PW
	private String mPwConfirm; //pw재확인
	private String mNickName;  //회원 닉네임
	private String email;      //회원 이메일
	private String phone;      //회원 전화번호
	private int mRole;     //회원 권한  
	private String mStatus;    //회원 상태
	
	// 매개변수 생성자
	public Member(Integer mNum, String mId, String mPw, String mPwConfirm, String mNickName, String email, String phone,
			int mRole, String mStatus) {
		super();
		this.mNum = mNum;
		this.mId = mId;
		this.mPw = mPw;
		this.mPwConfirm = mPwConfirm;
		this.mNickName = mNickName;
		this.email = email;
		this.phone = phone;
		this.mRole = mRole;
		this.mStatus = mStatus;
	}
	
	// 기본 생성자
	public Member() {
		super();
	}


	// getter & setter
	public Integer getmNum() {
		return mNum;
	}
	
	public void setmNum(Integer mNum) {
		this.mNum = mNum;
	}
	
	public String getmId() {
		return mId;
	}
	
	public void setmId(String mId) {
		this.mId = mId;
	}
	
	public String getmPw() {
		return mPw;
	}
	
	public void setmPw(String mPw) {
		this.mPw = mPw;
	}
	
	public String getmPwConfirm() {
		return mPwConfirm;
	}
	
	public void setmPwConfirm(String mPwConfirm) {
		this.mPwConfirm = mPwConfirm;
	}
	
	public String getmNickName() {
		return mNickName;
	}
	
	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getmRole() {
		return mRole;
	}
	
	public void setmRole(int mRole) {
		this.mRole = mRole;
	}
	
	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	// toString	
	@Override
	public String toString() {
		return "Member [mNum=" + mNum + ", mId=" + mId + ", mPw=" + mPw + ", mPwConfirm=" + mPwConfirm + ", mNickName="
				+ mNickName + ", email=" + email + ", phone=" + phone + ", mRole=" + mRole + ", mStatus=" + mStatus
				+ "]";
	}
	
}
