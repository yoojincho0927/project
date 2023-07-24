package kr.human.boot.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.boot.dao.CategoryDAO;
import kr.human.boot.dao.LoginDAO;
import kr.human.boot.dao.MenuDAO;
import kr.human.boot.dao.OrderMenuDAO;
import kr.human.boot.dao.CompanyDAO;
import kr.human.boot.vo.CategoryVO;
import kr.human.boot.vo.LoginVO;
import kr.human.boot.vo.MenuVO;
import kr.human.boot.vo.OrderMenuVO;
import kr.human.boot.vo.PagingVO;
import kr.human.boot.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;

@Service("foodService")
@Slf4j
public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private CompanyDAO restaurantDAO;
	@Autowired
	private MenuDAO menuDAO;
	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private OrderMenuDAO orderMenuDAO;
	@Override
	public List<CategoryVO> selectCategory() {
		List<CategoryVO> vo = null;
		try {
			vo = categoryDAO.select();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<CompanyVO> selectRestaurant() {
		List<CompanyVO> vo = null;
		try {
			vo = restaurantDAO.select();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<MenuVO> selectMenu() {
		List<MenuVO> vo = null;
		try {
			vo = menuDAO.select();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	
	@Override
	public List<CompanyVO> selectByRefRestaurant(int idx,String order) {
		List<CompanyVO> vo = null;
		try {
			HashMap<String, Object> map=new HashMap<>();
			map.put("idx", idx);
			map.put("order", order);
			vo = restaurantDAO.selectByRef(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("selectByIdxRestaurant : {}",vo);
		return vo;
	}

	@Override
	public MenuVO selectByIdxMenu(int idx,int ref) {
		MenuVO vo = null;
		try {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("idx", idx);
			map.put("ref", ref);
			vo = menuDAO.selectByIdx(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public  List<MenuVO> selectByRefMenu(int idx) {
		List<MenuVO> vo = null;
		try {
			vo = menuDAO.selectByRef(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public LoginVO selectLogin(String id) {
		LoginVO vo = null;
		try {
			vo = loginDAO.select(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public CompanyVO selectByIdxRestaurant(int idx) {
		CompanyVO vo = null;
		try {
			vo = restaurantDAO.selectByIdx(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("selectByIdxRestaurant : {}",vo);
		return vo;
	}

	@Override
	public void updateRestaurant(CompanyVO vo) {
		try {
			restaurantDAO.update(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public LoginVO selectLoginByIdx(int idx) {
		LoginVO vo = null;
		try {
			vo = loginDAO.selectByIdx(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public void insertLogin(LoginVO vo) {
		try {
			loginDAO.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insertRestaurant(CompanyVO vo) {
		try {
			restaurantDAO.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public OrderMenuVO selectByOrderMenuName(String name) {
		OrderMenuVO vo = null;
		try {
			vo = orderMenuDAO.selectByName(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	@Override
	public List<OrderMenuVO> selectOrderMenu(int idx) {
		List<OrderMenuVO> vo = null;
		try {
			vo = orderMenuDAO.selectOrderMenu(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}


	@Override
	public List<OrderMenuVO> selectByCategory(int idx) {
		List<OrderMenuVO> vo = null;
		try {
			vo = orderMenuDAO.selectByCategory(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public void insert(OrderMenuVO vo,int idx) {
		try {
			
				 orderMenuDAO.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MenuVO selectByNameMenu(String name) {
		MenuVO vo = null;
		try {
			vo = menuDAO.selectByName(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<OrderMenuVO> selectOrderMenuID(String name) {
		List<OrderMenuVO> vo = null;
		try {
			vo = orderMenuDAO.selectOrderMenuID(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<OrderMenuVO> selectOrderByCount(String name) {
		List<OrderMenuVO> vo = null;
		try {
			vo = orderMenuDAO.selectOrderByCount(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public PagingVO<OrderMenuVO> selectPage(String userid, int start, int size, int length) {
		PagingVO<OrderMenuVO> pv = null;
		try {
			int total=orderMenuDAO.selectCount(userid);
			pv = new PagingVO<>(total, start, size, length);
			HashMap<String, Object> map = new HashMap<>();
			map.put("userid", userid);
			map.put("startNo", pv.getStartNo());
			map.put("endNo", pv.getEndNo());
			List<OrderMenuVO> list = orderMenuDAO.selectPage(map);
			pv.setContent(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv;
	}

	@Override
	public void insertMenu(MenuVO vo) {
		try {
			log.info("insertMenu : {}",vo);
			menuDAO.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletetMenu(String name) {
		try {
			menuDAO.delete(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateMenu(MenuVO vo) {
		try {
			menuDAO.update(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
