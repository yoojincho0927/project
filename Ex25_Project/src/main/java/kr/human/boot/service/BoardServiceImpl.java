package kr.human.boot.service;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.boot.dao.BoardDAO;
import kr.human.boot.vo.BoardVO;
import kr.human.boot.vo.PagingVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO dao;
	@Override
	public PagingVO<BoardVO> select(int start, int size, int length,String category,String search) {
		PagingVO<BoardVO> pv = null;
		try {
			int total=dao.selectCount();
			pv = new PagingVO<>(total, start, size, length);
			HashMap<String, Object> map = new HashMap<>();
			map.put("startNo", pv.getStartNo());
			map.put("endNo", pv.getEndNo());
			if(category==null || category.trim().length()==0)category=null;
			else map.put("category", category);
			if(search==null || search.trim().length()==0)search=null;
			else map.put("search", search);
			pv.setContent(dao.select(map));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv;
	}

	@Override
	public BoardVO selectByIdx(int idx) {
		BoardVO vo = null;
		try {
			vo = dao.selectByIdx(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public void insert(BoardVO vo) {
		try {
			dao.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(BoardVO vo) {
		try {
			BoardVO bvo = dao.selectByIdx(vo.getIdx());
			if(bvo.getPassword().equals(vo.getPassword()))
				dao.delete(vo.getIdx());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(BoardVO vo) {
		try {
			BoardVO bvo = dao.selectByIdx(vo.getIdx());
			if(bvo.getPassword().equals(vo.getPassword()))
				dao.update(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
