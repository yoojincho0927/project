package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.BoardVO;

@Mapper
public interface BoardDAO {
	List<BoardVO> select(HashMap<String, Object> map) throws SQLException;
	BoardVO selectByIdx(int idx) throws SQLException;
	int selectCount() throws SQLException;
	void insert(BoardVO vo) throws SQLException;
	void delete(int idx) throws SQLException;
	void update(BoardVO vo) throws SQLException;
}
