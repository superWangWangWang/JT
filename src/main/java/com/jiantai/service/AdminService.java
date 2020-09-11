package com.jiantai.service;

import com.jiantai.entity.*;
import com.jiantai.vo.VO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<User> getAllCompanyInfo();
    List<User> getCompanyInfoById(String id);
    void updateCompanyEnableById(User user);
    void updateCompanyInfo(String field,String value,String id);
    List getCompanyInfoByName(String name);
    void updateCompanyModifyById(User user);
    //List<JTLog> getLogByCid(Integer cid);
    //void addLog(JTLog log);
    List<JTDeclareRecord> searchDeclareRecords(String date, String company, String material);
    List<Material> findMaterials();

    Map<String, Object> searchExprotDeclareRecords(String date, String company, String material);

    List<CompanyInfo> findExistPlanName();

    List<CompanyInfo> findExistProductName();

    List<JTMsdsUpload> findMsdsUpload(String company, String material);

    List<JtMaterialEvidence> findEvidenceUpload(String company, String datetime);

    JTMsdsUpload findMsdsUploadById(String id);

    JtMaterialEvidence findJtMaterialEvidenceById(String id);
    List<CompanyInfo> getCompanyByUserName(String name);
    void addCompany(String name,String pwd);
    List<JTLog> getAllLog();
    List<User> getAllCompanyInfoExcludeSuper();
}
