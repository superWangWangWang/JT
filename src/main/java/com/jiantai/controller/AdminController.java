package com.jiantai.controller;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTLog;
import com.jiantai.entity.Material;
import com.jiantai.service.impl.AdminServiceImpl;
import com.jiantai.vo.VO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
           /* vo.setCode(1);//状态码 0=正常 1 = 异常
            vo.setCount(0);//分页条数
            vo.setMsg("page传值不对");//相应信息*/
            List companyList = adminServiceImpl.getAllCompanyInfo();
            vo.setCode(0);//状态码 0=正常
            vo.setCount(0);// 总数据条数
            vo.setMsg("ok");//相应信息
            vo.setData(companyList);
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

        List<CompanyInfo> companys = adminServiceImpl.getCompanyInfoById(id);
        if (companys.size()>0){
            companys.get(0).setRight(r);
            adminServiceImpl.updateCompanyRightById(companys.get(0));
            //添加日志记录
            Integer cid = companys.get(0).getId();
            String content;
            if (r == 1 ){
                content = "开启跨月修改权限";
            }else {
                content = "关闭跨月修改权限";
            }
            adminServiceImpl.addLog(new JTLog(cid,content));
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
        return mv;
    }

    /**
     * @page 物料管理 material.html
     * @funciton
     */
    @RequestMapping("/searchDeclareRecords")
    @ResponseBody
    public String searchDeclareRecords(HttpServletRequest request) {
        VO vo = new VO();//前端需要的试图模型，view object
        String page = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        String date = request.getParameter("date");
        String company = request.getParameter("company");
        String material = request.getParameter("material");

        System.out.println("公司：" + company + ",日期：" + date + ",物料：" + material);
        if (!StringUtils.isNotBlank(page)) {
            vo.setCode(1);//状态码 0=正常 1 = 异常
            vo.setCount(0);//分页条数
            vo.setMsg("page传值不对");//相应信息
        } else {
            PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(pageSize));//设置分页 15条每页
            List<JTDeclareRecord> records = adminServiceImpl.searchDeclareRecords(date, company, material);
            PageInfo pageInfo = new PageInfo<>(records);
            //封装返回
            vo.setCount((int) pageInfo.getTotal());//分页条数
            vo.setCode(0);//状态码 0=正常
            vo.setMsg("ok");//相应信息
            vo.setData(records);
        }

        return JSON.toJSONString(vo);
    }

    /**
     * @page 物料管理 material.html
     * @funciton
     */
    @GetMapping("/findMaterials")
    @ResponseBody
    public VO findMaterials(HttpServletRequest request) {
        VO vo = new VO();//前端需要的试图模型，view object

        List<Material> records = adminServiceImpl.findMaterials();
        //封装返回
        vo.setCode(0);//状态码 0=正常
        vo.setMsg("ok");//相应信息
        vo.setData(records);
        return vo;
    }
}
