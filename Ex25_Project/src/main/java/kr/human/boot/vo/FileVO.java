package kr.human.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*CREATE SEQUENCE file2_idx_seq;
CREATE TABLE File2(
idx NUMBER NOT null,
uuid varchar2(2000) NOT NULL,
fileName varchar2(2000) NOT NULL,
contentType varchar2(2000) NOT NULL
);*/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileVO {

	private int idx;
	private String uuid; // 유일한 이름을 만들어 중복이름 처리할때 사용할 ID
	private String fileName; // 실제 파일이름
	private String contentType; // 파일의 종류
	private int ref; // 원본 파일 이름을 review idx와 참조

	public FileVO(String uuid, String fileName, String contentType) {
		super();
		this.uuid = uuid;
		this.fileName = fileName;
		this.contentType = contentType;

	}

	public FileVO(String uuid, String fileName, String contentType, int ref) {
		super();
		this.uuid = uuid;
		this.fileName = fileName;
		this.contentType = contentType;
		this.ref = ref;
	}

}
