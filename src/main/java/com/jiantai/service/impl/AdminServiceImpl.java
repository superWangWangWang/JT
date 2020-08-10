package com.jiantai.service.impl;

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
     * @param id
     * @return
     */

    public List<CompanyInfo> getCompanyInfoById(String id) {
        List companyList = adminDao.getCompanyInfoById(id);
        return companyList;
    }
    /**
     * 查询所有的公司信息
     * @return
     */
    public List getAllCompanyInfo() {
        List companyList = adminDao.getAllCompanyInfo();
        return companyList;
    }

    /**
     * 根据公司id获取日志
     * @param cid
     * @return
     */
    @Override
    public List<JTLog> getLogByCid(Integer cid) {
        return adminDao.getLogByCid(cid);
    }

    /**
     * 添加日志
     * @param log
     */
    @Override
    public void addLog(JTLog log) {
        adminDao.addLog(log);
    }

    /**
     * 搜索物料记录
     * @param date
     * @param company
     * @param material
     * @return
     */
    @Override
    public List<JTDeclareRecord> searchDeclareRecords(String date, String company, String material) {
        if (!StringUtils.isNotBlank(date)){
            date = null;
        }
        if (!StringUtils.isNotBlank(company)){
            company = null;
        }
        if (!StringUtils.isNotBlank(material)){
            material = null;
        }
        return adminDao.searchDeclareRecords(date, company, material);
    }
    @Override
    public List<Material> findMaterials() {
        return adminDao.findMaterials();
    }

    /**
     * 根据用户名查询公司
     * @param name
     * @return
     */
    @Override
    public List<CompanyInfo> getCompanyByUserName(String name) {
        return adminDao.getCompanyByUserName(name);
    }

    /**
     * 添加公司登录账号
     * @param name
     * @param pwd
     */
    @Override
    public void addCompany(String name, String pwd) {
        adminDao.addCompany(name,pwd);
    }

    /**
     * 查询所有的日志记录
     * @return
     */
    @Override
    public List<JTLog> getAllLog() {
        return adminDao.getAllLog();
    }
}
