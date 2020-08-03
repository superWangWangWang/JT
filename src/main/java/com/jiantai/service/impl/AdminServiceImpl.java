package com.jiantai.service.impl;

import com.jiantai.dao.AdminDao;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTLog;
import com.jiantai.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public List getCompanyInfoByName(String name) {
        List list = adminDao.getCompanyInfoByName(name);
        return list;
    }

    @Override
    public void updateCompanyInfoById(CompanyInfo companyInfo) {
        adminDao.updateCompanyInfoById(companyInfo);
    }

    /**
     * 根据公司id更新公司 是否有效
     * @param companyInfo
     */
    public void updateCompanyEnableById(CompanyInfo companyInfo) {
        adminDao.updateCompanyEnableById(companyInfo);
    }

    @Override
    public void updateCompanyRightById(CompanyInfo companyInfo) {
        adminDao.updateCompanyRightById(companyInfo);
    }

    /**
     * 根据公司id查询公司信息
     * @param id
     * @return
     */

    public List<CompanyInfo> getCompanyInfoById(String id) {
        List companyList = adminDao.getCompanyInfoById(id);
        return companyList;
    }
    /**
     * 查询所有的公司信息
     * @return
     */
    public List getAllCompanyInfo() {
        List companyList = adminDao.getAllCompanyInfo();
        return companyList;
    }

    @Override
    public List<JTLog> getLogByCid(Integer cid) {
        return adminDao.getLogByCid(cid);
    }
}
