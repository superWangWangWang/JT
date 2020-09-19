package com.jiantai.dao;

import com.jiantai.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

	//==============================================================================================

	/**
	 * 根据用户名查询用户是否存在，用于登录验证用户名
	 * @param userName
	 * @return
	 */
	@Select("select * from `user` where `user_name` = #{userName}")
	List<User> getUserByUserName(String userName);
	/**
	 * 根据传进来的user对象，更新user表
	 * @param u
	 */
	//@Update("UPDATE `user` SET `remarks` = #{u.remarks} WHERE `id` = #{u.id}")
	@Update("UPDATE `user` SET `company_short_name` = #{u.companyShortName},`company_name` = #{u.companyName},`legal_person` = #{u.legalPerson},`address` = #{u.address},`telephone` = #{u.telephone},`fax` = #{u.fax},`contacts` = #{u.contacts},`contacts_mobile_phone` = #{u.contactsMobilePhone},`social_credit_code` = #{u.socialCreditCode},`employees` = #{u.employees},`opening_time` = #{u.openingTime},`product_type` = #{u.productType},`location` = #{u.location},`remarks` = #{u.remarks} WHERE `id` = #{u.id}")
	void updateDetails(@Param("u") User u);
	/**
	 * 添加操作日志
	 * @param l
	 */
	@Insert("INSERT INTO `log` (cid,content) VALUE (#{l.cid},#{l.content})")
	void addLog(@Param("l")JTLog l);

	/**
	 * 更新user表上传的文件路径，根据字段名自动变换，
	 * @param field
	 * @param value
	 * @param id
	 */
	@Update("update `user` set ${field} = #{value} where `id` = #{id}")
	void uploadFile(String field,String value,Integer id);

	/**
	 * 根据id查user表的信息
	 * @return
	 */
	@Select("select * from `user` where `id` = #{id}")
	List<User> getUserById(Integer id);

	/**
	 * 修改密码
	 * @param pwd
	 * @param id
	 */
	@Update("update `user` set `password` = #{pwd} where `id` = #{id}")
	void changePwd(String pwd,Integer id);

	/**
	 * 根据公司id查询未上传的物料，排除已上传的
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM materials WHERE materials.`name` NOT IN (SELECT msds.`name` FROM msds WHERE c_id = #{id} and `state` = 1)")
	List<Material> getMaterialByIdExcludeUploaded(Integer id);

	/**
	 * 保存上传的msds文件路径
	 * @param cId
	 * @param name
	 * @param src
	 */
	@Insert("insert into `msds` (`c_id`,`name`,`src`) value (#{cId},#{name},#{src})")
	void saveMsds(Integer cId,String name,String src);

	/**
	 * 根据公司id和msds名 查询msds
	 * @param cId
	 * @return
	 */
	@Select("select * from `msds` where `c_id` = #{cId} and `name` = #{name} and `state` = 1")
	List<Msds> getMsdsByCidAndName(Integer cId,String name);

	/**
	 * 根据公司id和msds名 查询msds是否上传过
	 * @param cId
	 * @param name
	 * @return
	 */
	@Select("select * from `msds` where `c_id` = #{cId} and `name` = #{name}")
	List<Msds> getMsdsExist(Integer cId,String name);

	/**
	 * 根据id更新msds的路径
	 * @param src
	 * @param id
	 */
	@Update("update `msds` set `src` = #{src},`state` = 1 where `id` = #{id}")
	void updateMsdsById(String src,Integer id);

	/**
	 * 根据id删除msds
	 */
	@Update("UPDATE msds SET state = 0 WHERE `id` = #{id} AND `c_id` = #{c_id}")
	void deleteMsdsById(String id,Integer c_id);

	/**
	 * 根据公司id查询msds
	 * @return
	 */
	@Select("select * from `msds` where `c_id` = #{cId} and `state` = 1")
	List<Msds> getMsdsByCid(Integer cId);

	/**
	 * 根据公司的id查询 物料记忆表（该表用于回显公司的常用物料）
	 * @param cId
	 * @return
	 */
	@Select("SELECT materials.* FROM materials INNER JOIN materials_remember ON materials.`name` = materials_remember.`name` and materials_remember.`state` = 1 WHERE c_id = #{cId}")
	List<Material> getMaterialsRememberByCid(Integer cId);

	/**
	 * 根据公司id 和 申报年月 查询该公司的物料使用量  用于回显
	 * @param cId
	 * @param usedTime
	 * @return
	 */
	@Select("SELECT materials_used.* FROM materials_used WHERE `name` IN (SELECT `name` FROM materials_remember WHERE `c_id` = #{cId} AND `state` = 1 ) AND materials_used.`used_time` = #{usedTime}")
	List<Material> getMaterialsUsedByUsedTime(Integer cId,String usedTime);
	/**
	 * 根据公司id 获取物料表数据 排除物料记忆表中的（用于回显）
	 * @param cId
	 * @return
	 */
	@Select("SELECT * FROM materials WHERE materials.`name` NOT IN (SELECT materials_remember.`name` FROM materials_remember WHERE materials_remember.`c_id` = #{cId} and materials_remember.`state` = 1)")
	List<Material> getMaterialsExcludeRememberByCid(Integer cId);

	/**
	 * 根据公司id和物料名 查询
	 * @param cId
	 * @return
	 */
	@Select("select * from materials_remember where `c_id` = #{cId} and `name` = #{name}")
	List<MaterialRemember> getMaterialsRememberByCidAndName(Integer cId,String name);

	/**
	 * 保存物料到物料记忆表
	 * @param cId
	 * @param name
	 */
	@Insert("INSERT INTO `materials_remember` (c_id,`name`) VALUE (#{cId},#{name})")
	void saveMaterialsRememberByCidAndName(Integer cId,String name);

	/**
	 * 根据id和物料名 改变状态 1=正常，0=删除
	 * @param state
	 * @param cId
	 * @param name
	 */
	@Update("update materials_remember set `state` = #{state} where `c_id` = #{cId} and `name` = #{name}")
	void changeMaterialsRememberStateByCidAndName(Integer state,Integer cId,String name);

	/**
	 * 根据公司id和 物料用量使用年月查询
	 * @param cId
	 * @param usedTime
	 * @return
	 */
	@Select("SELECT * FROM materials_used WHERE `c_id` = #{cId} AND `used_time` = #{usedTime} AND `name` = #{name}")
	List<MaterialsUsed> getMaterialsUsedByCidAndUsedTimeAndName(Integer cId,String usedTime,String name);

	/**
	 * 保存物料使用情况
	 * @param cId
	 * @param name
	 * @param used
	 * @param usedTime
	 */
	@Insert("INSERT INTO materials_used (`c_id`,`name`,`used`,`used_time`) VALUES (#{cId},#{name},#{used},#{usedTime})")
	void saveMaterialsUsed(Integer cId,String name,Double used,String usedTime);

	/**
	 * 更新物料的使用情况
	 * @param used
	 * @param cId
	 * @param name
	 * @param usedTime
	 */
	@Update("UPDATE materials_used SET `used` = #{used} WHERE `c_id` = #{cId} AND `name` = #{name} AND `used_time` = #{usedTime}")
	void updateMaterialsUsed(Double used,Integer cId,String name,String usedTime);

	/**
	 * 查询所有产品
	 * @return
	 */
	@Select("SELECT products.*,products_class_second.`name` AS 'second',products_class_first.`name` AS 'first' FROM products INNER JOIN products_class_second ON products.`father` = products_class_second.`id` INNER JOIN products_class_first ON products_class_second.`father` = products_class_first.`id`")
	//@Select("select * from `products`")
	List<Product> getProductsList();

	/**
	 * 根据公司id，输出时间，查询公司每月的生产量
	 * @param cId
	 * @param outputTime
	 * @return
	 */
	//@Select("SELECT products.*,products_output.`output`,products_output.`output_time` FROM products INNER JOIN products_output ON products.`id` = products_output.`p_id` AND products_output.`c_id` = #{cId} AND output_time = #{outputTime}")
	@Select("SELECT products.*,products_output.`output`,products_output.`output_time`,products_class_second.`name` AS 'second',products_class_first.`name` AS 'first' FROM products INNER JOIN products_class_second ON products.`father` = products_class_second.`id` INNER JOIN products_class_first ON products_class_second.`father` = products_class_first.`id` INNER JOIN products_output ON products.`id` = products_output.`p_id` AND products_output.`c_id` = #{cId} AND output_time = #{outputTime}")
	List<Product> getProductsOutput(Integer cId,String outputTime);

	/**
	 * 插入产品产量
	 * @param cId
	 * @param pId
	 * @param output
	 * @param outputTime
	 */
	@Insert("INSERT INTO products_output (`c_id`,`p_id`,`output`,`output_time`) VALUES (#{cId},#{pId},#{output},#{outputTime})")
	void saveProductsOutput(Integer cId,String pId,Double output,String outputTime);

	/**
	 * 更新产品产量
	 * @param output
	 * @param outputTime
	 * @param cId
	 * @param pId
	 */
	@Update("UPDATE products_output SET `output` = #{output} WHERE `output_time` = #{outputTime} AND `c_id` = #{cId} AND `p_id` = #{pId}")
	void updateProductsOutput(Double output,String outputTime,Integer cId,String pId);

	/**
	 * 根据 pid获取产品信息  防止因为后面新增产品的话，新增的没法赋值
	 * @param cId
	 * @param outputTime
	 * @param pId
	 * @return
	 */
	@Select("SELECT products.*,products_output.`output`,products_output.`output_time` FROM products INNER JOIN products_output ON products.`id` = products_output.`p_id` AND products_output.`c_id` = #{cId} AND output_time = #{outputTime} AND `p_id` = #{pId}")
	List<Product> getProductsOutputByPid(Integer cId,String outputTime,String pId);

	/**
	 * 根据cid和时间查询 物料佐证
	 * @param cId
	 * @param evidenceTime
	 * @return
	 */
	@Select("SELECT * FROM materiels_evidence WHERE `c_id` = #{cId} AND `evidence_time` = #{evidenceTime}")
	List<Evidence> getMaterielsEvidenceByCid(Integer cId,String evidenceTime);

	/**
	 * 保存上传的物料凭证
	 * @param cId
	 * @param src
	 * @param evidenceTime
	 */
	@Insert("INSERT INTO materiels_evidence (`c_id`,`src`,`evidence_time`) VALUES (#{cId},#{src},#{evidenceTime})")
	void saveMaterielsEvidence(Integer cId,String src,String evidenceTime);

	/**
	 * 更新上传的物料凭证
	 * @param src
	 * @param cId
	 * @param evidenceTime
	 */
	@Update("UPDATE materiels_evidence SET `src` = #{src} WHERE `c_id` = #{cId} AND `evidence_time` = #{evidenceTime}")
	void updateMaterielsEvidence(String src,Integer cId,String evidenceTime);

	/**
	 * 根据id查询materiels_evidence表
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM materiels_evidence WHERE `id` = #{id}")
	List<Evidence> getMaterielsEvidenceById(Integer id);

	/**
	 * 添加空压机参数
	 * @param equipment
	 */
	@Insert("INSERT INTO `equipment` (`c_id`,`type`,`kinds`,`total`,`load_pressure`,`unload_pressure`,`power`,`exhaust_temperature`,`load_rate`,`lubricating_oil_used`,`lubricating_oil_replace`) VALUES (#{equipment.cId},#{equipment.type},#{equipment.kinds},#{equipment.total},#{equipment.loadPressure},#{equipment.unloadPressure},#{equipment.power},#{equipment.exhaustTemperature},#{equipment.loadRate},#{equipment.lubricatingOilUsed},#{equipment.lubricatingOilReplace})")
	void addEquipmentAir(@Param("equipment") Equipment equipment);

	/**
	 * 添加风机
	 * @param equipment
	 */
	@Insert("INSERT INTO `equipment` (`c_id`,`type`,`kinds`,`total`,`power`,`lubricating_oil_used`,`lubricating_oil_replace`) VALUES (#{equipment.cId},#{equipment.type},#{equipment.kinds},#{equipment.total},#{equipment.power},#{equipment.lubricatingOilUsed},#{equipment.lubricatingOilReplace})")
	void addEquipmentWind(@Param("equipment") Equipment equipment);

	/**
	 * 添加电机
	 * @param equipment
	 */
	@Insert("INSERT INTO `equipment` (`c_id`,`type`,`kinds`,`total`,`power`,`voltage`,`electric_current`,`speed`) VALUES (#{equipment.cId},#{equipment.type},#{equipment.kinds},#{equipment.total},#{equipment.power},#{equipment.voltage},#{equipment.electricCurrent},#{equipment.speed})")
	void addEquipmentElectric(@Param("equipment") Equipment equipment);

	/**
	 * 根据 型号，机器类型，公司id查询生产设备
	 * @param equipment
	 * @return
	 */
	@Select("SELECT * FROM equipment WHERE `type` = #{equipment.type} AND `kinds` = #{equipment.kinds} AND `state` = 1 AND `c_id` = #{equipment.cId}")
	List<Equipment> getEquipment(@Param("equipment") Equipment equipment);

	/**
	 * 根据公司id查出其所有的设备
	 * @param c_id
	 * @return
	 */
	@Select("select * from `equipment` where `c_id` = #{c_id} AND `state` = 1")
	List<Equipment> getEquipmentListByCid(Integer c_id);

	/**
	 * 根据equipment表的id删除单行 -- state状态置为0
	 * @param id
	 */
	@Update("update `equipment` set `state` = 0 where `id` = #{id}")
	void deleteEquipmentById(Integer id);

	/**
	 * 根据id查对应的设备详情
	 * @param id
	 * @return
	 */
	@Select("select * from `equipment` where `id` = #{id} AND `state` = 1")
	List<Equipment> getEquipmentById(Integer id);

	/**
	 * 根据id更新设备
	 * @param e
	 */
	@Update("UPDATE `equipment` SET `kinds` = #{e.kinds},`total` = #{e.total},`load_pressure` = #{e.loadPressure},`unload_pressure` = #{e.unloadPressure},`power` = #{e.power},`exhaust_temperature` = #{e.exhaustTemperature},`load_rate` = #{e.loadRate},`lubricating_oil_used` = #{e.lubricatingOilUsed},`lubricating_oil_replace` = #{e.lubricatingOilReplace},`voltage` = #{e.voltage},`electric_current` = #{e.electricCurrent},`speed` = #{e.speed} WHERE `id` = #{e.id}")
	void updateEquipment(@Param("e") Equipment e);

	/**
	 * 查询物料使用提交的时间（2020-08），根据物料记忆表没有删除的物料（因为删除了的物料就没必要再统计了），分组，
	 * 用于回显给用户看自己提交了哪几个月的数据
	 * @return
	 */
	@Select("SELECT `used_time` FROM  `materials_used` WHERE `c_id` = #{c_id} AND materials_used.`name` IN (SELECT `name` FROM `materials_remember` WHERE `c_id` = #{c_id} AND `state` = 1) GROUP BY `used_time` ORDER BY materials_used.`used_time` DESC")
	List<String> getMaterialsUsedTime(Integer c_id);

	/**
	 * 查询产品产量提交的时间（2020-08），分组，
	 * 用于回显给用户看自己提交了哪几个月的数据
	 * @param c_id
	 * @return
	 */
	@Select("SELECT output_time FROM `products_output` WHERE `c_id` = #{c_id} GROUP BY `output_time` ORDER BY products_output.`output_time` DESC")
	List<String> getProductsOutputByCid(Integer c_id);

	/**
	 * 根据公司id获取设备保养月份用于回显 ， 看看那个月份没有提交
	 * @param c_id
	 * @return
	 */
	@Select("SELECT maintain_time FROM equipment_maintenance WHERE c_id = #{c_id} AND state = 1 GROUP BY maintain_time")
	List<String> getMaintainTimeByCid(Integer c_id);

	/**
	 * 根据公司id和设备保养日期查询保养记录表 -- src
	 * @param c_id
	 * @param maintain_time
	 * @return
	 */
	@Select("SELECT * FROM equipment_maintenance WHERE c_id = #{c_id} AND maintain_time = #{maintain_time} AND `state` = 1")
	List<EquipmentMaintenance> getEquipmentMaintenance(Integer c_id,String maintain_time);

	/**
	 * 新增设备保养记录表
	 * @param c_id
	 * @param maintain_time
	 * @param src
	 */
	@Insert("INSERT INTO equipment_maintenance (`c_id`,`maintain_time`,`src`) VALUE (#{c_id},#{maintain_time},#{src})")
	void saveEquipmentMaintenance(Integer c_id,String maintain_time,String src);

	/**
	 * 更新设备保养表
	 * @param src
	 * @param id
	 */
	@Update("UPDATE equipment_maintenance SET `src` = #{src},`state` = 1 WHERE `id` = #{id}")
	void updateEquipmentMaintenanceById(String src,Integer id);

	/**
	 * 根据id删除设备保养记录
	 * @param id
	 */
	@Update("UPDATE equipment_maintenance SET `state` = 0 WHERE `id` = #{id}")
	void deleteEquipmentMaintenanceById(Integer id);
}
