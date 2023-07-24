package kr.human.boot.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Review {
	private int idx;
	private int ref;
	private String content;
	private String userid;
	private String name;
	private int score;
	private String regDate2;
	private int comref;
	private int count;
//	//파일을 같이 axios로 받아오게 하는 법
//	private String uuid;
//	private String fileName;;
	
	private List<FileVO> fileList;
	
}
