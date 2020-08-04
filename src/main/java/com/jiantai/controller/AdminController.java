package com.jiantai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTLog;
import com.jiantai.vo.VO;
import com.jiantai.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    /**
     * 查询所有公司详情，展示列表，返回json
     * @param request
     * @return
     */
    @RequestMapping("/companyList")
    @ResponseBody
    public String companyList(HttpServletRequest request){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        VO vo = new VO();//前端需要的试图模型，view object
        String companyName = request.getParameter("companyName");

        System.out.println(companyName+"-------======");
        if (companyName != "" && companyName != null){
            //根据公司名查询公司信息
            Page pa = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));//设置分页 15条每页
            List company = adminServiceImpl.getCompanyInfoByName(companyName);
            if (company.size()>0){
                //封装返回
                vo.setCode(0);//状态码 0=正常
                PageInfo pageInfo = new PageInfo(pa);
                int total = (int)pageInfo.getTotal();
                vo.setCount(total);//分页条数
                vo.setMsg("ok");//相应信息

                vo.setData(company);
                return JSON.toJSONString(vo);
            }else {
                vo.setCode(1);//状态码 0=正常 1 = 异常
                vo.setCount(0);//分页条数
                vo.setMsg("page传值不对");//相应信息
                return JSON.toJSONString(vo);
            }
        }

        if (null == page || "".equals(page)){
            //封装返回
            vo.setCode(1);//状态码 0=正常 1 = 异常
            vo.setCount(0);//分页条数
            vo.setMsg("page传值不对");//相应信息
        }else {//页码传入正常
            Page pa = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));//设置分页 15条每页
            List companyList = adminServiceImpl.getAllCompanyInfo();
            vo.setCode(0);//状态码 0=正常封装返回
            PageInfo pageInfo = new PageInfo(pa);
            int total = (int)pageInfo.getTotal();
            vo.setCount(total);//分页条数
            vo.setMsg("ok");//相应信息

            vo.setData(companyList);
        }

        return JSON.toJSONString(vo);
    }

    /**
     * 更新公司 是否启用（就是能不能登录） 不返回
     * @param request
     * @return
     */
    @RequestMapping("updateCompanyEnable")
    @ResponseBody
    public String updateCompanyEnable(HttpServletRequest request){
        String id = request.getParameter("id");
        String enable = request.getParameter("Enable");
        Integer en;
        if ("true".equals(enable)){
            en = 1;
        }else {
            en = 0;
        }
        System.out.println(request.getParameter("id"));
        System.out.println(request.getParameter("Enable"));
        List<CompanyInfo> companys = adminServiceImpl.getCompanyInfoById(id);
        if (companys.size()>0){
            companys.get(0).set是否启用(en);
            adminServiceImpl.updateCompanyEnableById(companys.get(0));
            //添加日志记录
            Integer cid = companys.get(0).getId();
            String content;
            if (en == 1 ){
                content = "开启登录权限";
            }else {
                content = "关闭登录权限";
            }
            adminServiceImpl.addLog(new JTLog(cid,content));
        }else {
            
        }
        return "";
    }

    /**
     * 更新公司能不能跨月提交资料，不返回
     * @param request
     * @return
     */
    @RequestMapping("updateCompanyRight")
    @ResponseBody
    public String updateCompanyRight(HttpServletRequest request){
        String id = request.getParameter("id");
        String right = request.getParameter("right");
        Integer r;
        if ("true".equals(right)){
            r = 1;
        }else {
            r = 0;
        }

        //System.out.println(request.getParameter("id"));
        //System.out.println(request.getParameter("right"));
        List<CompanyInfo> companys = adminServiceImpl.getCompanyInfoById(id);
        if (companys.size()>0){
            companys.get(0).setRight(r);
            adminServiceImpl.updateCompanyRightById(companys.get(0));
            System.out.println(companys);
            //添加日志记录
            Integer cid = companys.get(0).getId();
            String content;
            if (r == 1 ){
                content = "开启跨月修改权限";
            }else {
                content = "关闭跨月修改权限";
            }
            adminServiceImpl.addLog(new JTLog(cid,content));
        }else {

        }
        return "";
    }

    /**
     * 根据公司的id 更新公司的信息 不返回
     * @param companyInfo
     * @return
     */
    @RequestMapping("updateCompanyInfo")
    @ResponseBody
    public String updateCompanyInfoById(CompanyInfo companyInfo){

        System.out.println(companyInfo);
        adminServiceImpl.updateCompanyInfoById(companyInfo);
        return "";
    }

    /**
     * 根据公司id查询其操作记录
     * @param request
     * @return
     */
    @RequestMapping("showTimeLine")
    public ModelAndView showTimeLine(HttpServletRequest request){
        String id = request.getParameter("id");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/time-line");

        List<JTLog> resultList = adminServiceImpl.getLogByCid(Integer.parseInt(id));
        mv.addObject("log",resultList);
        System.out.println(resultList);
        return mv;
    }
}
