package com.green.biz.product;

import java.util.List;

import com.green.biz.dto.ProductVO;

import utils.Criteria;

public interface ProductService {

	List<ProductVO> getNewProductList();

	List<ProductVO> getBestProductList();

	List<ProductVO> getFreeProductList();
	
	ProductVO getProduct(ProductVO vo);

	List<ProductVO> getProductListByKind(ProductVO vo);

	// ��ü ��ǰ�� ���� ��ȸ
	int countProductList(String title);

	// ��ǰ ��� ��ȸ
	List<ProductVO> listProduct(String title);

	// �������� ��ǰ��� ��ȸ
	List<ProductVO> getListWithPaging(Criteria criteria, String title);

}