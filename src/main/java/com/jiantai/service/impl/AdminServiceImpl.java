package com.jiantai.service.impl;

import com.jiantai.dao.AdminDao;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import com.jiantai.entity.MaterialsUsed;
import com.jiantai.entity.User;
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
}
