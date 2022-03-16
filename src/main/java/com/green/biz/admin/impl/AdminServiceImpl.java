package com.green.biz.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.admin.AdminService;
import com.green.biz.dao.AdminDAO;
import com.green.biz.dto.ManagerVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDao;
	
	@Override
	public int managerCheck(ManagerVO vo) {
		int result = -1;
		
		// worker ���̺��� id�� �������� pwd�� ��ȸ
		String pwd_in_db = adminDao.managerCheck(vo.getId());
		
		// ����� �Է� pwd�� ���̺��� ��ȸ�� pwd�� ��
		if (pwd_in_db == null) { 
			result = -1;
		} else if (vo.getPwd().equals(pwd_in_db)) {
			result = 1;
		} else {
			result = 0;
		}
		
		return result;
	}

	@Override
	public ManagerVO getManager(String id) {
		
		return adminDao.getManager(id);
	}

}
