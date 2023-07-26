package kr.human.boot.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import kr.human.boot.vo.Review;

public interface ReviewService {
	// 목록보기
	List<Review> selectList() ;

	// 내용보기
	Review selectByIdx(int idx) ;

	// 저장하기
	void insert(Review review) ;

	// 수정하기
	void update(Review review) ;

	// 삭제하기
	void delete(Review review) ;
	
	int selectCount(int idx);
	//ref로 가져오기
	Review selectByRef(int ref)  ;
	//review 의 ref를 가져오는데 그게 review 의 idx로 가져옴  throws SQLException 없어도 
//userid와 file을 같이 받아오는 쿼리
	Review selectByRefReview(int idx);
	//해당 Company와 file을 List로 가져옴
	List<Review> selectCompany(int idx) ;
	List<Review> selectCompany2(int idx, int count);
	
	
	double selectReviewRank(int idx);
	
	List<Review> selectReviewByComRef(int comref);
	
}
