//회원정보 관련 처리 컨트롤러
package global.sesoc.practice5.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import global.sesoc.practice5.dao.MemberDAO;
import global.sesoc.practice5.vo.Member;

@Controller
@RequestMapping("member")	// 현재 콘트롤러의 모든 경로 앞에 member/가 붙음
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberDAO dao;
	
	// 가입폼으로 이동
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String join() {
		
		return "memberjsp/joinForm";	
	}
	
	// 가입처리
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String join(Member member) {
		logger.info("전달된 회원가입 정보 : {}", member);
		dao.insert(member);
		return "redirect:/";	
	}
	
	// 로그인폼으로 이동
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		
		return "memberjsp/loginForm";
	}
	
	// 로그인처리
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(Member member, HttpSession session) {
		// DB에서 ID로 회원정보를 조회해온다.
		Member member2 = dao.getMember(member.getId());
		// 결과가 있는지, 비밀번호가 맞는지 확인 후 세션에 ID와 이름 저장
		if (member2 != null && member2.getPassword().equals(member.getPassword())) {
			session.setAttribute("loginId", member2.getId());
			session.setAttribute("loginName", member2.getName());
			return "redirect:/";
		}
		return "redirect:login";	
	}
	
	// 로그아웃
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.removeAttribute("loginName");
		return "redirect:/";	
	}
	
	// ID중복확인 페이지로 이동
	@RequestMapping(value = "idcheck", method = RequestMethod.GET)
	public String idcheck() {
		
		return "memberjsp/idcheck";	
	}
	
	// ID중복 확인
	@RequestMapping(value = "idcheck", method = RequestMethod.POST)
	public String idcheck(String searchId, Model model) {
		logger.info("검색할 ID : {}", searchId);
		
		Member member = dao.getMember(searchId);
		model.addAttribute("member", member);
		model.addAttribute("searchId", searchId);
		
		return "memberjsp/idcheck";	
	}
	
	// 개인정보 수정 페이지로 이동
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(HttpSession session, Model m) {
		// 세션에서 ID를 읽기
		String id = (String) session.getAttribute("loginId");

		// ID가 null이 아니면 DB에서 회원정보를 조회
		Member member = dao.getMember(id);
		
		// 회원정보를 Model에 저장하고 JSP로 포워딩 
		m.addAttribute("member", member);
		
		return "memberjsp/updateForm";	
	}
	
	// 개인정보 수정 처리 
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpSession session, Member member) {
		String id = (String) session.getAttribute("loginId");
		
		member.setId(id);
		
		//member객체를 dao로 보내서 테이블의 정보 수정
		dao.updateMember(member);
		
		session.setAttribute("loginName", member.getName());
		
		return "redirect:/";	
	}
}
