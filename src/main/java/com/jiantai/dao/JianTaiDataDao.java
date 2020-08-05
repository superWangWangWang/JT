package com.jiantai.dao;

import java.util.Date;
import java.util.List;

import com.jiantai.entity.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface JianTaiDataDao {
	
	@Select("select * from jt_materials")
	List<Material> findDataToJTMaterialList();
	
	
	
	@Insert("insert into jt_declare_record(recordDatetime, datetime, material, unit, element, content, dosage, company) values(#{now}, #{datetime}, #{material}, #{unit}, #{element}, #{content}, #{dosage}, #{userName})")
	@Options(useGeneratedKeys = false)
	void addDeclareInfoToJTDeclareInfo(@Param("now") Object object, @Param("datetime") Object datetime, @Param("userName") Object object2, @Param("material") Object object3, @Param("unit") Object object4, @Param("element") Object object5,
                                       @Param("content") Object object6, @Param("dosage") Object object7);
	
	
	@Update("update jt_company_info set `联系地址` = #{联系地址}, `公司固话` = #{公司固话}, `公司传真` = #{公司传真}, `开业时间` = #{开业时间}, `企业人数` = #{企业人数}, `统一社会信用代码` = #{统一社会信用代码}, `法人代表` = #{法人代表}, `联系人` = #{联系人}, `联系人手机号` = #{联系人手机号}, `产品类型` = #{产品类型}, `园区位置` = #{园区位置}, `备注` = #{备注} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void setCompanyInfoById(@Param("id") String id, @Param("联系地址") String string, @Param("公司固话") String string2, @Param("公司传真") String string3, @Param("开业时间") String string4, @Param("企业人数") String string5,
                            @Param("统一社会信用代码") String string6, @Param("法人代表") String string7, @Param("联系人") String string8, @Param("联系人手机号") String string9, @Param("产品类型") String string10, @Param("园区位置") String string11,
                            @Param("备注") String string12);

	/**
	 * 根据id查询物料详情
	 * @param id
	 * @return
	 */
	@Select("select * from `jt_declare_record` where id = #{id}")
	List<JTDeclareRecord> getJtDeclareRecordById(Integer id);
	
	@Select("select * from jt_declare_record where company = #{username} and datetime = #{date} and `delete` = 0")
	List<JTDeclareRecord> findRecordById(@Param("username") String username, @Param("date") String date);

	
	//@Delete ("delete from jt_declare_record where id = #{id}")
	@Update("update `jt_declare_record` set `delete` = 1 where id = #{id}")
	@Options(useGeneratedKeys = false)
	void delRecordById(@Param("id") int id);

	@Update("update jt_declare_record set `material` = #{material}, `unit` = #{unit}, `element` = #{element}, `content` = #{content}, `dosage` = #{dosage} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void updateDeclareInfoById(@Param("material") Object material, @Param("unit") Object unit, @Param("element") Object element, @Param("content") Object content, @Param("dosage") Object dosage,
                               @Param("id") Object id);

	/**
	 * 根据id更新 jt_company_info表 平面图文件名字
	 * @param id
	 * @param filename
	 */
	@Update("update jt_company_info set `plan_name` = #{plan_name} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void setCompanyInfoPlanNameById(@Param("id") int id, @Param("plan_name") String filename);

	
	@Select("select * from jt_company_info where id = #{id}")
	CompanyInfo findCompanyInfoById(int id);


	/**
	 * 没有使用上此方法，不知道
	 * @param materialName
	 * @param filename
	 */
	@Update("update jt_materials set `msds_filename` = #{filename} where `物料名称` = #{materialName}")
	@Options(useGeneratedKeys = false)
	void setFilenameByMaterial(@Param("materialName") String materialName, @Param("filename") String filename);
	
	
	@Select("select * from jt_material_evidence where `company` = #{company} and `datetime` = #{datetime}")
	JtMaterialEvidence findEvidenceByCompanyName(@Param("company") String companyName, @Param("datetime") String date);

	
	
	@Update("update jt_material_evidence set `filename` = #{filename} where `id` = #{id}")
	@Options(useGeneratedKeys = false)
	void updateMaterialEvidenceById(@Param("id") int id, @Param("filename") String filename);
	
	@Insert("INSERT INTO jt_material_evidence(filename, company, datetime) values(#{filename}, #{company}, #{datetime})")
	@Options(useGeneratedKeys = false)
	void addMaterialEvidenceById(@Param("filename") String filename, @Param("company") String company, @Param("datetime") String date);


	@Select("select * from jt_products")
	List<JTProductRecord> findProducts();


	@Select("select * from jt_product_record where company = #{company} and datetime = #{datetime}")
	List<JTProductRecord> findProductRecordByDate(@Param("company") String company, @Param("datetime") String date);

	
	@Select("select * from jt_product_record where company = #{company} and datetime = #{datetime} and product = #{product}")
	JTProductRecord findUnitByCompanyAndDate(@Param("datetime") String date, @Param("product") String product, @Param("company") String name);

	/**
	 * 根据id 查询企业产品生产数据表详情
	 * @param id
	 * @return
	 */
	@Select("select * from `jt_product_record` where id = #{id}")
	List<JTProductRecord> getJtProductRecordById(Integer id);

	@Update("update jt_product_record set yield = #{yield} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void setYieldById(@Param("id") Integer id, @Param("yield") String yield);


	@Insert("INSERT INTO jt_product_record(product, record_datetime, datetime, yield, company) values(#{product}, #{record_datetime}, #{datetime}, #{yield}, #{company})")
	@Options(useGeneratedKeys = false)
	void addProductRecord(@Param("product") String product, @Param("record_datetime") Date now, @Param("datetime") String datetime, @Param("yield") String yield, @Param("company") String company);


	@Update("update jt_company_info set `product_name` = #{productName} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void setCompanyInfoProductNameById(@Param("id") int id, @Param("productName") String productName);


	@Select("select * from jt_msds_upload where company = #{userName} and material = #{materialName}")
	JTMsdsUpload findMsdsFilenameByCompanyAndMateriel(@Param("userName") String userName, @Param("materialName") String materialName);


	@Update("update jt_msds_upload set msds_filename = #{originalFilename}, update_datetime = #{now} where id = #{id}")
	@Options(useGeneratedKeys = false)
	void setMsdsFilename(@Param("originalFilename") String originalFilename, @Param("id") int id, @Param("now") Date now);


	@Insert("INSERT INTO jt_msds_upload(update_datetime, material, company, msds_filename) values(#{update_datetime}, #{material}, #{company}, #{msds_filename})")
	@Options(useGeneratedKeys = false)
	void addMsdsFileInfo(@Param("update_datetime") Date date, @Param("material") String materialName, @Param("company") String user, @Param("msds_filename") String originalFilename);
	/**
	 * 添加操作日志
	 * @param log
	 */
	@Insert("INSERT INTO jt_log (cid,content) VALUE (#{log.cid},#{log.content})")
	void addLog(@Param("log") JTLog log);
}
