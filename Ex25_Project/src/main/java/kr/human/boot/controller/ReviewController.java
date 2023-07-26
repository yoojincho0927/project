package kr.human.boot.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.human.boot.service.FileService;
import kr.human.boot.vo.FileVO;
import kr.human.boot.vo.Review;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/review") // get mapping앞에 모든 경로 앞에 이 것을 넣어준다는 뜻
public class ReviewController {

	@Autowired
	private kr.human.boot.service.ReviewService reviewService;

	@Autowired
	private FileService fileService;

	@Autowired
	ResourceLoader resourceLoader;
	
	//개수 세기
	
	
	
	

	// 저장폼 띄우기

	// review VO에 있는 ref와 같아야 함
	@GetMapping(value = "insert/{ref}")
	public String insert(Model model, @PathVariable int ref) {

		model.addAttribute("ref", ref);
		return "review/insert";
	}

	// return은 템플릿에서 간다는 뜻이다.

	// 저장하기 : Get방식일 경우 리스트로 강제이동 //url 에 직접 때려 넣었을 때 얘기
	@GetMapping(value = "/insertOk")
	public String insertOkGet(@ModelAttribute Review review, Model model) {
		return "redirect:review/list";
	}

	// 저장하기 : Post방식일때 저장하고 1페이지로 이동
	@PostMapping(value = "/insert/insertOk")
	public String insertPost(@ModelAttribute Review review, @RequestParam MultipartFile[] uploadFile,
			HttpServletRequest request, HttpSession session) throws IOException, SQLException {

		log.info("넘어온값1 : {}", review);
		log.info("넘어온값1 : {}", Arrays.toString(uploadFile));
		// id를 받아오기 위해 세션값을 가져옴
		review.setUserid(session.getAttribute("id").toString());

		reviewService.insert(review); // 글저장

		// 파일처리
		String filePath = resourceLoader.getResource("/").getURI().toString() + "upload/";
		filePath = filePath.substring(6);
		log.info("-".repeat(80));
		log.info(filePath);
		File f = new File(filePath);
		if (!f.exists())
			f.mkdirs();
		log.info("-".repeat(80));

		if (uploadFile != null && uploadFile.length > 0) { // 파일이 넘어 왔다면
			for (MultipartFile file : uploadFile) {
				if (!file.isEmpty()) { // 파일이 있다면
					FileVO vo = new FileVO(UUID.randomUUID().toString(), file.getOriginalFilename(),
							file.getContentType());

					// 실제 파일을 저장해줘야 한다.
					File newFile = new File(filePath + vo.getUuid());
					file.transferTo(newFile);

					// 여기에서 파일테이블에
					// idx, 원본글번호, 원본이름, 를 넣어줘야 한다

					fileService.insert(vo);
					log.info("-".repeat(80));
					log.info("파일 정보!!!! {}", vo.toString());
					log.info("-".repeat(80));

				}
			}
		}

		return "redirect:/menuweb/menuweb2/" + session.getAttribute("idx").toString();
		// // 이동
	}

	// 리뷰가 있으면go.html 로 가고 없으면 insert창으로 가는 것 만들어보자!
	@GetMapping(value = "/{idx}") // ordermenu에 대한 idx값 넣기
	public String terminal(@PathVariable int idx, HttpSession session) {
		Review vo = reviewService.selectByRef(idx);
		log.info("resultVo : {}", vo);
		if (vo != null) {
			return "review/go";
			// -->에러발생return
			// "redirect:/menuweb/menuweb2/"+session.getAttribute("idx").toString();
			// Cannnot invoke 어쩌고 저쩌고
		} else {
			return "redirect:/review/insert/" + idx;
		}
	}

	// menuweb 에 대한 axios를 만드는 주소창 만들기 @ResponseBody를 써줘야하고 return값은 주소창이 아니여야함
	// ->엑시오스에서 주소창을 써주기 때문
	// axios.get('/review/selectByRefReview') 주소창을 정확하게 써줘야함
	// 전체가져오기
	/*@GetMapping(value = "/selectByRefReview")
	@ResponseBody
	public List<Review> list2(HttpSession session) {
		int idx = Integer.parseInt(session.getAttribute("idx").toString());
		log.info("=".repeat(100));
		log.info("결과 : {}", reviewService.selectCompany(idx));
		
		log.info("=".repeat(100));
		return reviewService.selectCompany(idx);
	}*/

	@GetMapping(value = "/selectByRefReview2")
	@ResponseBody
	public List<Review> revice2(@RequestParam(defaultValue = "5")int count, HttpSession session) {
		int idx = Integer.parseInt(session.getAttribute("idx").toString());
		log.info("=".repeat(100));
		log.info("결과 : {}", reviewService.selectCompany2(idx,count));
		List<Review> vo= reviewService.selectCompany2(idx, count);
		log.info("=".repeat(100));
		return vo;
	}

	/*
	 * // 목록보기
	 * 
	 * @GetMapping(value = "/list") public String list(Model model) throws
	 * SQLException {
	 * 
	 * List<Review> page = reviewService.selectList();
	 * 
	 * model.addAttribute("list", page); model.addAttribute("count",
	 * reviewService.selectCount()); log.info("가져온 값 : {}", page);
	 * 
	 * return "review/list"; }
	 */
	// 내용보기

	@GetMapping(value = "/view")
	public String view(@RequestParam(required = false, defaultValue = "1") int idx, Model model) throws SQLException {
		log.info("내용넘어온값 : {}", idx);
		// 글
		Review review = reviewService.selectByIdx(idx);
		model.addAttribute("vo", review);
		// 그 글에 있는 그림 정보
		List<FileVO> fileList = fileService.selectByRef(idx);
		model.addAttribute("fileList", fileList);

		return "review/view";
	}

	// 내용에서 수정폼으로 갈 때 idx값을 받아오기 위함
	// 수정폼 띄우기
	@GetMapping(value = "/update")
	public String update(@RequestParam(required = false, defaultValue = "1") int idx, Model model) throws SQLException {
		log.info("수정폼넘어온값 : {}", idx);
		// 번호를 받아 DB에서 데이터를 받아온다.
		Review review = reviewService.selectByIdx(idx);

		if (review != null) {
			// 리뷰가 있다면 모델에 담아서 수정 폼으로 간다.
			model.addAttribute("vo", review);
			return "review/update";
		} else {
			// 없다면 DB에 해당 리뷰가 없다.....
			return "redirect:/review/list";

		}
	}

	// 수정하기 : Get방식일 경우 리스트로 강제이동
	@GetMapping(value = "/updateOk")
	public String updateOkGet(@ModelAttribute Review review, Model model) {
		return "redirect:/list";
	}

	// 수정하기 : Post방식일때 저장하고 1페이지로 이동
	@PostMapping(value = "/updateOk")
	public String updateOkPost(@ModelAttribute Review review, @RequestParam MultipartFile[] uploadFile,
			HttpServletRequest request, HttpSession session) throws IOException, SQLException {

		log.info("수정하기넘어온값1 : {}", review);

		// id를 받아오기 위해 세션값을 가져옴
		review.setUserid(session.getAttribute("id").toString());

		// 글 수정
		reviewService.update(review);

		fileService.deleteByRef(review.getIdx()); // 이전 파일 모두 지우고 //update 파일 할때 삭제를 해주고, 해당 ref를 받아와야 한다.......

		// review.getIdx()이게 해당 ref
		// 파일처리
		String filePath = resourceLoader.getResource("/").getURI().toString() + "upload/";
		filePath = filePath.substring(6);
		log.info("-".repeat(80));
		log.info(filePath);
		File f = new File(filePath);
		if (!f.exists())
			f.mkdirs();
		log.info("-".repeat(80));

		if (uploadFile != null && uploadFile.length > 0) { // 파일이 넘어 왔다면
			for (MultipartFile file : uploadFile) {
				if (!file.isEmpty()) { // 파일이 있다면
					FileVO vo = new FileVO(UUID.randomUUID().toString(), file.getOriginalFilename(),
							file.getContentType(), review.getIdx()); // <--- 해당 ref 넣은 거임

					// 실제 파일을 저장해줘야 한다.
					File newFile = new File(filePath + vo.getUuid());
					file.transferTo(newFile);

					// 여기에서 파일테이블에
					// idx, 원본글번호, 원본이름, 를 넣어줘야 한다

					fileService.insert2(vo); // 새로 추가한 파일 저장
					log.info("-".repeat(80));
					log.info("파일 수정!!!! {}", vo.toString());
					log.info("-".repeat(80));

				}
			}
		}
		return "redirect:/menuweb/menuweb2/" + session.getAttribute("idx").toString();
		// return "redirect:/review/view?idx=" + review.getIdx();
	}

	// 삭제폼 띄우기
	@GetMapping(value = "/delete")
	public String delete(@RequestParam(required = false, defaultValue = "1") int idx, Model model,HttpSession session) throws SQLException {
		log.info("삭제폼 넘어온값 : {}", idx);
		// 번호를 받아 DB에서 데이터를 받아온다.
		// id를 받아오기 위해 세션값을 가져옴

		//Review review = reviewService.selectByIdx(idx);

		// 리뷰가 있으나 없으나 목록으로 간다.
		//model.addAttribute("vo", review);
		Review r = new Review();
		r.setIdx(idx);
		reviewService.delete(r);
		return "redirect:/menuweb/menuweb2/"+session.getAttribute("idx").toString();
	}

	// 삭제하기 : Get방식일 경우 리스트로 강제이동
	@GetMapping(value = "/deleteOk")
	public String deleteOkGet(@ModelAttribute Review review, Model model) {
		return "redirect:/list";
	}

	// 삭제하기 : Post방식일때 저장하고 1페이지로 이동
	@PostMapping(value = "/deleteOk")
	public String deleteOkPost(@ModelAttribute Review review, HttpServletRequest request, HttpSession session)
			throws SQLException {

		log.info("삭제하기 넘어온값1 : {}", review);
		// id를 받아오기 위해 세션값을 가져옴
		review.setUserid(session.getAttribute("id").toString());

		reviewService.delete(review);
		return "redirect:/menuweb/menuweb2/" + session.getAttribute("idx").toString();
	}

	@GetMapping(value = "/reviewBycomRef")
	@ResponseBody
	public List<Review> reviewBycomRef(@RequestParam int comref) {
		return reviewService.selectReviewByComRef(comref);

	}

}
