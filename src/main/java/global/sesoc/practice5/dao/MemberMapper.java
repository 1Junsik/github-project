package global.sesoc.practice5.dao;

import global.sesoc.practice5.vo.Member;

public interface MemberMapper {
	public void insert(Member member);
	public Member getMember(String id);
	public void updateMember(Member member);
}
