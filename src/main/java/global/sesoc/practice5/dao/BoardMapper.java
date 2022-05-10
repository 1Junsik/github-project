package global.sesoc.practice5.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import global.sesoc.practice5.vo.Board;
import global.sesoc.practice5.vo.Reply;


public interface BoardMapper {
	// 글쓰기 
	void insertBoard(Board board);
	// 글목록
	ArrayList<Board> listBoard(String searchText, RowBounds rb);
	// 글읽기
	Board getBoard(int boardnum);
	// 조회수 1증가 
	void addhits(int boardnum);
	// 글수정
	void updateBoard(Board board);
	// 글삭제
	void deleteBoard(Board board);
	// 리플저장 
	void insertReply(Reply reply);
	// 리플읽기
	ArrayList<Reply> listReply(int boardnum);
	// 글개수
	int getTotal(String searchText);
}
