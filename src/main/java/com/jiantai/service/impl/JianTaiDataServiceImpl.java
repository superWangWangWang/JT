package com.jiantai.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jiantai.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiantai.dao.JianTaiDataDao;
import com.jiantai.service.JianTaiDataService;

@Service
public class JianTaiDataServiceImpl implements JianTaiDataService {
	@Autowired
	private JianTaiDataDao jianTaiDataDao;



	@Override
	public List<Material> findDataToJTMaterialList() {
		return jianTaiDataDao.findDataToJTMaterialList();
	}

	public void addDeclareInfoToJTDeclareInfo(Map<String, Object> map) {
		
		jianTaiDataDao.addDeclareInfoToJTDeclareInfo(map.get("now"), map.get("date"), map.get("userName"), map.get("material"),map.get("unit"),map.get("element"),map.get("content"),map.get("dosage"));
		
	}

	@Override
	public void setCompanyInfoById(String id, Map<String, String> map) {
		jianTaiDataDao.setCompanyInfoById(id, map.get("联系地址"), map.get("公司固话"), map.get("公司传真"), map.get("开业时间"), map.get("企业人数"), map.get("统一社会信用代码"), map.get("法人代表"), map.get("联系人"), map.get("联系人手机号"), map.get("产品类型"), map.get("园区位置"), map.get("备注"));
	}

	@Override
	public List<JTDeclareRecord> findRecordById(String username, String date) {
		return jianTaiDataDao.findRecordById(username, date);
	}

	@Override
	public void delRecordById(int id){
		jianTaiDataDao.delRecordById(id);
	}

	@Override
	public void updateDeclareInfoById(Map<String, Object> map) {
		jianTaiDataDao.updateDeclareInfoById(map.get("material"), map.get("unit"), map.get("element"), map.get("content"), map.get("dosage"), map.get("id"));
		
	}

	@Override
	public void setCompanyInfoPlanNameById(int id, String filename) {
		jianTaiDataDao.setCompanyInfoPlanNameById(id, filename);
		
	}

	@Override
	public CompanyInfo findCompanyInfoById(int id) {
		return jianTaiDataDao.findCompanyInfoById(id);
	}


	@Override
	public void setFilenameByMaterial(String materialName, String filename) {
		jianTaiDataDao.setFilenameByMaterial(materialName, filename);
		
	}

	@Override
	public JtMaterialEvidence findEvidenceByCompanyName(String companyName, String date) {
		return jianTaiDataDao.findEvidenceByCompanyName(companyName, date);
	}

	

	@Override
	public void updateMaterialEvidenceById(int id, String filename){
		jianTaiDataDao.updateMaterialEvidenceById(id, filename);
	}
	
	@Override
	public void addMaterialEvidenceById(String filename, String company, String date) {
		jianTaiDataDao.addMaterialEvidenceById(filename, company, date);
	}

	@Override
	public List<JTProductRecord> findProducts() {
		return jianTaiDataDao.findProducts();
	}

	@Override
	public List<JTProductRecord> findProductRecordByDate(String company, String date) {
		return jianTaiDataDao.findProductRecordByDate(company, date);
	}

	@Override
	public JTProductRecord findUnitByCompanyAndDate(String date, String product, String name) {
		return jianTaiDataDao.findUnitByCompanyAndDate(date, product, name);
	}

	@Override
	public void setYieldById(Integer id, String unit) {
		jianTaiDataDao.setYieldById(id, unit);
	}

	@Override
	public void addProductRecord(String product, Date now, String datetime, String yield, String company) {
		jianTaiDataDao.addProductRecord(product, now, datetime, yield, company);
	}

	@Override
	public void setCompanyInfoProductNameById(int id, String originalFilename) {
		jianTaiDataDao.setCompanyInfoProductNameById(id, originalFilename);
		
	}

	@Override
	public JTMsdsUpload findMsdsFilenameByCompanyAndMateriel(String userName, String materialName) {
		return jianTaiDataDao.findMsdsFilenameByCompanyAndMateriel(userName, materialName);
	}

	@Override
	public void setMsdsFilename(String originalFilename, int id, Date now) {
		jianTaiDataDao.setMsdsFilename(originalFilename, id, now);
		
	}
 
	@Override
	public void addMsdsFileInfo(Date date, String materialName, String user, String originalFilename) {
		jianTaiDataDao.addMsdsFileInfo(date, materialName, user, originalFilename);
	}

	/**
	 * 添加操作日志
	 * @param log
	 */
	@Override
	public void addLog(JTLog log) {
		jianTaiDataDao.addLog(log);
	}

	/**
	 * 根据id查询物料详情
	 * @param id
	 * @return
	 */
	@Override
	public List<JTDeclareRecord> getJtDeclareRecordById(Integer id) {
		return jianTaiDataDao.getJtDeclareRecordById(id);
	}

	/**
	 * 根据id 查询企业产品生产数据表详情
	 * @param id
	 * @return
	 */
	@Override
	public List<JTProductRecord> getJtProductRecordById(Integer id) {
		return jianTaiDataDao.getJtProductRecordById(id);
	}
}
