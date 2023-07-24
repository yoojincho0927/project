package kr.human.boot.vo;

import lombok.Data;

@Data
public class LoginVO {
	private int idx;
	private int ref;
	private int login_mode;
	private String id;
	private String password;
	private String email;
	private String addr;
	private String addr2;
	private boolean check_login;
	private int ckb;
}
