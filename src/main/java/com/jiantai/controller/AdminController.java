package com.jiantai.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import com.jiantai.entity.MaterialsUsed;
import com.jiantai.entity.User;
import com.jiantai.service.impl.AdminServiceImpl;
import com.jiantai.service.impl.CommonServiceImpl;
import com.jiantai.utils.MyUtils;
import com.jiantai.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private CommonServiceImpl commonService;

    /**
     * 到管理员首页
     *
     * @return
     */
    @RequestMapping("index")
    public ModelAndView toIndex() {
        ModelAndView mv = new ModelAndView();
        //mv.addObject("data", "aaa");
        mv.setViewName("admin/index");
        return mv;
    }

    /**
     * 返回管理员欢迎页面，单独写一个方法是为了后期能给模板动态赋值
     *
     * @return
     */
    @RequestMapping("welcome")
    public ModelAndView toWelcome() {
        System.out.println("welcome");
        ModelAndView mv = new ModelAndView();
        mv.addObject("data", "aaa");
        //查询公司一共有多少
        mv.setViewName("admin/welcome");
        return mv;
    }

    /**
     * 查询所有公司详情，展示列表，返回json
     *
     * @param request
     * @return
     */
    @RequestMapping("/companyList")
    @ResponseBody
    public ResultVO companyList(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String companyName = request.getParameter("companyName");

        ResultVO resultVO = new ResultVO();//前端需要的试图模型，view object
        resultVO.setCode(1);//状态码 0=正常 1 = err

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(limit)){
            Page pa = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));//设置分页 15条每页

            List<User> companyList;
            if (StringUtils.isNotBlank(companyName)){//传来的公司名不为空，即按简称搜索
                companyList = adminServiceImpl.getCompanyInfoByName(companyName);
                for (int i = 0;i<companyList.size();i++){
                    if (companyList.get(i).getType() == 2){
                        companyList.remove(i);
                    }
                }
            }else {
                if (user.getType() == 2){//超级管理员  能查询包括自己
                    companyList= adminServiceImpl.getAllCompanyInfo();
                }else {//普通管理员 不能查询超级管理员
                    companyList = adminServiceImpl.getAllCompanyInfoExcludeSuper();
                }
            }

            resultVO.setCode(0);//状态码 0=正常封装返回
            PageInfo pageInfo = new PageInfo(pa);
            int total = (int) pageInfo.getTotal();
            resultVO.setCount(total);//分页条数
            resultVO.setMsg("ok");//相应信息
            resultVO.setData(companyList);

        }else {
            resultVO.setMsg("传参不对！");
        }

        return resultVO;
    }

    /**
     * 更新公司 是否启用（就是能不能登录） 不返回
     *
     * @param request
     * @return
     */
    @RequestMapping("updateCompanyEnable")
    @ResponseBody
    public String updateCompanyEnable(HttpServletRequest request) {
        User u = (User)request.getSession().getAttribute("user");
        String id = request.getParameter("id");
        String enable = request.getParameter("Enable");
        Integer en;
        if ("true".equals(enable)) {
            en = 1;
        } else {
            en = 0;
        }
        List<User> users = adminServiceImpl.getCompanyInfoById(id);
        if (users.size() > 0) {
            users.get(0).setState(en);
            adminServiceImpl.updateCompanyEnableById(users.get(0));
            //添加日志记录
            Integer cid = users.get(0).getId();
            String content;
            if (en == 1) {
                content = "开启登录权限 ->" + users.get(0).getCompanyShortName();
            } else {
                content = "关闭登录权限 ->" + users.get(0).getCompanyShortName();
            }
            commonService.addLog(new JTLog(u.getId(), content));
        }
        return "";
    }

    /**
     * 更新公司能不能跨月提交资料，不返回
     *
     * @param request
     * @return
     */
    @RequestMapping("updateCompanyModify")
    @ResponseBody
    public void updateCompanyModify(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        String id = request.getParameter("id");
        String right = request.getParameter("right");
        Integer r;
        if ("true".equals(right)) {
            r = 1;
        } else {
            r = 0;
        }

        List<User> users = adminServiceImpl.getCompanyInfoById(id);
        if (users.size() > 0) {
            users.get(0).setModify(r);
            adminServiceImpl.updateCompanyModifyById(users.get(0));
            //添加日志记录
            Integer cid = users.get(0).getId();
            String content;
            if (r == 1) {
                content = "开启跨月修改权限 -> " + users.get(0).getCompanyShortName();
            } else {
                content = "关闭跨月修改权限 -> " + users.get(0).getCompanyShortName();
            }
            commonService.addLog(new JTLog(user.getId(), content));
        }
        //return "";
    }

    /**
     * 根据公司的id 更新公司的信息
     * @param request
     * @return
     */
    @RequestMapping("updateUser")
    @ResponseBody
    public ResultVO updateUser(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        Enumeration<String> parameterNames = request.getParameterNames();
        String fixed = null;
        String id = null;
        String value = null;
        while (parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            String v = request.getParameter(key);
            if ("id".equals(key)){
                id = v;
            }else {
                fixed = MyUtils.upperCharToUnderLine(key);
                value = v;
            }
        }
        List<User> list = adminServiceImpl.getCompanyInfoById(id);//查询是否存在该id
        if (list.size() > 0){
            try {
                adminServiceImpl.updateCompanyInfo(fixed,value,id);
                resultVO.setCode(1);
                resultVO.setMsg("修改成功");

                //添加日志   修改企业信息 -> 【航达】 company_short_name ：啊啊
                String content = "修改企业信息 -> 【" + list.get(0).getCompanyShortName() + "】 " + fixed + " : " + value;
                commonService.addLog(new JTLog(user.getId(), content));
            }catch (Exception e){
                resultVO.setMsg("修改失败，系统出现错误，联系技术人员处理！");
            }

        }else {
            resultVO.setMsg("修改失败，修改的企业不存在");
        }

        return resultVO;
    }

    /**
     * 根据公司id查询其操作记录
     *
     * @param request
     * @return
     */
    @RequestMapping("showTimeLine")
    public ModelAndView showTimeLine(HttpServletRequest request) {
        String id = request.getParameter("id");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/time-line");

        List<JTLog> resultList = commonService.getLogByCid(Integer.parseInt(id));
        mv.addObject("log", resultList);
        return mv;
    }


    /**
     * 管理员下载文件方法（平面图，产品名录）
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("down")
    @ResponseBody
    public void downFile(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String type = request.getParameter("type");// 1 = 平面图 2 = 产品名录 3 = msds 4 =
        String cid = request.getParameter("cid");//公司的id
        String id = request.getParameter("id");//表中行数据对应的id

        if (StringUtils.isNotBlank(type)){//传来的下载的类型才可以放行
            switch (type){
                case "1":
                    //根据cid查询对应的平面图
                    if (StringUtils.isNotBlank(cid)){
                        List<User> companyInfos = adminServiceImpl.getCompanyInfoById(cid);
                        if (companyInfos.size() > 0){
                            //有这个公司id
                            String src = companyInfos.get(0).getPlaneFigure();
                            System.out.println(companyInfos.get(0)+"==================");
                            MyUtils.downloadFile(response,src,"utf-8");
                        }
                    }

                    break;
                default:
                    //没传对，不处理
                    break;
            }

        }

    }


    /**
     * 显式系统日志首页
     * @param page
     * @return
     */
    @RequestMapping("showLog")
        public ModelAndView showLog (Integer page){
            ModelAndView mv = new ModelAndView();
            //
            if (page == null) page = 1;
            System.out.println(page);
            Page<Object> pa = (Page<Object>) PageHelper.startPage(page, 15);
            List<JTLog> list = adminServiceImpl.getAllLog();
            PageInfo<Object> info = new PageInfo<>(pa);

            mv.addObject("logs", list);
            mv.addObject("page", page);
            mv.addObject("count", info.getTotal());

            mv.setViewName("admin/log");

            return mv;
        }

    /**
     * 获取系统日志信息的分页数据，返回视图模型
     * @param page
     * @return
     */
    @RequestMapping("getLog")
        public ModelAndView getLog (Integer page){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/log-tem");
            if (page == null)
                page = 1;
            PageHelper.startPage(page, 15);
            List<JTLog> list = adminServiceImpl.getAllLog();
            mv.addObject("logs",list);
            return mv;
        }

    /**
     * 跳转到物料申报统计页面
     * @return
     */
    @RequestMapping("toMaterial")
    public ModelAndView toMaterial(){
        ModelAndView mv = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();//获取当前时间
        LocalDateTime last = now.minusMonths(1);//月份-1
        String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 时间格式化 2020-08
        mv.addObject("dateTime",last_month);//回显时间为上个月
        //回显公司列表
        List<User> companys = adminServiceImpl.getAllCompanyInfo();
        System.out.println("==========="+companys);
        mv.addObject("companys",companys);
        //回显物料名
        List<Material> materials = adminServiceImpl.getMaterials();
        mv.addObject("materials",materials);
        mv.setViewName("admin/material");
        return mv;
    }

    /**
     * 查询物料使用情况
     * @param request
     * @return
     */
    @RequestMapping("material")
    @ResponseBody
    public ResultVO material(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String usedTime = request.getParameter("usedTime");
        System.out.println(page);
        System.out.println(limit);
        System.out.println(usedTime);
        if (StringUtils.isBlank(usedTime)){
            LocalDateTime now = LocalDateTime.now();//获取当前时间
            LocalDateTime last = now.minusMonths(1);//月份-1
            String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 时间格式化 2020-08
            Page<Object> p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
            List<MaterialsUsed> list = adminServiceImpl.getMaterialsUsedByTime(last_month);//查出上一个月的所有公司物料使用情况
            resultVO.setCount((int)p.getTotal());
            resultVO.setData(list);
        }
        return resultVO;
    }
}