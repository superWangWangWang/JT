package com.jiantai.service.impl;

import com.jiantai.dao.AdminDao;
import com.jiantai.entity.*;
import com.jiantai.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.jiantai.dao.AdminDao;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import com.jiantai.service.AdminService;
import org.apache.commons.lang.StringUtils;
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
//    /**
//     * 根据公司id获取日志
//     *
//     * @param cid
//     * @return
//     */
//    @Override
//    public List<JTLog> getLogByCid(Integer cid) {
//        return adminDao.getLogByCid(cid);
//    }

//    /**
//     * 添加日志
//     *
//     * @param log
//     */
//    @Override
//    public void addLog(JTLog log) {
//        adminDao.addLog(log);
//    }

    /**
     * 搜索物料记录
     *
     * @param date
     * @param company
     * @param material
     * @return
     */
    @Override
    public List<JTDeclareRecord> searchDeclareRecords(String date, String company, String material) {
        if (!StringUtils.isNotBlank(date)) {
            date = null;
        }
        if (!StringUtils.isNotBlank(company)) {
            company = null;
        }
        if (!StringUtils.isNotBlank(material)) {
            material = null;
        }
        return adminDao.searchDeclareRecords(date, company, material);
    }

    @Override
    public List<Material> findMaterials() {
        return adminDao.findMaterials();
    }


    @Override
    public Map<String, Object> searchExprotDeclareRecords(String date, String company, String material) {

        if (!StringUtils.isNotBlank(date)) {
            date = null;
        }
        if (!StringUtils.isNotBlank(company)) {
            company = null;
        }
        if (!StringUtils.isNotBlank(material)) {
            material = null;
        }
        List<JTDeclareRecord> records = adminDao.searchDeclareRecords(date, company, material);

        Map<String, Object> map = new HashMap<>();
        List<String[]> exportInfos = new ArrayList<>();
        String[] title = new String[3];//这个数组用于excel表头
        /*title[0] = "id";
        title[1] = "所属公司";
        title[2] = "录入时间";*/
        title[0] = "物料名称";
        title[1] = "物料单位";
        title[2] = "物料用量";
        double count = 0;
        for (JTDeclareRecord r : records) {
            count += Double.parseDouble(r.getDosage());
            String[] content = new String[3];//excel内容
            //content[0] = r.getId() + "";
            //content[1] = r.getCompany();
            //content[2] = r.getDatetime();
            content[0] = r.getMaterial();
            content[1] = r.getUnit();
            content[2] = r.getDosage();
            exportInfos.add(content);
        }
        String[] end = new String[3];//用于excel表结尾的总计算
        end[0] = "合计";
        end[1] = "";
        end[2] = count + "";
        exportInfos.add(end);


        map.put("title", title);
        map.put("result", exportInfos);

        return map;
    }

    /**
     * 查询存在palnname的用户
     */
    @Override
    public List<CompanyInfo> findExistPlanName() {
        return adminDao.findExistPlanName();
    }

    /**
     * 查询存在productname的用户
     */
    @Override
    public List<CompanyInfo> findExistProductName() {
        return adminDao.findExistProductName();
    }


    /**
     * 查询上传的msds
     */
    @Override
    public List<JTMsdsUpload> findMsdsUpload(String company, String material) {
        if (!StringUtils.isNotBlank(company)) {
            company = null;
        }
        if (!StringUtils.isNotBlank(material)) {
            material = null;
        }
        return adminDao.findMsdsUpload(company, material);
    }

    /**
     * 根据条件查询 佐证
     */
    @Override
    public List<JtMaterialEvidence> findEvidenceUpload(String company, String datetime) {
        if (!StringUtils.isNotBlank(company)) {
            company = null;
        }
        if (!StringUtils.isNotBlank(datetime)) {
            datetime = null;
        }
        return adminDao.findEvidenceUpload(company, datetime);
    }

    /**
     * 根据id查询MSDS
     */
    @Override
    public JTMsdsUpload findMsdsUploadById(String id) {
        return adminDao.findMsdsUploadById(id);
    }

    /**
     * 根据id查询佐证
     */
    @Override
    public JtMaterialEvidence findJtMaterialEvidenceById(String id) {
        return adminDao.findJtMaterialEvidenceById(id);
    }


    /**
     * 根据用户名查询公司
     *
     * @param name
     * @return
     */
    @Override
    public List<CompanyInfo> getCompanyByUserName(String name) {
        return adminDao.getCompanyByUserName(name);
    }

    /**
     * 添加公司登录账号
     *
     * @param name
     * @param pwd
     */
    @Override
    public void addCompany(String name, String pwd) {
        adminDao.addCompany(name, pwd);
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
}
