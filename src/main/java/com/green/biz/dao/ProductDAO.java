package com.green.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.dto.ProductVO;

import utils.Criteria;

@Repository		// ������ ��ü�� ���
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<ProductVO> getNewProductList() {
		
		return mybatis.selectList("mappings.product-mapping.getNewProductList");
	}
	
	public List<ProductVO> getBestProductList() {
		
		return mybatis.selectList("mappings.product-mapping.getBestProductList");
	}
	
	public List<ProductVO> getFreeProductList() {
		
		return mybatis.selectList("mappings.product-mapping.getFreeProductList");
	}
	
	public ProductVO getProduct(ProductVO vo) {
		
		return mybatis.selectOne("mappings.product-mapping.getProduct", vo);
	}
	
	public List<ProductVO> getProductListByKind(ProductVO vo) {
		
		return mybatis.selectList("mappings.product-mapping.getProductListByKind", vo);
	}
	
	// ��ü ��ǰ�� ���� ��ȸ
	public int countProductList(String title) {
		
		return mybatis.selectOne("mappings.product-mapping.countProductList", title);
	}
	
	// ��ǰ ��� ��ȸ
	public List<ProductVO> listProduct(String title) {
		
		return mybatis.selectList("mappings.product-mapping.listProduct", title);
	}
	
	// �������� ��ǰ��� ��ȸ
	public List<ProductVO> getListWithPaging(Criteria criteria, String title) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("title", title);
		
		return mybatis.selectList("mappings.product-mapping.listWithPaging", map);
	}
	
}











