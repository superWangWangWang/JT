package com.jiantai.dao;

import com.jiantai.entity.JTLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonDao {
    /**
     * 根据公司id查询其操作记录
     * @param cid
     * @return
     */
    @Select("SELECT * FROM `log` WHERE `cid` = #{cid} ORDER BY `create_time` DESC LIMIT 100")
    List<JTLog> getLogByCid(Integer cid);
    /**
     * 添加操作日志
     * @param log
     */
    @Insert("INSERT INTO `log` (cid,content) VALUE (#{log.cid},#{log.content})")
    void addLog(@Param("log") JTLog log);
}
