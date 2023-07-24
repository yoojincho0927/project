package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.OrderMenuVO;
@Mapper
public interface OrderMenuDAO {
	OrderMenuVO selectByName(String name) throws SQLException;
	OrderMenuVO selectOrderMenuName(HashMap<String, String> map) throws SQLException;
	List<OrderMenuVO> selectByCategory(int idx) throws SQLException;
	List<OrderMenuVO> selectOrderMenu(int idx) throws SQLException;
	List<OrderMenuVO> selectOrderMenuID(String name) throws SQLException;
	List<OrderMenuVO> selectOrderByCount(String name) throws SQLException;
	List<OrderMenuVO> selectPage(HashMap<String, Object> map) throws SQLException;
	int selectCount(String name)throws SQLException;
	void insert(OrderMenuVO vo) throws SQLException;
	void update(OrderMenuVO vo) throws SQLException;
}
