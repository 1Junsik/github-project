//회원정보 관련 처리 컨트롤러
package global.sesoc.practice5.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import global.sesoc.practice5.dao.BoardDAO;
import global.sesoc.practice5.dao.MemberDAO;
import global.sesoc.practice5.util.FileService;
import global.sesoc.practice5.util.PageNavigator;
import global.sesoc.practice5.vo.Board;
import global.sesoc.practice5.vo.Member;
import global.sesoc.practice5.vo.Reply;

@Controller
@RequestMapping("board")	// 현재 콘트롤러의 모든 경로 앞에 member/가 붙음
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	final int countPerPage = 10;		// 페이지당 글 수
	final int pagePerGroup = 5;			// 페이지 이동 링크를 표시할 페이지 수
	final String uploadPath = "/boardfile";	// 파일 업로드 경로 
	
	@Autowired
	BoardDAO dao;
	
	// 글목록 이동
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model
			, @RequestParam(value="page", defaultValue="1") int page
			, @RequestParam(value="searchText", defaultValue="") String searchText) {
		logger.info("검색어:{}", searchText);
		
		int total = dao.getTotal(searchText);
		
		// 페이지 계산을 위한 객체 생성
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		// 검색어와 시작 위치, 페이지당 글 수를 전달하여 목록 읽기
		ArrayList<Board> boardlist = dao.list(searchText, navi.getStartRecord(), navi.getCountPerPage());
		
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);
		return "boardjsp/list";	
	}
	
	// 글쓰기 폼 이동
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String write() {
		
		return "boardjsp/write";	
	}
	
	// 글 저장
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(Board board, HttpSession session, MultipartFile upload) {
		logger.info("파일정보:{}", upload.getContentType());
		logger.info("파일정보:{}", upload.getName());
		logger.info("파일정보:{}", upload.getOriginalFilename());
		logger.info("파일정보:{}", upload.getSize());
		logger.info("파일정보:{}", upload.isEmpty());
		
		String id = (String) session.getAttribute("loginId");
		board.setId(id);
		
		//첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅
		if (!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}
		
		dao.write(board);
		return "redirect:list";	
	}
	
	// 글 읽기
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public String write(int boardnum, Model model) {
		// 조회수 1증가
		dao.addhits(boardnum);
		// 해당 번호의 글정보 읽기
		Board board = dao.getBoard(boardnum);
		
		if (board == null) {
			return "redirect:list";
		}
		
		// 결과가 있으면 모델에 글 정보 저장하고 JSP로 포워딩
		model.addAttribute("board", board);
		
		// 이 글에 달린 댓글 목록도 가져감
		ArrayList<Reply> replyList = dao.listReply(boardnum); 
		model.addAttribute("replyList", replyList);
		return "boardjsp/read";	
	}
	
	/**
	 * 파일 다운로드
	 * @param boardnum 파일이 첨부된 글 번호
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String fileDownload(int boardnum, Model model, HttpServletResponse response) {
		Board board = dao.getBoard(boardnum);
		
		//원래의 파일명
		String originalfile = new String(board.getOriginalfile());
		try {
			response.setHeader("Content-Disposition", " attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//저장된 파일 경로  "/boardfile/20220420.jpg"
		String fullPath = uploadPath + "/" + board.getSavedfile();
		
		//서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
		FileInputStream filein = null;
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			
			//Spring의 파일 관련 유틸 이용하여 출력
			FileCopyUtils.copy(filein, fileout);
			
			filein.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	// 글 수정 폼으로 이동
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(int boardnum, Model model) {
		
		// 해당 번호의 글정보 읽기
		Board board = dao.getBoard(boardnum);
		
		if (board == null) {
			return "redirect:list";
		}

		model.addAttribute("board", board);
		return "boardjsp/edit";	
	}
	
	// 글 수정 처리 
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(Board board, HttpSession session, MultipartFile upload) {
		String id = (String) session.getAttribute("loginId");
		Board oldBoard = dao.getBoard(board.getBoardnum());
		if (oldBoard == null || !oldBoard.getId().equals(id)) {
			return "redirect:list";
		}
		board.setId(id);
		
		//수정 시 새로 첨부한 파일이 있으면 기존 파일을 삭제하고 새로 업로드
		if (!upload.isEmpty()) {
			//기존 글에 첨부된 파일의 실제 저장된 이름
			String savedfile = oldBoard.getSavedfile();
			//기존 파일이 있으면 삭제
			if (savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + savedfile);
			}
			
			//새로 업로드한 파일 저장
			savedfile = FileService.saveFile(upload, uploadPath);
			
			//수정 정보에 새로 저장된 파일명과 원래의 파일명 저장
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}
		
		dao.edit(board);
		return "redirect:list";	
	}

	// 게시글 삭제
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(Board board, HttpSession session) {
		String id = (String) session.getAttribute("loginId");
		board.setId(id);
		
		String savedfile = dao.getBoard(board.getBoardnum()).getSavedfile();
		
		dao.delete(board);
		
		//글 삭제 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		return "redirect:list";	
	}
	
	// 리플 저장
	@RequestMapping(value = "replyWrite", method = RequestMethod.POST)
	public String edit(Reply reply, HttpSession session) {
		String id = (String) session.getAttribute("loginId");
		reply.setId(id);
		// dao로 전달하여 DB에 리플정보 저장 
		logger.info("{}", reply);
		dao.insertReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
}
