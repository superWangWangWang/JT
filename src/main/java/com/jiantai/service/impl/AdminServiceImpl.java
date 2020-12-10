package com.jiantai.service.impl;

import com.jiantai.dao.AdminDao;
import com.jiantai.entity.*;
import com.jiantai.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    /**
     * 添加公司账户
     * @param user_name
     * @param password
     * @param company_name
     * @param company_short_name
     */
    @Override
    public void companyAdd(String user_name, String password, String company_name, String company_short_name) {
        adminDao.companyAdd(user_name,password,company_name,company_short_name);
    }

    @Override
    public List getCompanyInfoByName(String name) {
        List list = adminDao.getCompanyInfoByName(name);
        return list;
    }

    /**
     * 管理员修改公司的信息
     * @param field
     * @param value
     * @param id
     */
    @Override
    public void updateCompanyInfo(String field, String value, String id) {
        adminDao.updateCompanyInfo(field,value,id);
    }

    /**
     * 根据公司id更新公司 是否有效
     *
     * @param user
     */
    public void updateCompanyEnableById(User user) {
        adminDao.updateCompanyEnableById(user);
    }

    /**
     * 更新公司的跨月修改权限
     * @param user
     */
    @Override
    public void updateCompanyModifyById(User user) {
        adminDao.updateCompanyModifyById(user);
    }

    /**
     * 根据公司id查询公司信息
     *
     * @param id
     * @return
     */

    public List<User> getCompanyInfoById(String id) {
        return adminDao.getCompanyInfoById(id);
    }

    /**
     * 查询所有的公司信息
     *
     * @return
     */
    public List<User> getAllCompanyInfo() {
        return adminDao.getAllCompanyInfo();
    }

    /**
     * 询所有的公司信息除了超级管理员 -- state不为2
     * @return
     */
    @Override
    public List<User> getAllCompanyInfoExcludeSuper() {
        return adminDao.getAllCompanyInfoExcludeSuper();
    }


    /**
     * 查询所有的日志记录
     *
     * @return
     */
    @Override
    public List<JTLog> getAllLog() {
        return adminDao.getAllLog();
    }

    /**
     * 根据申报时间查询物料表
     * @param used_time
     * @return
     */
    @Override
    public List<MaterialsUsed> getMaterialsUsedByTime(String used_time,String c_id,String mid) {
        return adminDao.getMaterialsUsedByTime(used_time,c_id,mid);
    }

    /**
     * 查询物料列表
     * @return
     */
    @Override
    public List<Material> getMaterials() {
        return adminDao.getMaterials();
    }

    /**
     * 查询msds信息
     * @param c_id
     * @return
     */
    @Override
    public List<Msds> getMsds(String c_id) {
        return adminDao.getMsds(c_id);
    }

    /**
     * 根据公司id和自身id 查询msds的src用于管理员下载
     * @param id
     * @param c_id
     * @return
     */
    @Override
    public List<Msds> getMsdsByIdAndCid(String id, String c_id) {
        return adminDao.getMsdsByIdAndCid(id,c_id);
    }

    /**
     * 根据时间或公司id查询产品输出
     * @param time
     * @param cid
     * @return
     */
    @Override
    public List<Product> getOutput(String time, String cid) {
        return adminDao.getOutput(time,cid);
    }

    /**
     * 根据公司id查询生产设备
     * @param cid
     * @param type
     * @return
     */
    @Override
    public List<Equipment> getEquipment(String cid, String type) {
        return adminDao.getEquipment(cid,type);
    }

    /**
     * 查询上传的物料凭证
     * @param cid
     * @param time
     * @return
     */
    @Override
    public List<MaterielEvidence> getMaterielEvidence(String cid, String time) {
        return adminDao.getMaterielEvidence(cid,time);
    }

    /**
     * 根据公司id和物料凭证id查询 物料凭证src用于管理员下载
     * @param cid
     * @param id
     * @return
     */
    @Override
    public List<MaterielEvidence> getMaterielEvidenceSrc(String cid, String id) {
        return adminDao.getMaterielEvidenceSrc(cid,id);
    }

    /**
     * 获取设备保养记录列表
     * @param time
     * @param cid
     * @return
     */
    @Override
    public List<EquipmentMaintenance> getEquipmentMaintenanceList(String time, String cid) {
        return adminDao.getEquipmentMaintenanceList(time,cid);
    }

    /**
     * 获取设备保养记录的src用于管理员下载
     * @param cid
     * @param id
     * @return
     */
    @Override
    public List<EquipmentMaintenance> getEquipmentMaintenanceSrc(String cid, String id) {
        return adminDao.getEquipmentMaintenanceSrc(cid,id);
    }

    /**
     * 获取上个月的物料申报公司名列表
     * @param time
     * @return
     */
    @Override
    public List<String> getMaterialsUsedCompanyNameLastMonth(String time) {
        return adminDao.getMaterialsUsedCompanyNameLastMonth(time);
    }

    /**
     * 获取上个月的物料佐证上传公司名列表
     * @param time
     * @return
     */
    @Override
    public List<String> getMaterielsEvidenceCompanyNameLastMonth(String time) {
        return adminDao.getMaterielsEvidenceCompanyNameLastMonth(time);
    }

    /**
     * 获取上一个月的提交产品产量的公司名列表
     * @param time
     * @return
     */
    @Override
    public List<String> getProductsOutputCompanyNameLastMonth(String time) {
        return adminDao.getProductsOutputCompanyNameLastMonth(time);
    }

    /**
     * 获取每月设备保养记录的企业名列表
     * @param time
     * @return
     */
    @Override
    public List<String> getEquipmentMaintenanceCompanyNameLastMonth(String time) {
        return adminDao.getEquipmentMaintenanceCompanyNameLastMonth(time);
    }

    /**
     * 获取生产设备提交的企业名列表
     * @return
     */
    @Override
    public List<String> getEquipmentCompanyName() {
        return adminDao.getEquipmentCompanyName();
    }

    /**
     * 获取已上传msds的公司名列表
     * @return
     */
    @Override
    public List<String> getMSDSCompanyName() {
        return adminDao.getMSDSCompanyName();
    }

    @Override
    public void addMaterial(Material material) {
        adminDao.addMaterial(material);
    }
}
