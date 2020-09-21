package com.jiantai.service;

import com.jiantai.entity.*;

import java.util.List;

public interface AdminService {
    List<User> getAllCompanyInfo();
    List<User> getCompanyInfoById(String id);
    void updateCompanyEnableById(User user);
    void updateCompanyInfo(String field,String value,String id);
    List getCompanyInfoByName(String name);
    void updateCompanyModifyById(User user);
    List<MaterialsUsed> getMaterialsUsedByTime(String used_time,String c_id,String mid);
    List<JTLog> getAllLog();
    List<User> getAllCompanyInfoExcludeSuper();
    List<Material> getMaterials();
    List<Msds> getMsds(String c_id);
    List<Msds> getMsdsByIdAndCid(String id,String c_id);
    List<Product> getOutput(String time,String cid);
}
