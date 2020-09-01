package com.jiantai.service;

import com.jiantai.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

	public void changePassword(CompanyInfo companyInfo, String new_password) throws Exception;

	public CompanyInfo companyLogin(String username);

	public CompanyInfo findCompanyById(String id);
	void addLog(JTLog log);
	//=========================================
	List<User> getUserByUserName(String userName);
	void updateDetails(User u);
	void uploadFile(String field,String value,Integer id);
	List<User> getUserById(Integer id);
	List<Material> getMaterialByIdExcludeUploaded(Integer id);
	void saveMsds(Integer cId,String name,String src);
	List<Msds> getMsdsByCidAndName(Integer cId,String name);
	void updateMsdsById(String src,Integer id);
	List<Msds> getMsdsByCid(Integer cId);
	List<Material> getMaterialsRememberByCid(Integer cId);
	List<Material> getMaterialsExcludeRememberByCid(Integer cId);
	List<MaterialRemember> getMaterialsRememberByCidAndName(Integer cId,String name);
	void saveMaterialsRememberByCidAndName(Integer cId,String name);
	void changeMaterialsRememberStateByCidAndName(Integer state,Integer cId,String name);
	List<MaterialsUsed> getMaterialsUsedByCidAndUsedTimeAndName(Integer cId,String usedTime,String name);
	void saveMaterialsUsed(Integer cId,String name,Double used,String usedTime);
	void updateMaterialsUsed(Double used,Integer cId,String name,String usedTime);
	List<Material> getMaterialsUsedByUsedTime(Integer cId,String usedTime);
	List<Product> getProductsList();
	List<Product> getProductsOutput(Integer cId,String outputTime);
	void saveProductsOutput(Integer cId,String pId,Double output,String outputTime);
	void updateProductsOutput(Double output,String outputTime,Integer cId,String pId);
	List<Product> getProductsOutputByPid(Integer cId,String outputTime,String pId);
	List<Evidence> getMaterielsEvidenceByCid(Integer cId,String evidenceTime);
	void saveMaterielsEvidence(Integer cId,String src,String evidenceTime);
	void updateMaterielsEvidence(String src,Integer cId,String evidenceTime);
	List<Evidence> getMaterielsEvidenceById(Integer id);
	void addEquipmentAir(Integer c_id,Integer type,Double kinds,Double total,Double load_pressure,Double unload_pressure,Double power,Double exhaust_temperature,Double load_rate,Double lubricating_oil_used,Double lubricating_oil_replace);
	List<Equipment> getEquipmentListByCid(Integer c_id);
}
