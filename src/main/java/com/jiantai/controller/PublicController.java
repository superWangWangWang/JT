package com.jiantai.controller;

import com.jiantai.entity.User;
import com.jiantai.service.impl.UserServiceImpl;
import com.jiantai.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PublicController {
    @Autowired
    private UserServiceImpl userService;


}
