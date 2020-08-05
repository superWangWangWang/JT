package com.jiantai.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.LoginEntity;
import com.jiantai.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jiantai.entity.CompanyInfo;
import com.jiantai.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;

	/**
	 * 获取记住密码信息
	 **/
	@PostMapping(value = "/cookie")
	@ResponseBody
	public String cookie(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		Cookie userCookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("USER".equals(cookie.getName())) {
					userCookie = cookie;
				}
			}
		}
		if (userCookie != null) {
			result.put("user", userCookie.getValue().split("FENGE")[0]);
			result.put("password", userCookie.getValue().split("FENGE")[1]);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 登录
	 **/
	@SuppressWarnings("finally")
	@PostMapping(value = "/companyLogin")
	@ResponseBody
	public String companyLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		String keepPassword = request.getParameter("keepPassword");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		CompanyInfo companyInfo = userServiceImpl.companyLogin(username);
		//System.out.println("==================");
		try {


			if (companyInfo != null) {

				if (!companyInfo.getPassword().equals(password)) {
					result.put("error", "密码不正确");
				} else {
					//账号密码 正确 - 判断是不是账号被锁
					if (companyInfo.get是否启用() != 1){ //为1是正常账户 可以放行

						if (companyInfo.getType() == 1) {//普通账户 type=0 超级管理员type=1，即使是账号被锁也能登录
							result.put("error", "账户异常，已被锁！！！联系管理员处理！！！");
							return JSON.toJSONString(result);
						}
					}


					if ("on".equals(keepPassword)) {
						Cookie userCookie = new Cookie("USER",
								companyInfo.getUser() + "FENGE" + companyInfo.getPassword());
						userCookie.setMaxAge(60 * 60 * 24 * 30);
						response.addCookie(userCookie);
						// System.out.println("记住密码");
					} else {
						// System.out.println("尚未勾选记住密码");

						Cookie userCookie = new Cookie("USER", "");
						userCookie.setMaxAge(0);
						response.addCookie(userCookie);
					}
				}

				if (companyInfo.getType() == 1){
					System.out.println(companyInfo.get企业简称() + "于"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "登录页面");
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(-1);
					session.setAttribute("LOGIN_USER", companyInfo);
					System.out.println("================admin");
					//return "password.html";
				}else {
					System.out.println(companyInfo.get企业简称() + "于"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "登录页面");
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(-1);
					session.setAttribute("LOGIN_USER", companyInfo);
					//System.out.println("----"+companyInfo);
				}
				String ip = request.getRemoteAddr();
				String s = "<script> " +
						"select *  from jt_declare_record " +
						" <where> " +
						" <if test='datetime != null'>datetime = #{datetime}</if> " +
						" <if test='company != null'> and company=#{company}</if> " +
						" <if test='material != null'> and material=#{material}</if> " +
						" </where> " +
						" </script> ";
				System.out.println("************"+s);
				if (!"0:0:0:0:0:0:0:1".equals(ip)) {
					userServiceImpl.addLog(new JTLog(companyInfo.getId(), " 登录成功 IP：" + request.getRemoteAddr()));
				}
			} else {
				result.put("error", "用户不存在");
			}
		} catch (Exception e) {
			System.out.println(e);
			result.put("error", "服务器繁忙，请稍后再试");
			e.printStackTrace();
		} finally {

			if (companyInfo.getType() == 1){

				result.put("type", 1);
				return JSON.toJSONString(result);
			}else {
				result.put("type", 0);
				return JSON.toJSONString(result);
			}
			//return "admin/index.html";

		}
	}

	
	/**
	 * 判断是否登录成功
	 **/
	@PostMapping(value = "/getUserInfo")
	@ResponseBody
	public String islogin(HttpServletRequest request) {
		//System.out.println("==========aa");
		Map<String, Object> result = new HashMap<>();
		CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
		result.put("LOGIN_USER", companyInfo);
		return JSON.toJSONString(result);
	}

	
	/**
	 * 判断是否登录成功
	 **/
	@PostMapping(value = "/findUserInfo")
	@ResponseBody
	public String findCompanyById(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		CompanyInfo company = userServiceImpl.findCompanyById(id);
		result.put("company", company);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 用户退出
	 **/
	@GetMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("LOGIN_USER");
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

	/**
	 * 修改密码
	 **/
	@SuppressWarnings("finally")
	@PostMapping(value = "/changePassword")
	@ResponseBody
	public String changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String new_password = request.getParameter("password");
			CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
			if (companyInfo != null) {
				System.out.println(companyInfo.getUser() + ">正在修改密码");
				userServiceImpl.changePassword(companyInfo, new_password);
			} else {
				result.put("error", "请先登录");
			}
		} catch (Exception e) {
			result.put("error", "修改失败，请稍后再试");
			e.printStackTrace();
		}finally {
			return JSON.toJSONString(result);
		}

	}

}
