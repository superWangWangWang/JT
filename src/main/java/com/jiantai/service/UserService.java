package com.jiantai.service;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTLog;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	public void changePassword(CompanyInfo companyInfo, String new_password) throws Exception;

	public CompanyInfo companyLogin(String username);

	public CompanyInfo findCompanyById(String id);
	void addLog(JTLog log);
}
