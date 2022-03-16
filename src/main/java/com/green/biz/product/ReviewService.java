package com.green.biz.product;

import java.util.List;

import com.green.biz.dto.ReviewVO;

public interface ReviewService {

	// ���� �ۼ�
	void insertReview(ReviewVO vo);

	// ���� ���
	List<ReviewVO> listReview(ReviewVO vo);

	// �����Ͽ��� �׸� ����
	void deleteReview(int rseq);

	// �����Ͽ��� �׸� ������Ʈ
	public void updateReview(ReviewVO vo);

}