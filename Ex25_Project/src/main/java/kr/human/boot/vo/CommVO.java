package kr.human.boot.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// p, s, b를 받아주는 VO
@Getter
@ToString
@EqualsAndHashCode
public class CommVO {
	private int p=1,s=5,b=5,idx=0;
	private String mode="insert";
	private int currentPage,pageSize,bottomLength;
	public void setP(int p) {
		if(p<1)p=1;
		this.p = p;
		this.currentPage=p;
	}
	public void setS(int s) {
		this.s = s;
		this.pageSize=s;
	}
	public void setB(int b) {
		this.b = b;
		this.bottomLength=b;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setMode(String mode) {
		if(mode==null)mode="insert";
		this.mode = mode;
	}
	
	
}
