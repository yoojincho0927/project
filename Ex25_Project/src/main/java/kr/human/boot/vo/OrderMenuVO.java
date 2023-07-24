package kr.human.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuVO {
	private int idx;
	private String name;
	private int count;
	private String userid;
	private int ref;
}
