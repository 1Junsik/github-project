package global.sesoc.practice5.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import global.sesoc.practice5.vo.Member;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession session;
	
	//회원가입 처리
	public void insert(Member member) {
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		mapper.insert(member);
	}
	
	//회원정보 검색
	public Member getMember(String id) {
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		Member member = null;
		member = mapper.getMember(id);
		return member;
	}	
	
	public void updateMember(Member member) {
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		mapper.updateMember(member);
	}
}
