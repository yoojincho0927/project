package kr.human.boot.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.human.boot.service.BoardService;
import kr.human.boot.vo.BoardVO;
import kr.human.boot.vo.CommVO;
import kr.human.boot.vo.PagingVO;

@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	@GetMapping(value = {"/menuweb/problemsolve","/menuweb/menuweb2/problemsolve"})
	public String index(@ModelAttribute CommVO cv,Model model) {
		PagingVO<BoardVO> vo = service.select(cv.getP(), cv.getS(), cv.getB(),null,null);
		model.addAttribute("vo",vo.getContent());
		model.addAttribute("pv",vo);
		return "board";
	}
	// 저장 폼
	@GetMapping(value = {"/menuweb/insert","/menuweb/menuweb2/problemsolve/insert"})
	public String insert() {
		return "insert";
	}
	// 검색을 수행한 후
	@PostMapping(value = {"/menuweb/select","/menuweb/menuweb2/select"})
	public String select(@RequestParam(required = false) HashMap<String, Object> map,Model model,@ModelAttribute CommVO cv) {
		PagingVO<BoardVO> vo = service.select(cv.getP(), cv.getS(), cv.getB(),map.get("category").toString(),map.get("search").toString());
		model.addAttribute("vo",vo.getContent());
		model.addAttribute("pv",vo);
		model.addAttribute("content",map.get("search"));
		return "board";
	}
	@GetMapping(value = "/menuweb/problemsolve/insertOk")
	public String insertOkGet() {
		return "redirect:/";
	}
	// 저장 후
	@PostMapping(value = {"/menuweb/insertOk","/menuweb/menuweb2/insertOk"})
	public String insertOkPost(@ModelAttribute BoardVO vo) {
		service.insert(vo);
		return "redirect:/menuweb/problemsolve";		
	}
	@GetMapping(value = "/menuweb/view/updateOk")
	public String updateOkGet() {
		return "redirect:/menuweb/problemsolve";
	}
	// 수정 후
	@PostMapping(value = {"/menuweb/view/updateOk","/menuweb/menuweb2/view/updateOk"})
	public String updateOkPost(@ModelAttribute BoardVO vo) {
		if(vo.getMode().equals("update"))service.update(vo);
		else service.delete(vo);
		return "redirect:/menuweb/problemsolve";		
	}
	// 내용보기
	@GetMapping(value = {"/menuweb/view/{idx}","/menuweb/menuweb2/view/{idx}"})
	public String view(@PathVariable int idx, Model model) {
		BoardVO vo = service.selectByIdx(idx);
		model.addAttribute("vo",vo);
		return "view";
	}
}