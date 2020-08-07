package com.jiantai.service;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
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
    void addLog(JTLog log);
    List<JTDeclareRecord> searchDeclareRecords(String date, String company, String material);
    List<Material> findMaterials();
    List<CompanyInfo> getCompanyByUserName(String name);
    void addCompany(String name,String pwd);
}
