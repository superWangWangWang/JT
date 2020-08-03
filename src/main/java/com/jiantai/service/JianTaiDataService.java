package com.jiantai.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTMsdsUpload;
import com.jiantai.entity.JTProductRecord;
import com.jiantai.entity.JtMaterialEvidence;
import com.jiantai.entity.Material;
import org.springframework.stereotype.Service;

@Service
public interface JianTaiDataService {


	List<Material> findDataToJTMaterialList();

	void addDeclareInfoToJTDeclareInfo(Map<String, Object> map);

	void setCompanyInfoById(String id, Map<String, String> map);

	List<JTDeclareRecord> findRecordById(String username, String date);

	void delRecordById(int id);

	void updateDeclareInfoById(Map<String, Object> map);

	void setCompanyInfoPlanNameById(int id, String filename);

	CompanyInfo findCompanyInfoById(int id);


	void setFilenameByMaterial(String materialName, String filename);

	JtMaterialEvidence findEvidenceByCompanyName(String company, String date);

	void updateMaterialEvidenceById(int id, String filename);

	void addMaterialEvidenceById(String filename, String company, String date);

	List<JTProductRecord> findProducts();

	List<JTProductRecord> findProductRecordByDate(String company, String date);

	JTProductRecord findUnitByCompanyAndDate(String date, String product, String company);

	void setYieldById(Integer id, String yield);

	void addProductRecord(String product, Date now, String datetime, String yield, String company);

	void setCompanyInfoProductNameById(int id, String originalFilename);

	JTMsdsUpload findMsdsFilenameByCompanyAndMateriel(String userName, String materialName);
	
	void setMsdsFilename(String originalFilename, int id, Date now);

	void addMsdsFileInfo(Date date, String materialName, String user, String originalFilename);
	
}
