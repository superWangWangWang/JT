package com.jiantai.controller;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.entity.*;
import com.jiantai.service.impl.AdminServiceImpl;
import com.jiantai.utils.DownloadUtil;
import com.jiantai.vo.VO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        /*List<String []> list = new ArrayList<>();
        String [] arr = new String[3];
        String [] arr2 = new String[3];
        arr[0] = "1";
        arr[1] = "2";
        arr[2] = "3";

        arr2[0] = "4";
        arr2[1] = "5";
        arr2[2] = "6";
        list.add(arr);
        list.add(arr2);

        vo.setData(list);*/
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
    /**
     * @page 物料导出数据 material.html
     * @funciton
     */
    @PostMapping("/exportToMaterial")
    @ResponseBody
    public VO exportToMaterial(HttpServletRequest request) {
        VO vo = new VO();//前端需要的试图模型，view object
        String date = request.getParameter("date");
        String company = request.getParameter("company");
        String material = request.getParameter("material");
        //System.out.println(date + "," + company + "," + material);
        Map<String, Object> exportInfos = adminServiceImpl.searchExprotDeclareRecords(date, company, material);
        vo.setData(exportInfos);
        return vo;
    }


    /**
     * @page 根据类型查询已上传文件 file.html
     * @funciton
     */
    @GetMapping("/findFileByType")
    @ResponseBody
    public VO findFileByType(HttpServletRequest request) {
        VO vo = new VO();//前端需要的试图模型，view object
        String type = request.getParameter("type");//type = 平面图 生产设备名录 MSDS 佐证
        String page = request.getParameter("page");
        String pageSize = request.getParameter("limit");

        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(pageSize));//设置分页
        if(StringUtils.isNotBlank(type)){
            if("plan".equals(type)){//选择类型为平面图
                List<CompanyInfo> companyInfos = adminServiceImpl.findExistPlanName();//根据plan_name字段不为空的条件查询公司信息
                PageInfo pageInfo = new PageInfo<>(companyInfos);
                //封装返回
                vo.setCount((int) pageInfo.getTotal());//所有数据数量
                vo.setData(companyInfos);
            } else if("product".equals(type)){//选择类型为产品
                List<CompanyInfo> companyInfos = adminServiceImpl.findExistProductName();//根据product_name字段不为空的条件查询公司信息
                PageInfo pageInfo = new PageInfo<>(companyInfos);
                //封装返回
                vo.setCount((int) pageInfo.getTotal());//所有数据数量
                vo.setData(companyInfos);
            } else if("msds".equals(type)){//选择类型为产品
                String company = request.getParameter("company");
                String material = request.getParameter("material");
                List<JTMsdsUpload> jtMsdsUploads = adminServiceImpl.findMsdsUpload(company, material);
                PageInfo pageInfo = new PageInfo<>(jtMsdsUploads);
                //封装返回
                vo.setCount((int) pageInfo.getTotal());//所有数据数量
                vo.setData(jtMsdsUploads);
            } else if("evidence".equals(type)){//选择类型为产品
                String company = request.getParameter("company");
                String material = request.getParameter("material");
                String datetime = request.getParameter("date");
                List<JtMaterialEvidence> jtMaterialEvidences = adminServiceImpl.findEvidenceUpload(company,datetime);
                PageInfo pageInfo = new PageInfo<>(jtMaterialEvidences);
                //封装返回
                vo.setCount((int) pageInfo.getTotal());//所有数据数量
                vo.setData(jtMaterialEvidences);
            }
        }

        vo.setCode(0);//状态码 0=正常
        vo.setMsg("ok");//相应信息
        return vo;
    }

    //下载文件接口
    @GetMapping("/downloadFile")
    @ResponseBody
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取路径和文件名
        String folder = request.getParameter("folder");//文件夹名称
        String id = request.getParameter("id");//物料名
        String localPath = "D:\\upload\\" + folder + "\\";
        String filename_str = "";
        CompanyInfo companyInfo = adminServiceImpl.getCompanyInfoById(id).get(0);//获取登录用户
        if ("msds".equals(folder)) {//MSDS目录
            JTMsdsUpload jtMsdsUpload = adminServiceImpl.findMsdsUploadById(id);//根据id查询msds
            filename_str = jtMsdsUpload.getMsdsFilename();
            localPath += filename_str;
        } else if ("plan".equals(folder)) {//平面图目录
            filename_str = companyInfo.getPlanName();
            localPath += filename_str;
        } else if ("evidence".equals(folder)) {//佐证文件目录
            JtMaterialEvidence jtMaterialEvidence = adminServiceImpl.findJtMaterialEvidenceById(id);
            filename_str = jtMaterialEvidence.getFilename();
            localPath += filename_str;
        } else if ("product".equals(folder)) {//产品设备文件目录
            filename_str = companyInfo.getProductName();
            localPath += filename_str;
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename_str);
        File file = new File(localPath);
        DownloadUtil.downloadFile(file, response);
        System.out.println("下载目录文件为:" + localPath);
        return null;
    }
}
