package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.FileVO;

@Mapper
public interface FileDAO {

	FileVO selectByIdx(int idx) throws SQLException;
	List<FileVO> selectByRef(int ref) throws SQLException;
	void insert(FileVO fileVO)  throws SQLException;
	void insert2(FileVO fileVO)  throws SQLException;
	void update(FileVO fileVO)  throws SQLException;
	void delete(int idx) throws SQLException;
	void deleteByRef(int ref) throws SQLException;
}
