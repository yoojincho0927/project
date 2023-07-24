package kr.human.boot.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.boot.dao.FileDAO;
import kr.human.boot.vo.FileVO;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileDAO fileDAO;
	

	@Override
	public List<FileVO> selectByRef(int ref) throws SQLException {
		// TODO Auto-generated method 

		 
		 List<FileVO> list = fileDAO.selectByRef(ref);
		 
		return list;
	}

	@Override
	public void insert(FileVO fileVO) throws SQLException {
		// TODO Auto-generated method stub
		
		if(fileVO!=null) {
			fileDAO.insert(fileVO);
		}
		
	}

	@Override
	public void update(FileVO fileVO) throws SQLException {
		// TODO Auto-generated method stub
		if(fileVO!=null) {
			fileDAO.update(fileVO);
		}
	}

	@Override
	public void delete(FileVO fileVO) throws SQLException {
		// TODO Auto-generated method stub
		
		if(fileVO!=null) {
			fileDAO.delete(fileVO.getIdx());
		}
	}
     
	
	//수정할 이미지 가져오는 해당 ref 
	@Override
	public void deleteByRef(int ref) throws SQLException {
		fileDAO.deleteByRef(ref);
	}

	@Override
	public void insert2(FileVO fileVO) throws SQLException {
		fileDAO.insert2(fileVO);
	}

}
