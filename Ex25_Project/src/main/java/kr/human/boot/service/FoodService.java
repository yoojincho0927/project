package kr.human.boot.service;

import java.util.List;

import kr.human.boot.vo.CategoryVO;
import kr.human.boot.vo.CompanyVO;
import kr.human.boot.vo.LoginVO;
import kr.human.boot.vo.MenuVO;
import kr.human.boot.vo.OrderMenuVO;
import kr.human.boot.vo.PagingVO;

public interface FoodService {
	List<CategoryVO> selectCategory();
	List<CompanyVO> selectRestaurant();
	List<MenuVO> selectMenu();
	CompanyVO selectByIdxRestaurant(int idx);
	List<CompanyVO> selectByRefRestaurant(int idx,String order);	
	MenuVO selectByIdxMenu(int idx,int ref);
	List<MenuVO> selectByRefMenu(int idx);
	MenuVO selectByNameMenu(String name);
	LoginVO selectLogin(String id);
	LoginVO selectLoginByIdx(int idx);
	void updateRestaurant(CompanyVO vo);
	void insertRestaurant(CompanyVO vo);
	void insertLogin(LoginVO vo);
	OrderMenuVO selectByOrderMenuName(String name);
	List<OrderMenuVO> selectByCategory(int idx);
	List<OrderMenuVO> selectOrderMenu(int idx);
	List<OrderMenuVO> selectOrderMenuID(String name);
	List<OrderMenuVO> selectOrderByCount(String name);
	PagingVO<OrderMenuVO> selectPage(String userid,int start,int size,int length);
	void insert(OrderMenuVO vo,int idx);
	void insertMenu(MenuVO vo);
	void deletetMenu(String name);
	void updateMenu(MenuVO vo);
}
