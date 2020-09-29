package com.jiantai.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiantai.entity.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public ModelAndView toIndex(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User user = (User)request.getSession().getAttribute("user");
        mv.addObject("user",user);
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
        ModelAndView mv = new ModelAndView();
        //mv.addObject("data", "aaa");

        mv.setViewName("admin/welcome");
        return mv;
    }

    /**
     * 数据预览
     * @return
     */
    @RequestMapping("preview")
    public ModelAndView preview(){
        ModelAndView mv = new ModelAndView();
        LocalDate now = LocalDate.now();
        now = now.minusMonths(1);
        String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        //取得所有公司账户
        List<User> users = adminServiceImpl.getAllCompanyInfoExcludeSuper();
        ArrayList uList = new ArrayList();
        users.forEach(u->{
            if (u.getState() == 1)
                uList.add(u);
        });
        mv.addObject("users",uList);

        //统计上月物料申报企业个数，
        List<String> list1 = adminServiceImpl.getMaterialsUsedCompanyNameLastMonth(time);
        mv.addObject("total_MaterialsUsed",list1);
        //统计上月物料凭证上传企业个数，
        List<String> list2 = adminServiceImpl.getMaterielsEvidenceCompanyNameLastMonth(time);
        mv.addObject("total_MaterielsEvidence",list2);
        //统计上一个月的提交产品产量的企业个数
        List<String> list3 = adminServiceImpl.getProductsOutputCompanyNameLastMonth(time);
        mv.addObject("total_ProductsOutput",list3);
        //统计上一个月的设备保养记录提交的企业个数
        List<String> list4 = adminServiceImpl.getEquipmentMaintenanceCompanyNameLastMonth(time);
        mv.addObject("total_EquipmentMaintenance",list4);
        //统计获取生产设备提交的企业个数
        List<String> list5 = adminServiceImpl.getEquipmentCompanyName();
        mv.addObject("total_Equipment",list5);
        //统计上传msds的企业个数
        List<String> list6 = adminServiceImpl.getMSDSCompanyName();
        mv.addObject("total_MSDS",list6);

        mv.setViewName("admin/preview");
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
            Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));//设置分页 15条每页

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
            PageInfo pageInfo = new PageInfo(p);
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
     * 新增公司账户
     * @return
     */
    @RequestMapping("companyAdd")
    @ResponseBody
    public ResultVO companyAdd(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        String name = request.getParameter("name");//登录账户 -- 需要判断是否重复
        String pwd = request.getParameter("pwd");//登录密码
        String cname = request.getParameter("cname");//公司全称 -- 需要判断是否重复
        String csname = request.getParameter("csname");//公司简称 -- 需要判断是否重复
        String code = request.getParameter("code");//通行码，没有的话不让添加
        if(!"520520".equals(code)){//暂时写死为520520
            resultVO.setMsg("通行码错误，您没有该权限");
            return resultVO;
        }

        //获取所有的账户信息
        List<User> allCompanyInfo = adminServiceImpl.getAllCompanyInfo();
        for (int i = 0;i<allCompanyInfo.size();i++){
            if (allCompanyInfo.get(i).getUserName().equals(name)){
                resultVO.setMsg("账号已存在，请替换");
                return resultVO;
            }
            if (allCompanyInfo.get(i).getCompanyName().equals(cname)){
                resultVO.setMsg("公司全称已存在，请替换");
                return resultVO;
            }
            if (allCompanyInfo.get(i).getCompanyShortName().equals(csname)){
                resultVO.setMsg("公司简称已存在，请替换");
                return resultVO;
            }
        }
        //通过了，直接添加
        adminServiceImpl.companyAdd(name,pwd,cname,csname);
        resultVO.setMsg("新增成功");
        //添加日志
        User u = (User)request.getSession().getAttribute("user");
        String content = "新增公司账户";
        commonService.addLog(new JTLog(u.getId(), content));
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
     * 管理员下载文件方法（1=平面图，2=产品名录,3=msds,4=物料凭证，5=设备保养记录）
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("down")
    @ResponseBody
    public void downFile(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String type = request.getParameter("type");// 1 = 平面图 2 = 产品名录 3 = msds 4 =物料凭证 5=设备保养记录
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
                case "2":
                    //根据cid查询对应的平面图
                    if (StringUtils.isNotBlank(cid)){
                        List<User> companyInfos = adminServiceImpl.getCompanyInfoById(cid);
                        if (companyInfos.size() > 0){
                            //有这个公司id
                            String src = companyInfos.get(0).getProductionEquipmentList();
                            System.out.println(companyInfos.get(0)+"==================");
                            MyUtils.downloadFile(response,src,"utf-8");
                        }
                    }
                case "3":
                        if (StringUtils.isNotBlank(cid) && StringUtils.isNotBlank(id)){//cid 和 id两个都不为空
                            //根据cid和id查询msds表的src
                            List<Msds> list = adminServiceImpl.getMsdsByIdAndCid(id, cid);
                            if (list.size() > 0 ){//查到了
                                String src = list.get(0).getSrc();
                                MyUtils.downloadFile(response,src,"utf-8");
                            }
                        }
                case "4":
                    if (StringUtils.isNotBlank(cid) && StringUtils.isNotBlank(id)){//cid 和 id两个都不为空
                        //根据cid和id查询msds表的src
                        List<MaterielEvidence> list = adminServiceImpl.getMaterielEvidenceSrc(cid,id);
                        if (list.size() > 0 ){//查到了
                            String src = list.get(0).getSrc();
                            MyUtils.downloadFile(response,src,"utf-8");
                        }
                    }
                case "5":
                    if (StringUtils.isNotBlank(cid) && StringUtils.isNotBlank(id)){//cid 和 id两个都不为空
                        //根据cid和id查询msds表的src
                        List<EquipmentMaintenance> list = adminServiceImpl.getEquipmentMaintenanceSrc(cid,id);
                        if (list.size() > 0 ){//查到了
                            String src = list.get(0).getSrc();
                            MyUtils.downloadFile(response,src,"utf-8");
                        }
                    }
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
//        LocalDateTime now = LocalDateTime.now();//获取当前时间
//        LocalDateTime last = now.minusMonths(1);//月份-1
//        String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 时间格式化 2020-08
//        mv.addObject("dateTime",last_month);//回显时间为上个月
        //回显公司列表
        List<User> companys = adminServiceImpl.getAllCompanyInfo();
        System.out.println("==========="+companys);
        for (int i = 0;i<companys.size();i++){
            if (companys.get(i).getType() != 0){
                companys.remove(i);
            }
        }

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
        String usedTime = request.getParameter("time");
        String material = request.getParameter("material");//id
        String company = request.getParameter("company");//id
        System.out.println(page);
        System.out.println(limit);
        System.out.println(usedTime);
        if (StringUtils.isBlank(usedTime)){
            usedTime = "";
            //LocalDateTime now = LocalDateTime.now();//获取当前时间
            //LocalDateTime last = now.minusMonths(1);//月份-1
            //String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 时间格式化 2020-08
            Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
            //List<MaterialsUsed> list = adminServiceImpl.getMaterialsUsedByTime(last_month);//查出上一个月的所有公司物料使用情况
            List<MaterialsUsed> list = adminServiceImpl.getMaterialsUsedByTime(usedTime,company,material);//查出上一个月的所有公司物料使用情况
            PageInfo pageInfo = new PageInfo(p);
            int total = (int)pageInfo.getTotal();
            resultVO.setCount(total);
            resultVO.setData(list);
        }else {
            if (MyUtils.isValidDate(usedTime)){
                Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
                List<MaterialsUsed> list = adminServiceImpl.getMaterialsUsedByTime(usedTime,company,material);//查出上一个月的所有公司物料使用情况
                PageInfo pageInfo = new PageInfo(p);
                int total = (int)pageInfo.getTotal();
                resultVO.setCount(total);

                resultVO.setData(list);
            }else {
                resultVO.setMsg("时间格式不对");
            }

        }
        return resultVO;
    }

    /**
     * 跳转到msds页面
     * @param request
     * @return
     */
    @RequestMapping("toMsds")
    public ModelAndView toMsds(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //回显公司列表 -仅type = 0
        List<User> companylist = adminServiceImpl.getAllCompanyInfo();
        System.out.println("===========" + companylist);
        for (int i = 0;i<companylist.size();i++){
            if (companylist.get(i).getType() != 0){
                companylist.remove(i);
            }
        }

        mv.addObject("companys",companylist);

        mv.setViewName("admin/msds");
        return mv;
    }

    /**
     * 获取msds数据
     * @param request
     * @return
     */
    @RequestMapping("msds")
    @ResponseBody
    public ResultVO msds(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        //查询msds
        String id = request.getParameter("id");
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        List<Msds> msdsList = adminServiceImpl.getMsds(id);
        PageInfo pageInfo = new PageInfo(p);
        int total = (int)pageInfo.getTotal();
        resultVO.setCount(total);
        resultVO.setCode(0);
        resultVO.setMsg("查询成功");
        resultVO.setData(msdsList);
        return resultVO;
    }

    /**
     * 跳转到产品产量输出页面
     * @return
     */
    @RequestMapping("toOutput")
    public ModelAndView toOutput(){
        ModelAndView mv = new ModelAndView();
        //回显公司列表
        List<User> companys = adminServiceImpl.getAllCompanyInfo();
        System.out.println("==========="+companys);
        for (int i = 0;i<companys.size();i++){
            if (companys.get(i).getType() != 0){
                companys.remove(i);
            }
        }

        mv.addObject("companys",companys);
        mv.setViewName("admin/product-output");
        return mv;
    }

    /**
     * 获取每月产量数据
     * @return
     */
    @RequestMapping("output")
    @ResponseBody
    public ResultVO output(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        String outputTime = request.getParameter("time");//查询的时间
        String companyId = request.getParameter("company");//查询的公司id
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        System.out.println(outputTime+"=========");
        Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        //查询
        List<Product> list = adminServiceImpl.getOutput(outputTime, companyId);
        list.forEach(l->{
            l.setName(l.getFirst() + " -> " + l.getSecond() + " -> " +  l.getName());
        });
        PageInfo pageInfo = new PageInfo(p);
        int total = (int)pageInfo.getTotal();
        resultVO.setCode(0);
        resultVO.setData(list);
        resultVO.setCount(total);
        return resultVO;
    }

    /**
     * 跳转到生产设备页面
     * @return
     */
    @RequestMapping("toEquipment")
    public ModelAndView toEquipment(){
        ModelAndView mv = new ModelAndView();
        //回显公司列表 -仅type = 0
        List<User> companylist = adminServiceImpl.getAllCompanyInfo();
        System.out.println("===========" + companylist);
        for (int i = 0;i<companylist.size();i++){
            if (companylist.get(i).getType() != 0){
                companylist.remove(i);
            }
        }

        mv.addObject("companys",companylist);
        mv.setViewName("admin/equipment-list");
        return mv;
    }

    /**
     * 获取生产设备数据
     * @param request
     * @return
     */
    @RequestMapping("equipment")
    @ResponseBody
    public ResultVO equipment(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cid = request.getParameter("cid");
        String type = request.getParameter("type");
        //根据公司id和type 1=空压机，2=风机，3=电机，查生产设备--没有公司id则查出全部
        Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        List<Equipment> list = adminServiceImpl.getEquipment(cid, type);
        PageInfo pageInfo = new PageInfo(p);
        int total = (int)pageInfo.getTotal();
        resultVO.setCount(total);
        resultVO.setData(list);
        return resultVO;
    }

    /**
     * 跳转到物料凭证列表页面
     * @return
     */
    @RequestMapping("toMaterielEvidence")
    public ModelAndView toMaterielEvidence(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/materiel-evidence");
        //回显公司列表
        List<User> companys = adminServiceImpl.getAllCompanyInfo();
        System.out.println("==========="+companys);
        for (int i = 0;i<companys.size();i++){
            if (companys.get(i).getType() != 0){
                companys.remove(i);
            }
        }

        mv.addObject("companys",companys);
        return mv;
    }

    /**
     * 获取物料凭证上传信息，用于管理员下载
     * @param request
     * @return
     */
    @RequestMapping("materielEvidence")
    @ResponseBody
    public ResultVO materielEvidence(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String time = request.getParameter("time");
        String cid = request.getParameter("cid");
        //查询上传的物料凭证

        Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        List<MaterielEvidence> list = adminServiceImpl.getMaterielEvidence(cid, time);
        PageInfo pageInfo = new PageInfo(p);
        int total = (int)pageInfo.getTotal();
        resultVO.setCount(total);
        resultVO.setData(list);
        return resultVO;
    }

    /**
     * 跳转到设备保养记录页面
     * @return
     */
    @RequestMapping("toEquipmentMaintenance")
    public ModelAndView toEquipmentMaintenance(){
        ModelAndView mv = new ModelAndView();
        //回显公司列表
        List<User> companys = adminServiceImpl.getAllCompanyInfo();
        System.out.println("==========="+companys);
        for (int i = 0;i<companys.size();i++){
            if (companys.get(i).getType() != 0){
                companys.remove(i);
            }
        }

        mv.addObject("companys",companys);
        mv.setViewName("admin/equipment-maintenance");
        return mv;
    }

    /**
     * 获取设备保养记录
     * @param request
     * @return
     */
    @RequestMapping("equipmentMaintenance")
    @ResponseBody
    public ResultVO equipmentMaintenance(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cid = request.getParameter("cid");
        String time = request.getParameter("time");
        //获取列表
        Page p = PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        List<EquipmentMaintenance> equipmentMaintenanceList = adminServiceImpl.getEquipmentMaintenanceList(time, cid);
        PageInfo pageInfo = new PageInfo(p);
        int total = (int)pageInfo.getTotal();
        resultVO.setCount(total);
        resultVO.setData(equipmentMaintenanceList);
        return resultVO;
    }
}