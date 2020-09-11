package com.jiantai.service.impl;

import com.jiantai.dao.CommonDao;
import com.jiantai.dao.UserDao;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.User;
import com.jiantai.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private UserDao userDao;
    /**
     * 添加日志
     * @param log
     */
    @Override
    public void addLog(JTLog log) {
        List<User> users = userDao.getUserById(log.getCid());
        User user = users.get(0);
        if (user.getType() == 2){//超管type = 2 ，普通管理员 1 ，公司 0
            System.out.println("================================超级管理员日志不记录入数据库========================================");
        }else {
            commonDao.addLog(log);
        }
    }

    /**
     * 根据公司id查询其操作记录
     * @param cid
     * @return
     */
    @Override
    public List<JTLog> getLogByCid(Integer cid) {
        return commonDao.getLogByCid(cid);
    }
}
