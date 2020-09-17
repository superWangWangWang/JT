package com.jiantai.service;

import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import com.jiantai.entity.MaterialsUsed;
import com.jiantai.entity.User;

import java.util.List;

public interface AdminService {
    List<User> getAllCompanyInfo();
    List<User> getCompanyInfoById(String id);
    void updateCompanyEnableById(User user);
    void updateCompanyInfo(String field,String value,String id);
    List getCompanyInfoByName(String name);
    void updateCompanyModifyById(User user);
    List<MaterialsUsed> getMaterialsUsedByTime(String used_time);
    List<JTLog> getAllLog();
    List<User> getAllCompanyInfoExcludeSuper();
    List<Material> getMaterials();
}
