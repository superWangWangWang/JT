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
     * 查询物料列表
     */
    @Select("select * from `materials`")
    List<Material> getMaterials();

    /**
     * 根据申报时间查询物料表
     * @param used_time
     * @return
     */
    //@Select("SELECT materials_used.*,`user`.`user_name` AS c_name FROM materials_used INNER JOIN `user` ON materials_used.`c_id` = `user`.`id`  WHERE `used_time` = #{used_time} AND `used` != 0")
    @Select("SELECT materials_used.*,`user`.`company_short_name` AS c_name FROM materials_used INNER JOIN `user` ON materials_used.`c_id` = `user`.`id`  WHERE `used_time` = #{used_time}")
    List<MaterialsUsed> getMaterialsUsedByTime(String used_time);

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
    @Select("SELECT l.*,u.company_short_name FROM `log` AS l INNER JOIN `user` AS u ON l.`cid` = u.`id` ORDER BY create_time DESC")
    List<JTLog> getAllLog();
}




