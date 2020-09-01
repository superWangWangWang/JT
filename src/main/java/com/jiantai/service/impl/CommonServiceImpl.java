package com.jiantai.service.impl;

import com.jiantai.dao.CommonDao;
import com.jiantai.entity.JTLog;
import com.jiantai.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;

    /**
     * 添加日志
     * @param log
     */
    @Override
    public void addLog(JTLog log) {
        commonDao.addLog(log);
    }
}
