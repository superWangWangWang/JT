package com.jiantai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiantai.dao.UserDao;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;


	@Override
	public void changePassword(CompanyInfo companyInfo, String new_password) throws Exception {
		userDao.changePassword(companyInfo,new_password);
	}

	@Override
	public CompanyInfo companyLogin(String username) {
		return userDao.companyLogin(username);
	}

	@Override
	public CompanyInfo findCompanyById(String id) {
		return userDao.findCompanyById(id);
	}

}
