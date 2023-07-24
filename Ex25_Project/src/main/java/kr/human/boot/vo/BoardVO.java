package kr.human.boot.vo;

import java.util.Date;

import lombok.Data;
@Data
public class BoardVO {
	private int idx;
	private String name;
	private String password;
	private String subject;
	private String content;
	private String category; 
	private String mode; 
	private String search;
	private Date regDate;
}
