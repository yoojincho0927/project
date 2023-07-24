package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.CategoryVO;
@Mapper
public interface CategoryDAO {
	List<CategoryVO> select() throws SQLException;
}
