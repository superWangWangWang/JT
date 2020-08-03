package com.jiantai.service;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {
    List<CompanyInfo> getAllCompanyInfo();
    List<CompanyInfo> getCompanyInfoById(String id);
    void updateCompanyEnableById(CompanyInfo companyInfo);
    void updateCompanyInfoById(CompanyInfo companyInfo);
    List getCompanyInfoByName(String name);
    void updateCompanyRightById(CompanyInfo companyInfo);
    List<JTLog> getLogByCid(Integer cid);
}
