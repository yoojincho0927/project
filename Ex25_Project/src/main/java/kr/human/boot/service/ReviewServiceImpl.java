package kr.human.boot.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.boot.dao.CompanyDAO;
import kr.human.boot.dao.FileDAO;
import kr.human.boot.dao.ReviewDAO;
import kr.human.boot.vo.CompanyVO;
import kr.human.boot.vo.FileVO;
import kr.human.boot.vo.Review;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ReviewDAO reviewDAO;

	// 목록보기

	@Autowired
	private FileDAO fileDAO;

	@Override
	public List<Review> selectList() {
		List<Review> list = null;

		try {

//			int totalCount =reviewDAO.selectCount();
//			
//			if(list.getTotalCount()>0) {
//				list.setList(reviewDAO.selectList());
//			
//			}

			list = reviewDAO.selectList();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public Review selectByIdx(int idx) {

		Review review = null;

		try {
			review = reviewDAO.selectByIdx(idx);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return review;

	}

	@Override
	public void insert(Review review) {

		if (review != null) {
			try {
				reviewDAO.insert(review);
				int idx=reviewDAO.selectCompanyIdx(review.getRef());
				CompanyVO cvo = companyDAO.selectByIdx(idx);
				cvo.setRank(reviewDAO.selectReviewRank(idx));
				companyDAO.updateCompanyRank(cvo);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Review review) {

		if (review != null) {
			try {

				reviewDAO.update(review);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(Review review) {
		if (review != null) {
			try {

				reviewDAO.delete(review.getIdx());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public int selectCount(int idx) {
		int totalCount = 0;
		try {
			totalCount = reviewDAO.selectCount(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalCount;
	}

	@Override
	public Review selectByRef(int ref) {
		Review vo = null;
		try {
			vo = reviewDAO.selectByRef(ref);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public Review selectByRefReview(int idx) {
		Review vo = null;
		try {
			vo = reviewDAO.selectByRefReview(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<Review> selectCompany(int idx) {
		List<Review> vo = null;
		try {
			vo = reviewDAO.selectCompany(idx);
			for (Review v : vo) {
				List<FileVO> fvo = fileDAO.selectByRef(v.getIdx());
				v.setFileList(fvo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	// 이거 사실 상 안씀
	public List<Review> selectReviewByComRef(int comref) {
		List<Review> list = null;
		try {
			list = reviewDAO.selectReviewByComRef(comref);
			for (Review vo : list) {
				// 각각의 리뷰에 필요한 파일 목록을 얻어서
				// List<FileVO> files = .comref.
				// vo에 넣어준다.

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Double selectReviewRank(int idx) {
		Double count=0.0;
		try {
			count = reviewDAO.selectReviewRank(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	
	}

	

}
