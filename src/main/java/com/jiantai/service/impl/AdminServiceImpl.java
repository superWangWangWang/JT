package com.jiantai.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.dao.AdminDao;
import com.jiantai.entity.*;
import com.jiantai.service.AdminService;
import com.jiantai.vo.VO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param id
     * @return
     */

    public List<CompanyInfo> getCompanyInfoById(String id) {
        List companyList = adminDao.getCompanyInfoById(id);
        return companyList;
    }

    /**
     * 查询所有的公司信息
     *
     * @return
     */
    public List getAllCompanyInfo() {
        List companyList = adminDao.getAllCompanyInfo();
        return companyList;
    }

    /**
     * 根据公司id获取日志
     *
     * @param cid
     * @return
     */
    @Override
    public List<JTLog> getLogByCid(Integer cid) {
        return adminDao.getLogByCid(cid);
    }

    /**
     * 添加日志
     *
     * @param log
     */
    @Override
    public void addLog(JTLog log) {
        adminDao.addLog(log);
    }

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
        String[] title = new String[8];//这个数组用于excel表头
        title[0] = "id";
        title[1] = "所属公司";
        title[2] = "录入时间";
        title[3] = "物料名称";
        title[4] = "物料单位";
        title[5] = "物料成分";
        title[6] = "成分含量";
        title[7] = "当月用量";
        for (JTDeclareRecord r : records) {
            String[] content = new String[8];//excel内容
            System.out.println(r);
            content[0] = r.getId() + "";
            content[1] = r.getCompany();
            content[2] = r.getDatetime();
            content[3] = r.getMaterial();
            content[4] = r.getUnit();
            content[5] = r.getElement();
            content[6] = r.getContent();
            content[7] = r.getDosage();
            exportInfos.add(content);
        }

        map.put("title", title);
        map.put("result", records);

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

}
