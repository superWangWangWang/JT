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
    List<Equipment> getEquipment(String cid,String type);
    List<MaterielEvidence> getMaterielEvidence(String cid,String time);
    List<MaterielEvidence> getMaterielEvidenceSrc(String cid,String id);
    List<EquipmentMaintenance> getEquipmentMaintenanceList(String time,String cid);
    List<EquipmentMaintenance> getEquipmentMaintenanceSrc(String cid,String id);
    void companyAdd(String user_name,String password,String company_name,String company_short_name);
    List<String> getMaterialsUsedCompanyNameLastMonth(String time);
    List<String> getMaterielsEvidenceCompanyNameLastMonth(String time);
    List<String> getProductsOutputCompanyNameLastMonth(String time);
    List<String> getEquipmentMaintenanceCompanyNameLastMonth(String time);
    List<String> getEquipmentCompanyName();
    List<String> getMSDSCompanyName();
}
