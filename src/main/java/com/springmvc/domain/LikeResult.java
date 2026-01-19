package com.springmvc.domain;

public class LikeResult {
	
	private final boolean liked;
    private final int likeCount;
    
    //매개변수 생성자
	public LikeResult(boolean liked, int likeCount) {
		super();
		this.liked = liked;
		this.likeCount = likeCount;
	}
	//Getter & Setter
	public boolean isLiked() {
		return liked;
	}

	public int getLikeCount() {
		return likeCount;
	}
	
	//toString
	@Override
	public String toString() {
		return "LikeResult [liked=" + liked + ", likeCount=" + likeCount + "]";
	}

}
