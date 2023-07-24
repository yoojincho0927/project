package kr.human.boot.vo;

import lombok.Data;

@Data
public class CompanyVO {
	private int idx;
	private int ref;
	private String name;
	private String managerId;
	private String managerName;
	private String mode;
	private String managerPhone;
	private String faxNumber;
	private String managerAddress;
	private String managerPost;
	private String managerEmail;
	private String image;
	private double rank;
}
