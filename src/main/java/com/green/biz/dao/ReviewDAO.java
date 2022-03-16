package com.green.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.dto.ReviewVO;

@Repository
public class ReviewDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// ���� �ۼ�
	public void insertReview(ReviewVO rv) {
		
		mybatis.insert("mappings.review-mapping.insertReview", rv);
	}
	
	// ���� ���
	public List<ReviewVO> listReview(ReviewVO rv) {
		
		return mybatis.selectList("mappings.review-mapping.listReview", rv);
	}
	
	// �����Ͽ��� �׸� ����
	public void deleteReview(int rseq) {
		
		mybatis.delete("mappings.review-mapping.deleteReview", rseq);
	}
	
	// �����Ͽ��� �׸� ������Ʈ
	public void updateReview(ReviewVO rv) {
		
		mybatis.delete("mappings.review-mapping.updateReview", rv);
	}
}
