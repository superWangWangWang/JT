package com.jiantai.dao;

import com.jiantai.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 查询所有的公司信息
     * @return
     */
    @Select("SELECT * FROM `user`")
    List<User> getAllCompanyInfo();

    /**
     * 查询所有的公司信息除了超级管理员 -- state不为2
     * @return
     */
    @Select("SELECT * FROM `user` where `type` != 2")
    List<User> getAllCompanyInfoExcludeSuper();

    /**
     * 根据公司id查询公司信息
     * @param id
     * @return
     */
    @Select("select * from `user` where `id` = #{id}")
    List<User> getCompanyInfoById(String id);

    /**
     * 根据公司id更新
     * @param user
     */
    @Update("update `user` set `state` = #{user.state} where `id` = #{user.id}")
    void updateCompanyEnableById(@Param("user") User user);
    /**
     * 更新前端传来的公司数据
     */
    @Update("UPDATE `user` SET ${field} = #{value} WHERE `id` = #{id}")
    void updateCompanyInfo(String field,String value,String id);

    /**
     * 根据公司简称查询详情
     * @param name
     * @return
     */
    @Select("select * from `user` where `company_short_name` like CONCAT('%',#{name},'%')")
    List<User> getCompanyInfoByName(@Param("name") String name);

    /**
     * 根据公司id设置是否开启跨月修改
     * @param user
     */
    @Update("update `user` set `modify` = #{user.modify} where `id` = #{user.id}")
    void updateCompanyModifyById(@Param("user") User user);

//    /**
//     * 根据公司id查询其操作记录
//     * @param cid
//     * @return
//     */
//    @Select("SELECT * FROM `log` WHERE `cid` = #{cid} ORDER BY `create_time` DESC LIMIT 100")
//    List<JTLog> getLogByCid(Integer cid);

//    /**
//     * 添加操作日志
//     * @param log
//     */
//    @Insert("INSERT INTO `log` (cid,content) VALUE (#{log.cid},#{log.content})")
//    void addLog(@Param("log") JTLog log);

    /**
     * 查询物料记录
     */
    //@Select("select * from `jt_declare_record`")

    @Select("<script> " +
            "select *  from jt_declare_record " +
            " <where> " +
            " `delete` = 0" +
            " <if test='datetime != null'>and datetime = #{datetime}</if> " +
            " <if test='company != null'> and company = #{company}</if> " +
            " <if test='material != null'> and material = #{material}</if> " +
            " </where> " +
            " </script> ")
    List<JTDeclareRecord> searchDeclareRecords(@Param("datetime") String datetime, @Param("company") String company, @Param("material") String material);

    /**
     * 查询物料列表
     */
    @Select("select * from `jt_materials`")
    List<Material> findMaterials();

    @Select("select * from `jt_company_info` where plan_name is not null and `企业简称` != '测试账号' ")
    List<CompanyInfo> findExistPlanName();


    @Select("select * from `jt_company_info` where product_name is not null and `企业简称` != '测试账号' ")
    List<CompanyInfo> findExistProductName();


    @Select("<script> " +
            "select *  from jt_msds_upload " +
            " <where> " +
            " <if test='company != null'>company = #{company}</if> " +
            " <if test='material != null'> and material=#{material}</if> " +
            " </where> " +
            " </script> ")
    List<JTMsdsUpload> findMsdsUpload(@Param("company") String company, @Param("material") String material);

    @Select("<script> " +
            "select *  from jt_material_evidence " +
            " <where> " +
            " <if test='company != null'>company = #{company}</if> " +
            " <if test='datetime != null'> and datetime=#{datetime}</if> " +
            " </where> " +
            " </script> ")
    List<JtMaterialEvidence> findEvidenceUpload(@Param("company") String company, @Param("datetime") String datetime);


    /**
     * 根据id查询msds
     * @param id
     */
    @Select("select * from `jt_msds_upload` where `id` = #{id}")
    JTMsdsUpload findMsdsUploadById(String id);


    /**
     * 根据id查询佐证
     * @param id
     */
    @Select("select * from `jt_material_evidence` where `id` = #{id}")
    JtMaterialEvidence findJtMaterialEvidenceById(String id);
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
    void addCompany(@Param("name") String name,@Param("pwd") String pwd);

    /**
     * 查询所有的日志，便于管理员查看
     * @return
     */
    @Select("SELECT l.create_time,l.content,c.企业简称 AS shortName FROM `jt_log` AS l INNER JOIN (SELECT * FROM `jt_company_info` WHERE `企业简称` IS NOT NULL) AS c ON l.cid = c.id  ORDER BY create_time DESC")
    List<JTLog> getAllLog();
}




