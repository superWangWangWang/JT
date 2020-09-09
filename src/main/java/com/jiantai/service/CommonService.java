package com.jiantai.service;

import com.jiantai.entity.JTLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonService {
    void addLog(JTLog log);
    List<JTLog> getLogByCid(Integer cid);
}
