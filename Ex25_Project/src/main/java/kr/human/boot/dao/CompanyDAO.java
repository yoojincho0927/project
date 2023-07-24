package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.CompanyVO;
@Mapper
public interface CompanyDAO {
	List<CompanyVO> select() throws SQLException;
	CompanyVO selectByIdx(int idx) throws SQLException;
	List<CompanyVO>  selectByRef(HashMap<String, Object> map) throws SQLException;
	void update(CompanyVO vo) throws SQLException;
	void insert(CompanyVO vo) throws SQLException;
	void updateCompanyRank(CompanyVO vo) throws SQLException;
}
