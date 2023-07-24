package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.MenuVO;
@Mapper
public interface MenuDAO {
	List<MenuVO> select() throws SQLException;
	MenuVO selectByIdx(HashMap<String, Integer> map) throws SQLException;
	List<MenuVO> selectByRef(int idx) throws SQLException;
	MenuVO selectByName(String name) throws SQLException;
	void insert(MenuVO vo)throws SQLException;
	void delete(String name)throws SQLException;
	void update(MenuVO vo)throws SQLException;
}
