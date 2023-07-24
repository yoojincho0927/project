package kr.human.boot.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.LoginVO;
@Mapper
public interface LoginDAO {
	LoginVO select(String name) throws SQLException;
	LoginVO selectByIdx(int idx) throws SQLException;
	void insert(LoginVO vo) throws SQLException;
}
