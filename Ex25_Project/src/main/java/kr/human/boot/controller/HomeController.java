package kr.human.boot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.human.boot.service.FoodService;
import kr.human.boot.service.ReviewService;
import kr.human.boot.vo.CommVO;
import kr.human.boot.vo.CompanyVO;
import kr.human.boot.vo.LoginVO;
import kr.human.boot.vo.MenuVO;
import kr.human.boot.vo.OrderMenuVO;
import kr.human.boot.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@Autowired
	private FoodService service;

	@Autowired
	ResourceLoader resourceLoader;

	// 파일 저장할때
	@Value("${spring.servlet.multipart.location}")

	private String path;
	// 로그인 화면
	@GetMapping(value = { "login" })
	public String index(Model model, HttpServletRequest request) {
//		model.addAttribute("today",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
		Cookie[] cookies = request.getCookies();
		String id = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id")) {
					id = cookie.getValue();
				}
			}
		}
		model.addAttribute("rid", id);
		return "login";
	}

	@GetMapping(value = "/selectMenu/deleteMenu/deleteOk")
	public String deleteOkGet() {
		return "redirect:/manager";
	}

	@PostMapping(value = "/selectMenu/deleteMenu/deleteOk")
	public String deleteOkPost(@ModelAttribute MenuVO vo) {
		service.deletetMenu(vo.getName());
		return "redirect:/manager";
	}
	// 관리자 모드에서 해당 메뉴를 볼때
	@GetMapping(value = "/selectMenu/{idx}")
	public String selectMenu(@PathVariable int idx, Model model) {
		List<MenuVO> vo = service.selectByRefMenu(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("idx", idx);
		return "selectMenu";
	}
	// 메뉴 추가 html
	@GetMapping(value = "/selectMenu/addMenu/{idx}")
	public String addMenu(@PathVariable int idx, Model model) {
		model.addAttribute("idx", idx);
		return "addMenu";
	}

	@GetMapping(value = "/menuOk")
	public String menuOkGet() {
		return "redirect:/manager";
	}

	@GetMapping(value = "/selectMenu/selectMenuByName/updateOk")
	public String updateOkGet() {
		return "redirect:/manager";
	}

	@PostMapping(value = "/selectMenu/selectMenuByName/updateOk")
	public String updateOkPost(@ModelAttribute MenuVO vo) {
		service.updateMenu(vo);
		return "redirect:/manager";
	}
	// 메뉴를 수정할때 사용
	@GetMapping(value = "/selectMenu/selectMenuByName/{name}")
	public String selectMenuByName(@PathVariable String name, Model model) {
		MenuVO vo = service.selectByNameMenu(name);
		model.addAttribute("vo", vo);
		return "selectMenuByName";
	}
	// 메뉴 추가
	@PostMapping(value = "/selectMenu/addMenu/menuOk")
	public String menuOkPost(@ModelAttribute MenuVO vo, MultipartFile uploadFile) {
		File file = new File(path + "/" + uploadFile.getOriginalFilename());
		try {
			FileCopyUtils.copy(uploadFile.getBytes(), file);
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(
					"./src/main/resources/static/images/" + uploadFile.getOriginalFilename());
			int n = 0;
			byte[] data = new byte[1024 * 1024];
			while ((n = fis.read(data)) > 0) {
				fos.write(data, 0, n);
				fos.flush();
			}
			vo.setImage("images/" + uploadFile.getOriginalFilename());
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("menuOkPost : {}", vo);
		service.insertMenu(vo);
		return "redirect:/manager";
	}
	// 선택한 메뉴 삭제
	@GetMapping(value = "/selectMenu/deleteMenu/{name}")
	public String deleteMenu(@PathVariable String name, Model model) {
		MenuVO vo = service.selectByNameMenu(name);
		model.addAttribute("vo", vo);
		return "deleteMenu";
	}
	// 회사명 불러오기
	@GetMapping(value = "/selectByCompany")
	@ResponseBody
	public CompanyVO selectByCompany(@RequestParam(required = false) int idx) {
		return service.selectByIdxRestaurant(idx);
	}

	@GetMapping(value = { "/", "/yokiyoo" })
	public String yokiyoo() {
		return "yokiyoo";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("id");
		return "redirect:/login";
	}

	@GetMapping(value = "/menuweb/{idx}/exPay")
	public String expayGet(@PathVariable int idx) {
		return "redirect:/menuweb";
	}

	// 결제 화면
	@PostMapping(value = "/pay")
	public String pay(@RequestParam List<String> name, @RequestParam List<String> count, HttpSession session) {
		int idx = Integer.parseInt(session.getAttribute("idx").toString());
		if (session.getAttribute("id") != null) {
			String id = session.getAttribute("id").toString();
			for (int i = 0; i < name.size(); i++) {
				OrderMenuVO ovo = new OrderMenuVO();
				ovo.setName(name.get(i));
				ovo.setCount(Integer.parseInt(count.get(i)));
				ovo.setUserid(id);
				service.insert(ovo, idx);
			}
			log.info("넘겨받은 idx : {}", session.getAttribute("ref"));
			return "redirect:/menuweb/menuweb2/" + session.getAttribute("idx");
		} else
			return "redirect:/login";
	}
	// 주문내역
	@GetMapping(value = "/orderlist/{idx}")
	public String orderlist(Model model, @PathVariable int idx, HttpSession session) {
//		List<OrderMenuVO> order = service.selectOrderMenu(idx);
//		List<MenuVO> vo = new ArrayList<>();
//		for(OrderMenuVO ovo:order) {
//			MenuVO mvo = service.selectByNameMenu(ovo.getName());
//			vo.add(mvo);
//		}
		List<OrderMenuVO> order = service.selectOrderMenu(idx);
		List<Integer> price = new ArrayList<>();
		for (OrderMenuVO o : order) {
			price.add(service.selectByNameMenu(o.getName()).getPrice());
		}
		session.setAttribute("managerid", idx);
		model.addAttribute("ovo", order);
		model.addAttribute("price", price);
		return "orderlist";
	}

	// 마이페이지
		@GetMapping(value = "/mypage2")
		public String mypage2(HttpSession session,Model model) {
			LoginVO vo = service.selectLogin(session.getAttribute("id").toString());
			model.addAttribute("vo",vo);
			return "mypage2";
		}
	
	
	// 주문내역
	@GetMapping(value = "/mypage")
	public String mypage(Model model, HttpSession session, @ModelAttribute CommVO cvo) {
		String id = session.getAttribute("id").toString();
		PagingVO<OrderMenuVO> vo = service.selectPage(id, cvo.getP(), cvo.getS(), cvo.getB());
		model.addAttribute("vo", vo);
		model.addAttribute("ref", session.getAttribute("ref"));
		return "mypage";
	}

	// 관리자 모드에서 해당 업체의 주문내역 확인
	@GetMapping(value = "/orderlist/orderlistjson")
	@ResponseBody
	public List<OrderMenuVO> orderlistjson(HttpSession session) {
		int idx = Integer.parseInt(session.getAttribute("managerid").toString());
		return service.selectByCategory(idx);
	}
	// 주문내역 json
	@GetMapping(value = "/orderlist/orderbyname")
	@ResponseBody
	public List<OrderMenuVO> orderlistbyname(HttpSession session, @RequestParam(required = false) String name) {
		return service.selectOrderByCount(name);
	}

	// 약관동의
	@GetMapping(value = "/term")
	public String term() {
		return "term";
	}

	// 결제화면으로 가기위한 POST
	@PostMapping(value = "/menuweb/menuweb2/exPay")
	public String expayPost(@RequestParam List<String> name, @RequestParam List<Integer> count, Model model) {
		List<MenuVO> vo = new ArrayList<>();
		List<Integer> price = new ArrayList<>();
		for (String i : name) {
			MenuVO v = service.selectByNameMenu(i);
			log.info("결과값 : {}", i);
			vo.add(v);
			price.add(v.getPrice());
		}
		model.addAttribute("name", name);
		model.addAttribute("count", count);
		model.addAttribute("price", price);
		model.addAttribute("vo", vo);
		return "pay";
	}

	// DB에 등록한 업체명
	@GetMapping(value = "/menuweb/{idx}")
	public String menuweb(@PathVariable int idx, Model model, HttpSession session) {
		session.setAttribute("ref", idx);
		session.setMaxInactiveInterval(60 * 60 * 7);
		model.addAttribute("idx", idx);
		model.addAttribute("id", session.getAttribute("id"));
		List<CompanyVO> vo = service.selectByRefRestaurant(idx,null);
		model.addAttribute("vo", vo);
		model.addAttribute("size", vo.size());
		log.info("menuweb : {}", vo);
		return "menuweb2";
	}

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ResourceLoader loader;
	// 메뉴보기
	@GetMapping(value = "/menuweb/menuweb2/{idx}")
	public String menuweb2(@PathVariable int idx, Model model, HttpSession session) throws IOException {
		session.setAttribute("idx", idx);
		session.setMaxInactiveInterval(60 * 60 * 7);
		model.addAttribute("idx", idx);
		model.addAttribute("id", session.getAttribute("id"));
		CompanyVO vo = service.selectByIdxRestaurant(idx);
		model.addAttribute("vo", vo);
		log.info("아이디아이디아이디 : {}", session.getAttribute("id"));
		String path = loader.getResource("/").getURI().toString() + "upload/";
		model.addAttribute("count", reviewService.selectCount(idx));
		path = path.substring(6);
		log.info("파일 경로 : {}", path);
		return "menuweb";
	}

	@GetMapping(value = "loginOk")
	public String loginGetOk() {
		return "redirect:/";
	}

	@GetMapping(value = "/selectCompany")
	@ResponseBody
	public List<CompanyVO> selectCompany(HttpSession session,@RequestParam(required = false) String order){
		int ref = Integer.parseInt(session.getAttribute("ref").toString());
		return service.selectByRefRestaurant(ref,order);
	}
	
	// 관리자 모드
	@GetMapping(value = "/manager")
	public String manager(HttpSession session, Model model) {
		String id = session.getAttribute("id").toString();
		LoginVO vo = service.selectLogin(id);
		// int ref=Integer.parseInt(session.getAttribute("ref").toString());
		model.addAttribute("vo", service.selectByIdxRestaurant(vo.getRef()));

		model.addAttribute("id", vo.getId());
		model.addAttribute("idx", vo.getRef());
		return "manager";
	}

	// 로그인 POST
	@PostMapping(value = "/loginOk")
	public String loginPostOk(@ModelAttribute LoginVO lvo, Model model, HttpServletResponse response,
			@RequestParam(required = false) boolean check, HttpServletRequest request) {
		LoginVO vo = service.selectLogin(lvo.getId());
		log.info("loginPostOk : {}", vo);
		if (vo == null || !(lvo.getId().equals(vo.getId()) && vo.getPassword().equals(lvo.getPassword()))) {
			return "login";
		}
		Cookie cookie = new Cookie("id", vo.getId());
		if (check) {
			cookie.setMaxAge(60 * 60 * 20);
		} else
			cookie.setMaxAge(0);
		response.addCookie(cookie);
		model.addAttribute("id", vo.getId());
		model.addAttribute("idx", vo.getRef());
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("id", vo.getId());
		session.setMaxInactiveInterval(60 * 60 * 7);
		model.addAttribute("vo", service.selectByIdxRestaurant(vo.getRef()));

		if (vo.getLogin_mode() == 0) {
			return "yokiyoo";
		} else
			return "manager";
	}

	@GetMapping(value = "/loginjson")
	@ResponseBody
	public LoginVO loginjson(@RequestParam(required = false) String id) {
		log.info("loginjson : {}", id);
		LoginVO vo = service.selectLogin(id);
		return vo;
	}

	// 해당 업체의 메뉴 확인
	@GetMapping(value = "menuweb/menuweb2/selectByRef/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<MenuVO> selectByRef(@PathVariable int idx) {
		List<MenuVO> vo = service.selectByRefMenu(idx);
		return vo;
	}

	// 해당업체 메뉴 JSON
	@GetMapping(value = "menuweb/menuweb2/ex3/selectByIdx/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MenuVO selectByIdx(@PathVariable int idx, HttpSession session) {
		int ref = Integer.parseInt(session.getAttribute("idx").toString());
		MenuVO vo = service.selectByIdxMenu(idx, ref);
		return vo;
	}

	@GetMapping(value = "menuweb/menuweb2/ex3/{idx}")
	public String ex3(@PathVariable int idx, Model model, HttpSession session) {
		int ref = Integer.parseInt(session.getAttribute("idx").toString());
		model.addAttribute("vo", service.selectByIdxMenu(idx, ref));
		log.info("ex3 결과값 : {}", service.selectByIdxMenu(idx, ref));
		model.addAttribute("idx", idx);
		return "ex3";
	}

	@GetMapping(value = "/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping(value = "/signupOk")
	public String signupGet() {
		return "redirect:/signup";
	}

	// 해당 메뉴를 구매한 유저목록
	@GetMapping(value = "/orderlist/orderbyname/{name}")
	public String orderByName(@PathVariable String name, Model model) {
		List<OrderMenuVO> vo = service.selectOrderByCount(name);
		model.addAttribute("ovo", vo);
		model.addAttribute("name", name);
		return "orderByName";
	}

	@PostMapping(value = "/signupOk")
	public String signupPost(@ModelAttribute LoginVO vo) {
		log.info("signupOk : {}", vo.getAddr() + vo.getAddr2());
		service.insertLogin(vo);
		return "redirect:/";
	}

	@GetMapping(value = "/updateOk")
	public String updateGet() {
		return "redirect:/manager";
	}

	@PostMapping(value = "/updateOk")
	public String updatePost(@ModelAttribute CompanyVO vo, Model model,
			@RequestParam(required = false) MultipartFile uploadFile) {
		if (uploadFile != null) {
			File file = new File(path + "/" + uploadFile.getOriginalFilename());
			try {
				FileCopyUtils.copy(uploadFile.getBytes(), file);
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(
						"./src/main/resources/static/images/" + uploadFile.getOriginalFilename());
				int n = 0;
				byte[] data = new byte[1024 * 1024];
				while ((n = fis.read(data)) > 0) {
					fos.write(data, 0, n);
					fos.flush();
				}
				vo.setImage("images/" + uploadFile.getOriginalFilename());
				fos.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("타면 안된다.");
		}
		if (vo.getMode().equals("update")) {
			log.info("uploadPost 결과값 : {}", vo.getImage());
			service.updateRestaurant(vo);
		} else {
			service.insertRestaurant(vo);
		}
		CompanyVO rvo = service.selectByIdxRestaurant(vo.getIdx());
		LoginVO lvo = service.selectLoginByIdx(rvo.getIdx());
		model.addAttribute("id", lvo.getId());
		model.addAttribute("vo", vo);
		model.addAttribute("idx", lvo.getRef());
		return "redirect:/manager";
	}
}
