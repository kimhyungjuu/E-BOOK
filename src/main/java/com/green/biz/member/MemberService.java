package com.green.biz.member;

import java.util.List;

import com.green.biz.dto.MemberVO;

public interface MemberService {
	// ȸ�� ��� ��ȸ
	public List<MemberVO> listMember(String name);
}
