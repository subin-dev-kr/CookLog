package com.springmvc.domain;

public class LikeToggleResponse {

    private boolean ok;
    private boolean liked;
    private int likeCount;
    private String code; // NOT_LOGIN 같은 에러 코드용
    
    // 성공 응답용
	public LikeToggleResponse(boolean ok, boolean liked, int likeCount) {
		super();
		this.ok = ok;
		this.liked = liked;
		this.likeCount = likeCount;
	}
	// 실패 응답용 (NOT_LOGIN 같은 코드)
    public LikeToggleResponse(boolean ok, String code) {
        this.ok = ok;
        this.code = code;
    }

	//기본 생성자
	public LikeToggleResponse() {
		super();
	}

	//Getter & Setter
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// toString
	@Override
	public String toString() {
		return "LikeToggleResponse [ok=" + ok + ", liked=" + liked + ", likeCount=" + likeCount + ", code=" + code
				+ "]";
	}

}
