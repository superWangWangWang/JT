package com.jiantai.dao;

import com.jiantai.entity.*;
import org.apache.commons.lang.StringUtils;
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
     * 根据申报时间,公司id、物料id，查询物料表
     * @param used_time
     * @param c_id
     * @param mid
     * @return
     */
    //使用UserDaoProvider类的findUserById方法来生成sql
    @SelectProvider(type = AdminDaoProvider.class, method = "getMaterialsUsedByTime")
    List<MaterialsUsed> getMaterialsUsedByTime(String used_time, String c_id, String mid);

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

    /**
     * 查询msds信息
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class, method = "getMsds")
    List<Msds> getMsds(String c_id);

    /**
     * 根据公司id和自身id 查询msds的src用于管理员下载
     * @param id
     * @param c_id
     * @return
     */
    @Select("select * from `msds` where `id` = #{id} and `c_id` = #{c_id} and `state` = 1")
    List<Msds> getMsdsByIdAndCid(String id,String c_id);


    class AdminDaoProvider {
        public String getMaterialsUsedByTime(String used_time,String c_id,String mid) {
            String tem = "";//AND c_id = 20 AND materials.id =27
            if (StringUtils.isNotBlank(c_id)){
                tem = tem + " AND `user`.id = " + c_id;
            }
            if (StringUtils.isNotBlank(mid)){
                tem = tem + " AND materials.id = " + mid;
            }
            tem = tem + " AND `user`.type = 0";
            String sql = "SELECT materials.`unit_cn`,materials.`id` AS materials_id,materials_used.*,`user`.`company_short_name` AS c_name FROM materials_used INNER JOIN materials ON materials.`name` = materials_used.`name` INNER JOIN `user` ON materials_used.`c_id` = `user`.`id`  WHERE `used_time` LIKE CONCAT('%',#{used_time},'%') AND materials_used.`name` IN (SELECT materials_remember.`name` FROM materials_remember WHERE `state` = 1) " + tem;
            sql = sql + " ORDER BY `update_time` DESC";
            return sql;
        }
        public String getMsds(String cid){
            String tem = "";
            if (StringUtils.isNotBlank(cid))
                tem = " AND `user`.id = " + cid;

            String sql = "SELECT `msds`.*,`user`.`company_short_name` FROM `msds` INNER JOIN `user` ON `msds`.`c_id` = `user`.`id` WHERE `user`.`type` = 0 AND `msds`.`state` = 1" + tem;
            return sql;
        }
    }
}




