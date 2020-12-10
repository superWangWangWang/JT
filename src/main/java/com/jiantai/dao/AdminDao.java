package com.jiantai.dao;

import com.jiantai.entity.*;
import com.jiantai.utils.MyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 获取已上传msds的公司名列表
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM msds AS m INNER JOIN `user` AS u ON m.c_id = u.id WHERE m.state = 1 AND u.type = 0 AND u.state = 1 GROUP BY m.c_id ")
    List<String> getMSDSCompanyName();

    /**
     * 获取生产设备提交的企业名列表
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM equipment AS e INNER JOIN `user` AS u ON e.c_id = u.id WHERE e.state = 1 AND u.type = 0 AND u.state = 1 GROUP BY e.c_id ")
    List<String> getEquipmentCompanyName();

    /**
     * 获取每月设备保养记录的企业名列表
     * @param time
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM equipment_maintenance AS e INNER JOIN `user` AS u ON e.c_id = u.id WHERE e.maintain_time = #{time} AND u.type = 0 AND u.state = 1 GROUP BY e.c_id")
    List<String> getEquipmentMaintenanceCompanyNameLastMonth(String time);

    /**
     * 获取上一个月的提交产品产量的公司名列表
     * @param time
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM products_output AS po INNER JOIN `user` AS u ON po.c_id = u.id WHERE po.output_time = #{time} AND u.type = 0 AND u.state = 1 GROUP BY po.c_id")
    List<String> getProductsOutputCompanyNameLastMonth(String time);

    /**
     * 获取上个月的物料佐证上传公司名列表
     * @param time
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM materiels_evidence AS m INNER JOIN `user` AS u ON m.c_id = u.id WHERE m.evidence_time = #{time} AND u.type = 0 AND u.state = 1")
    List<String> getMaterielsEvidenceCompanyNameLastMonth(String time);

    /**
     * 获取上个月的物料申报公司名列表
     * @param time
     * @return
     */
    @Select("SELECT u.company_short_name AS `name` FROM materials_used AS m INNER JOIN `user` AS u ON u.id = m.c_id INNER JOIN materials_remember AS mr ON mr.name = m.name WHERE mr.state = 1 AND m.used_time = #{time} AND mr.c_id = u.id AND u.type = 0 AND u.state = 1 GROUP BY m.c_id")
    List<String> getMaterialsUsedCompanyNameLastMonth(String time);
//------------------------------------下面的暂时没对公司账户是否state = 1 判断，如果是0则需要排除，因为该账户已不能正常登录使用
    /**
     * 添加公司账户
     * @param user_name
     * @param password
     * @param company_name
     * @param company_short_name
     */
    @Insert("INSERT INTO `user` SET `user_name` = #{user_name},`password` = #{password},`company_name` = #{company_name},`company_short_name`= #{company_short_name}")
    void companyAdd(String user_name,String password,String company_name,String company_short_name);

    /**
     * 查询所有的公司信息
     * @return
     */
    @Select("SELECT * FROM `user`")
    List<User> getAllCompanyInfo();

    /**
     * 查询所有的公司信息除了管理员
     * @return
     */
    @Select("SELECT * FROM `user` where `type` = 0")
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

    /**
     * 根据时间或公司id查询产品输出
     * @param time
     * @param cid
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class, method = "getOutput")
    List<Product> getOutput(String time,String cid);

    /**
     * 根据公司id查询生产设备
     * @param cid
     * @param type
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class,method = "getEquipment")
    List<Equipment> getEquipment(String cid,String type);

    /**
     * 查询上传的物料凭证
     * @param cid
     * @param time
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class,method = "getMaterielEvidence")
    List<MaterielEvidence> getMaterielEvidence(String cid,String time);

    /**
     * 根据公司id和物料凭证id查询 物料凭证src用于管理员下载
     * @param cid
     * @param id
     * @return
     */
    @Select("SELECT materiels_evidence.* FROM materiels_evidence INNER JOIN `user` ON materiels_evidence.`c_id` = `user`.`id` WHERE `user`.`id` = #{cid} AND materiels_evidence.`id` = #{id}")
    List<MaterielEvidence> getMaterielEvidenceSrc(String cid,String id);

    /**
     * 获取设备保养记录列表
     * @param time
     * @param cid
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class,method = "getEquipmentMaintenanceList")
    List<EquipmentMaintenance> getEquipmentMaintenanceList(String time,String cid);

    /**
     * 获取设备保养记录的src用于管理员下载
     * @param cid
     * @param id
     * @return
     */
    @Select("SELECT e.*,u.`company_short_name` FROM equipment_maintenance AS e INNER JOIN `user` AS u ON u.`id` = e.`c_id` WHERE e.`state` = 1 AND e.`id` = #{id} AND e.`c_id` = #{cid}")
    List<EquipmentMaintenance> getEquipmentMaintenanceSrc(String cid,String id);

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
        public String getOutput(String time,String cid){
            String sql = "SELECT po.*,u.`company_short_name`,p.unit_cn,p.name AS `name`,p.unit,pcs.name AS `second`,pcf.name AS `first` " +
                    "FROM products_output AS po INNER JOIN `user` AS u ON u.`id` = po.`c_id` " +
                    "INNER JOIN products AS p ON p.id = po.p_id " +
                    "INNER JOIN products_class_second AS pcs ON pcs.id = p.father " +
                    "INNER JOIN products_class_first AS pcf ON pcf.id = pcs.father " +
                    "WHERE po.output != 0";

            if (StringUtils.isNotBlank(time) && MyUtils.isValidDate(time)){
                System.out.println(time);
                sql = sql + " AND po.output_time = '" + time + "'";
            }
            if (StringUtils.isNotBlank(cid)){
                sql = sql + " AND po.c_id = " + cid;
            }
            return sql;
        }
        public String getEquipment(String cid,String type){
            String sql = "SELECT equipment.*,`user`.`company_short_name` FROM equipment INNER JOIN `user` ON `user`.`id` = equipment.`c_id` WHERE equipment.`type` = " + type + " AND equipment.`state` = 1 AND `user`.`type` = 0";
            if (StringUtils.isNotBlank(cid))
                sql += " AND equipment.`c_id` = " + cid;
            return sql;
        }
        public String getMaterielEvidence(String cid,String time){
            String sql = "SELECT m.*,u.`company_short_name` FROM materiels_evidence AS m INNER JOIN `user` AS u ON m.c_id = u.id WHERE 1 = 1";
            if (StringUtils.isNotBlank(cid))
                sql += " AND m.`c_id` = " + cid;
            if (StringUtils.isNotBlank(time) && MyUtils.isValidDate(time))
                sql += " AND m.`evidence_time` = '" + time + "'";
            return sql;
        }
        public String getEquipmentMaintenanceList(String time,String cid){
            String sql = "SELECT e.*,u.`company_short_name` FROM equipment_maintenance AS e INNER JOIN `user` AS u ON u.`id` = e.`c_id` WHERE e.`state` = 1";
            if (StringUtils.isNotBlank(time) && MyUtils.isValidDate(time))
                sql += " AND e.`maintain_time` = '" + time + "'";
            if (StringUtils.isNotBlank(cid))
                sql += " AND e.`c_id` = " + cid;
            return sql;
        }
    }

    /**
     * 新增物料
     * @param material
     */
    @Insert("INSERT INTO `materials` (`name`,`unit`,`unit_cn`,`remarks`) VALUE (#{material.name},#{material.unit},#{material.unitCn},#{material.remarks})")
    void addMaterial(@Param("material") Material material);
}




