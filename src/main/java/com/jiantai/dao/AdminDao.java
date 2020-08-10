package com.jiantai.dao;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 查询所有的公司信息
     * @return
     */
    @Select("select * from `jt_company_info`")
    List<CompanyInfo> getAllCompanyInfo();

    /**
     * 根据公司id查询公司信息
     * @param id
     * @return
     */
    @Select("select * from `jt_company_info` where `id` = #{id}")
    List<CompanyInfo> getCompanyInfoById(String id);

    /**
     * 根据公司id更新
     * @param companyInfo
     */
    @Update("update `jt_company_info` set `是否启用` = #{companyInfo.是否启用} where `id` = #{companyInfo.id}")
    void updateCompanyEnableById(@Param("companyInfo") CompanyInfo companyInfo);
    /**
     * 更新前端传来的公司数据
     */
    @Update("UPDATE `jiantai`.`jt_company_info` SET`id` = #{companyInfo.id},`user` = #{companyInfo.user},`password` = #{companyInfo.password},`企业编号` = #{companyInfo.企业编号},`企业简称` = #{companyInfo.企业简称},`企业全称` = #{companyInfo.企业全称},`法人代表` = #{companyInfo.法人代表},`联系地址` = #{companyInfo.联系地址},`公司固话` = #{companyInfo.公司固话},`公司传真` = #{companyInfo.公司传真},`联系人` = #{companyInfo.联系人},`联系人手机号` = #{companyInfo.联系人手机号},`统一社会信用代码` = #{companyInfo.统一社会信用代码},`企业人数` = #{companyInfo.企业人数},`开业时间` = #{companyInfo.开业时间},`产品类型` = #{companyInfo.产品类型},`园区位置` = #{companyInfo.园区位置},`是否启用` = #{companyInfo.是否启用},`备注` = #{companyInfo.备注},`plan_name` = #{companyInfo.planName},`product_name` = #{companyInfo.productName},`right` = #{companyInfo.right} WHERE `id` = #{companyInfo.id}")
    void updateCompanyInfoById(@Param("companyInfo") CompanyInfo companyInfo);

    /**
     * 根据公司简称查询详情
     * @param name
     * @return
     */
    @Select("select * from `jt_company_info` where `企业简称` like CONCAT('%',#{name},'%')")
    List<CompanyInfo> getCompanyInfoByName(@Param("name") String name);

    /**
     * 根据公司id设置是否开启跨月修改
     * @param companyInfo
     */
    @Update("update `jt_company_info` set `right` = #{companyInfo.right} where `id` = #{companyInfo.id}")
    void updateCompanyRightById(@Param("companyInfo") CompanyInfo companyInfo);

    /**
     * 根据公司id查询其操作记录
     * @param cid
     * @return
     */
    @Select("SELECT * FROM `jt_log` WHERE `cid` = #{cid} ORDER BY `create_time` DESC LIMIT 100")
    List<JTLog> getLogByCid(Integer cid);

    /**
     * 添加操作日志
     * @param log
     */
    @Insert("INSERT INTO jt_log (cid,content) VALUE (#{log.cid},#{log.content})")
    void addLog(@Param("log") JTLog log);

    /**
     * 查询物料记录
     */
    //@Select("select * from `jt_declare_record`")

    @Select("<script> " +
            "select *  from jt_declare_record " +
            " <where> " +
            " <if test='datetime != null'>datetime = #{datetime}</if> " +
            " <if test='company != null'> and company=#{company}</if> " +
            " <if test='material != null'> and material=#{material}</if> " +

            "<otherwise>" +
            "AND `delete` = 0"+
            "</otherwise>" +


            " </where> " +
            " </script> ")
    List<JTDeclareRecord> searchDeclareRecords(@Param("datetime") String datetime, @Param("company") String company, @Param("material") String material);

    /**
     * 查询物料列表
     */
    @Select("select * from `jt_materials`")
    List<Material> findMaterials();

    /**
     * 根据公司登录账号查找公司信息
     * @param name
     * @return
     */
    @Select("select * from `jt_company_info` where `user` = #{name}")
    List<CompanyInfo> getCompanyByUserName(String name);

    /**
     * 添加公司登录账号
     * @param name
     * @param pwd
     */
    @Insert("insert into `jt_company_info` (user,password) values (#{name},#{pwd})")
    void addCompany(String name,String pwd);

    /**
     * 查询所有的日志，便于管理员查看
     * @return
     */
    @Select("select * from `jt_log` order by create_time desc")
    List<JTLog> getAllLog();
}




