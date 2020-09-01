package com.jiantai.service.impl;

import com.jiantai.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiantai.dao.UserDao;
import com.jiantai.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;


	@Override
	public void changePassword(CompanyInfo companyInfo, String new_password) throws Exception {
		userDao.changePassword(companyInfo,new_password);
	}

	@Override
	public CompanyInfo companyLogin(String username) {
		return userDao.companyLogin(username);
	}

	@Override
	public CompanyInfo findCompanyById(String id) {
		return userDao.findCompanyById(id);
	}

	@Override
	public void addLog(JTLog log) {
		userDao.addLog(log);
	}

	//=========================================

	/**
	 * 登录的时候，根据用户名查询是否存在用户
	 * @param userName
	 * @return
	 */
	@Override
	public List<User> getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	/**
	 * 根据传进来的user对象，更新user表
	 * @param u
	 */
	@Override
	public void updateDetails(User u) {
		userDao.updateDetails(u);
	}

	/**
	 * 更新user表上传的文件路径，根据字段名自动变换
	 * @param field
	 * @param value
	 * @param id
	 */
	@Override
	public void uploadFile(String field, String value, Integer id) {
		userDao.uploadFile(field,value,id);
	}

	@Override
	public List<User> getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	/**
	 * 根据公司id查询未上传的物料，排除已上传的
	 * @param id
	 * @return
	 */
	@Override
	public List<Material> getMaterialByIdExcludeUploaded(Integer id) {
		return userDao.getMaterialByIdExcludeUploaded(id);
	}

	/**
	 * 保存上传的msds文件路径
	 * @param cId
	 * @param name
	 * @param src
	 */
	@Override
	public void saveMsds(Integer cId, String name, String src) {
		userDao.saveMsds(cId,name,src);
	}

	/**
	 * 根据公司id和msds名 查询msds
	 * @param cId
	 * @return
	 */
	@Override
	public List<Msds> getMsdsByCidAndName(Integer cId, String name) {
		return userDao.getMsdsByCidAndName(cId,name);
	}

	/**
	 * 根据id更新msds的路径
	 * @param src
	 * @param id
	 */
	@Override
	public void updateMsdsById(String src, Integer id) {
		userDao.updateMsdsById(src,id);
	}

	/**
	 * 根据公司id查询msds
	 * @param cId
	 * @return
	 */
	@Override
	public List<Msds> getMsdsByCid(Integer cId) {
		return userDao.getMsdsByCid(cId);
	}

	/**
	 * 根据公司的id查询 物料记忆表（该表用于回显公司的常用物料）
	 * @param cId
	 * @return
	 */
	@Override
	public List<Material> getMaterialsRememberByCid(Integer cId) {
		return userDao.getMaterialsRememberByCid(cId);
	}

	@Override
	public List<Material> getMaterialsUsedByUsedTime(Integer cId, String usedTime) {
		return userDao.getMaterialsUsedByUsedTime(cId,usedTime);
	}

	/**
	 * 根据公司id 获取物料表数据 排除物料记忆表中的（用于回显）
	 * @param cId
	 * @return
	 */
	@Override
	public List<Material> getMaterialsExcludeRememberByCid(Integer cId) {
		return userDao.getMaterialsExcludeRememberByCid(cId);
	}

	/**
	 * 根据公司id和物料名 查询
	 * @param cId
	 * @param name
	 * @return
	 */
	@Override
	public List<MaterialRemember> getMaterialsRememberByCidAndName(Integer cId, String name) {
		return userDao.getMaterialsRememberByCidAndName(cId,name);
	}

	/**
	 * 保存物料到物料记忆表
	 * @param cId
	 * @param name
	 */
	@Override
	public void saveMaterialsRememberByCidAndName(Integer cId, String name) {
		userDao.saveMaterialsRememberByCidAndName(cId,name);
	}

	/**
	 * 根据id和物料名 改变状态 1=正常，0=删除
	 * @param state
	 * @param cId
	 * @param name
	 */
	@Override
	public void changeMaterialsRememberStateByCidAndName(Integer state, Integer cId, String name) {
		userDao.changeMaterialsRememberStateByCidAndName(state,cId,name);
	}

	/**
	 * 根据公司id和 物料用量使用年月 和 物料名 查询
	 * @param cId
	 * @param usedTime
	 * @param name
	 * @return
	 */
	@Override
	public List<MaterialsUsed> getMaterialsUsedByCidAndUsedTimeAndName(Integer cId, String usedTime, String name) {
		return userDao.getMaterialsUsedByCidAndUsedTimeAndName(cId,usedTime,name);
	}

	/**
	 * 保存物料使用情况
	 * @param cId
	 * @param name
	 * @param used
	 * @param usedTime
	 */
	@Override
	public void saveMaterialsUsed(Integer cId, String name, Double used, String usedTime) {
		userDao.saveMaterialsUsed(cId,name,used,usedTime);
	}

	/**
	 * 更新物料使用情况
	 * @param used
	 * @param cId
	 * @param name
	 * @param usedTime
	 */
	@Override
	public void updateMaterialsUsed(Double used, Integer cId, String name, String usedTime) {
		userDao.updateMaterialsUsed(used,cId,name,usedTime);
	}

	/**
	 * 查询所有产品
	 * @return
	 */
	@Override
	public List<Product> getProductsList() {
		return userDao.getProductsList();
	}

	/**
	 * 根据公司id，输出时间，查询公司每月的生产量
	 * @param cId
	 * @param outputTime
	 * @return
	 */
	@Override
	public List<Product> getProductsOutput(Integer cId, String outputTime) {
		return userDao.getProductsOutput(cId,outputTime);
	}

	/**
	 * 插入产品产量
	 * @param cId
	 * @param pId
	 * @param output
	 * @param outputTime
	 */
	@Override
	public void saveProductsOutput(Integer cId, String pId, Double output, String outputTime) {
		userDao.saveProductsOutput(cId,pId,output,outputTime);
	}

	/**
	 * 更新产品产量
	 * @param output
	 * @param outputTime
	 * @param cId
	 * @param pId
	 */
	@Override
	public void updateProductsOutput(Double output, String outputTime, Integer cId, String pId) {
		userDao.updateProductsOutput(output,outputTime,cId,pId);
	}

	/**
	 * 根据 pid获取产品信息  防止因为后面新增产品的话，新增的没法赋值
	 * @param cId
	 * @param outputTime
	 * @param pId
	 * @return
	 */
	@Override
	public List<Product> getProductsOutputByPid(Integer cId, String outputTime, String pId) {
		return userDao.getProductsOutputByPid(cId,outputTime,pId);
	}

	/**
	 * 根据cid和时间查询 物料佐证
	 * @param cId
	 * @param evidenceTime
	 * @return
	 */
	@Override
	public List<Evidence> getMaterielsEvidenceByCid(Integer cId, String evidenceTime) {
		return userDao.getMaterielsEvidenceByCid(cId,evidenceTime);
	}

	/**
	 * 保存上传的物料凭证
	 * @param cId
	 * @param src
	 * @param evidenceTime
	 */
	@Override
	public void saveMaterielsEvidence(Integer cId, String src, String evidenceTime) {
		userDao.saveMaterielsEvidence(cId,src,evidenceTime);
	}

	/**
	 * 更新上传的物料凭证
	 * @param src
	 * @param cId
	 * @param evidenceTime
	 */
	@Override
	public void updateMaterielsEvidence(String src, Integer cId, String evidenceTime) {
		userDao.updateMaterielsEvidence(src,cId,evidenceTime);
	}

	/**
	 * 根据id查询materiels_evidence表
	 * @param id
	 * @return
	 */
	@Override
	public List<Evidence> getMaterielsEvidenceById(Integer id) {
		return userDao.getMaterielsEvidenceById(id);
	}

	/**
	 * 添加空压机参数
	 * @param c_id
	 * @param kinds
	 * @param total
	 * @param load_pressure
	 * @param unload_pressure
	 * @param power
	 * @param exhaust_temperature
	 * @param load_rate
	 * @param lubricating_oil_used
	 * @param lubricating_oil_replace
	 */
	@Override
	public void addEquipmentAir(Integer c_id,Integer type, Double kinds, Double total, Double load_pressure, Double unload_pressure, Double power, Double exhaust_temperature, Double load_rate, Double lubricating_oil_used, Double lubricating_oil_replace) {
		userDao.addEquipmentAir(c_id,type,kinds,total,load_pressure,unload_pressure,power,exhaust_temperature,load_rate,lubricating_oil_used,lubricating_oil_replace);
	}

	@Override
	public List<Equipment> getEquipmentListByCid(Integer c_id) {
		return userDao.getEquipmentListByCid(c_id);
	}
}
