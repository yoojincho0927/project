package kr.human.boot.service;

import java.sql.SQLException;
import java.util.List;

import kr.human.boot.vo.FileVO;

public interface FileService {
	List<FileVO> selectByRef(int ref) throws SQLException;
	void insert(FileVO fileVO)  throws SQLException;
	void insert2(FileVO fileVO)  throws SQLException;
	void update(FileVO fileVO)  throws SQLException;
	void delete(FileVO fileVO) throws SQLException;
	void deleteByRef(int ref) throws SQLException;
}
