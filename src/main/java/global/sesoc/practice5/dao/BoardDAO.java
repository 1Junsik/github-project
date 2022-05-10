package global.sesoc.practice5.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import global.sesoc.practice5.vo.Board;
import global.sesoc.practice5.vo.Member;
import global.sesoc.practice5.vo.Reply;

@Repository
public class BoardDAO {
	
	@Autowired
	SqlSession session;
	
	// 글쓰기
	public void write(Board board) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		mapper.insertBoard(board);
	}
	
	// 글목록
	public ArrayList<Board> list(String searchText, int startRecord, int countPerPage) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		// 전체 검색 결과 중 읽을 시작위치와 개수
		RowBounds rb = new RowBounds(startRecord, countPerPage);
		
		// 검색어와 읽을 범위 전달
		ArrayList<Board> result = mapper.listBoard(searchText, rb);
		return result;
	}
	
	// 글개수
	public int getTotal(String searchText) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		return mapper.getTotal(searchText);
	}

	// 글읽기
	public Board getBoard(int boardnum) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		Board board = mapper.getBoard(boardnum);
		return board;
	}

	// 조회수 1증가 
	public void addhits(int boardnum) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		mapper.addhits(boardnum);
	}
	
	// 글 수정 
	public void edit(Board board) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		mapper.updateBoard(board);
	}
	
	// 글 삭제 
	public void delete(Board board) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		mapper.deleteBoard(board);
	}
	
	// 리플 저장
	public void insertReply(Reply reply) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		mapper.insertReply(reply);
	}

	public ArrayList<Reply> listReply(int boardnum) {
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		ArrayList<Reply> replylist = mapper.listReply(boardnum);
		return replylist;
	}

}
