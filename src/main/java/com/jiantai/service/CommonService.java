package com.jiantai.service;

import com.jiantai.entity.JTLog;
import org.apache.ibatis.annotations.Param;

public interface CommonService {
    void addLog(@Param("log") JTLog log);
}
