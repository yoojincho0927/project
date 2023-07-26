package kr.human.boot.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.boot.vo.Review;


@Mapper
public interface ReviewDAO {
	int selectCount(int idx) throws SQLException;
	//1개 가져오기
	Review selectByIdx(int idx) throws SQLException;
	//review의 ref를 가져오는데 idx값으로 가져오기
	Review selectByRefReview(int idx) throws SQLException;

	//해당 Company를 가져오는데  idx값으로 가져오는데 list로 가져오기
	List<Review> selectCompany(int idx) throws SQLException;

	//해당 Company를 가져오는데  idx값으로 가져오는데 list로 원하는 개수만 가져오기
	List<Review> selectCompany2(HashMap<String, Integer> map) throws SQLException;
	
	
	//해당 Review를 가져오는데 list로 가져오기
	List<Review>  selectList()  throws SQLException;
	void insert(Review review)  throws SQLException;
	void update(Review review)  throws SQLException;
	void delete(int idx) throws SQLException;
	//review의 ref가져오기
	Review selectByRef(int ref)  throws SQLException;
	
	List<Review> selectReviewByComRef(int comref) throws SQLException;
	
	double selectReviewRank(int idx) throws SQLException;
	
	int selectCompanyIdx(int idx)throws SQLException;
}
