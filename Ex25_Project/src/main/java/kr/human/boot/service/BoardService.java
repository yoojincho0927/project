package kr.human.boot.service;

import kr.human.boot.vo.BoardVO;
import kr.human.boot.vo.PagingVO;

public interface BoardService {
	PagingVO<BoardVO> select(int start,int size,int length,String category,String search);
	BoardVO selectByIdx(int idx);
	void insert(BoardVO vo);
	void delete(BoardVO vo);
	void update(BoardVO vo);
}
