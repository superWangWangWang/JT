package com.jiantai.controller;

import com.jiantai.entity.*;
import com.jiantai.service.impl.CommonServiceImpl;
import com.jiantai.service.impl.UserServiceImpl;
import com.jiantai.utils.MyUtils;
import com.jiantai.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserControll {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommonServiceImpl commonService;
    /**
     * 用户统一登录方法
     * @param user
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultVO login(User user, HttpServletRequest request){
        String userName = user.getUserName();
        String password = user.getPassword();
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        //非空
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)){
            //根据用户名去查询用户
            List<User> users = userService.getUserByUserName(userName);
            if (users.size() > 0){
                //查到了用户名存在
                User u = users.get(0);
                //判断密码是否正确
                if (u.getPassword().equals(password)){
                    //一样

                    HttpSession session = request.getSession();
                    session.setAttribute("user",u);//登录成功将用户对象放进session
                    System.out.println(u);
                    if (u.getType() == 0){
                        vo.setCode(1);
                    }else if (u.getType() == 1){
                        vo.setCode(2);
                    }
                    vo.setMsg("登录成功");
                    String ip = request.getRemoteAddr();
                    if (!"0:0:0:0:0:0:0:1".equals(ip) && !"127.0.0.1".equals(ip)) {//ip不是本地，就记录日志
                        //userServiceImpl.addLog(new JTLog(companyInfo.getId(), " 登录成功 IP：" + request.getRemoteAddr()));
                        //添加日志
                        String content = "登录成功 IP：" + ip;
                        commonService.addLog(new JTLog(u.getId(),content));
                    }


                }else {
                    vo.setMsg("账号密码不一致");
                }
            }else {
                //查不到用户名
                vo.setMsg("用户不存在");
            }
        }else {
            vo.setMsg("账号密码不能为空");
        }
        return vo;
    }
    /**
     * 用户退出
     **/
    @RequestMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
    /**
     * 跳转到用户首页
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/login.html";//这里需要注意的是需要手动添加.html，否则会报404，重定向则会自动去public文件夹下寻找
        }
        if (user.getType() == 1){//type NULL身份 0=普通公司 1 = 超级管理员 可以登录管理后台
            return "admin/index";
        }
        return "user/index";

    }

//    @RequestMapping("toIndex")
//    public ModelAndView toIndex(HttpServletRequest request){
//        ModelAndView mv = new ModelAndView();
//        CompanyInfo companyInfo = (CompanyInfo)request.getSession().getAttribute("LOGIN_USER");
//        if (companyInfo.getType() == 1){
//            //管理员，type = 1
//            mv.setViewName("redirect:/admin/index");
//        }else {
//            //普通公司账户 type =0
//            mv.setViewName("user/index-old");
//        }
//        return mv;
//    }
    /**
     * 欢迎页面
     * @param request
     * @return
     */
    @RequestMapping("welcome")
    public ModelAndView wellcome(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<User> users = userService.getUserById(user.getId());

        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/welcome");
        mv.addObject("user",users.get(0));
        //查询msds上传个数
        List<Msds> msdss = userService.getMsdsByCid(user.getId());
        mv.addObject("msds_number",msdss.size());
        return mv;
    }

    /**
     * 跳转到修改公司详情页面
     * @param request
     * @return
     */
    @RequestMapping("toDetails")
    public ModelAndView updateDetails(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/update-details");
        User user = (User)request.getSession().getAttribute("user");
        mv.addObject("user",user);
        return mv;
    }

    /**
     * 更新公司详情
     * @param u
     * @param request
     * @return
     */
    @RequestMapping("updateDetails")
    @ResponseBody
    public ResultVO updateDetails(User u,HttpServletRequest request){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);//用于前端显示笑脸或者哭脸提示，0=哭，1=笑
        //获取session中的对象跟传来的对象比较，
        User user = (User)request.getSession().getAttribute("user");
        HashMap map = new HashMap<>();
        int state = 0;//用于判断传来的user对象是否修改了session中的user的值，state=0，不用存入库，state=1存入库
        if (u.getId() == user.getId()){

            //逐个判断哪个字段改变了，便于更清晰记录日志
            if (StringUtils.isNotBlank(u.getCompanyShortName()) && !u.getCompanyShortName().equals(user.getCompanyShortName())){//简称
                state = 1;
                map.put("简称:" + user.getCompanyShortName(),u.getCompanyShortName());
                user.setCompanyShortName(u.getCompanyShortName());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (StringUtils.isNotBlank(u.getCompanyName()) && !u.getCompanyName().equals(user.getCompanyName())){//全称
                state = 1;
                map.put("全称:" + user.getCompanyName(),u.getCompanyName());
                user.setCompanyName(u.getCompanyName());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getLegalPerson().equals(user.getLegalPerson())){//法人
                state = 1;
                map.put("法人:" + user.getLegalPerson(),u.getLegalPerson());
                user.setLegalPerson(u.getLegalPerson());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getAddress().equals(user.getAddress())){//地址
                state = 1;
                map.put("地址" + user.getAddress(),u.getAddress());
                user.setAddress(u.getAddress());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getTelephone().equals(user.getTelephone())){//固话
                state = 1;
                map.put("固话:" + user.getTelephone(),u.getTelephone());
                user.setTelephone(u.getTelephone());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getFax().equals(user.getFax())){//传真
                state = 1;
                map.put("传真:" + user.getFax(),u.getFax());
                user.setFax(u.getFax());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getContacts().equals(user.getContacts())){//联系人
                state = 1;
                map.put("联系人:" + user.getContacts(),u.getContacts());
                user.setContacts(u.getContacts());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getContactsMobilePhone().equals(user.getContactsMobilePhone())){//联系人手机号
                state = 1;
                map.put("联系人手机号:" + user.getContactsMobilePhone(),u.getContactsMobilePhone());
                user.setContactsMobilePhone(u.getContactsMobilePhone());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getSocialCreditCode().equals(user.getSocialCreditCode())){//社会信用码
                state = 1;
                map.put("社会信用码:" + user.getSocialCreditCode(),u.getSocialCreditCode());
                user.setSocialCreditCode(u.getSocialCreditCode());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (u.getEmployees() != user.getEmployees()){//员工数
                state = 1;
                map.put("员工数:" + user.getEmployees(),u.getEmployees());
                user.setEmployees(u.getEmployees());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getOpeningTime().equals(user.getOpeningTime())){//开业时间
                state = 1;
                map.put("开业时间:" + user.getOpeningTime(),u.getOpeningTime());
                user.setOpeningTime(u.getOpeningTime());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getProductType().equals(user.getProductType())){//产品类型
                state = 1;
                map.put("产品类型:" + user.getProductType(),u.getProductType());
                user.setProductType(u.getProductType());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getLocation().equals(user.getLocation())){//园区位置
                state = 1;
                map.put("园区位置:" + user.getLocation(),u.getLocation());
                user.setLocation(u.getLocation());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }
            if (!u.getRemarks().equals(user.getRemarks())){//备注
                state = 1;
                map.put("备注:" + user.getRemarks(),u.getRemarks());
                user.setRemarks(u.getRemarks());//将传来的用户对象中的值，替换掉session取出的用户值，用于dao层操作
            }

        }
        if (state == 1){
            //存入数据库
            userService.updateDetails(user);
            resultVO.setCode(1);
            resultVO.setMsg("修改成功");
            //修改成功后存user对象进session
            request.getSession().setAttribute("user",user);
            //记录日志
            String logs = map.toString();
            logs = "更新企业详情信息：" + logs;
            //logs = "【" + user.getCompanyShortName() + "】更新企业详情信息：" + logs;
            logs = logs.replaceAll("="," -> ");
            commonService.addLog(new JTLog(user.getId(),logs));
        }else {
            resultVO.setMsg("修改失败，原因：未做任何修改");
        }
        return resultVO;
    }

    /**
     * 上传文件接口，type: 1=平面图 2=产品目录 3=MSDS 4=物料凭证
     * 文件保存在 D://upload/declare/ 下
     * @param type
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public ResultVO upload(Integer type, MultipartFile file,HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);//0=上传成功，1=上传失败
        String filePath = "D://upload/declare/";//上传文件存放的磁盘位置
        User user = (User)request.getSession().getAttribute("user");
        String field = "";//字段名
        String materials = "";//物料名称
        String path = "";//完整的文件路径
        String dateTime = request.getParameter("dateTime");//传来的时间 2020-02

        if (file.isEmpty()) {
            resultVO.setMsg("上传失败，文件为空");
        }else {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//文件后缀，已经带 点
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + suffix;//文件名用年月日时分秒替换


            if (type == 1){//平面图类型
                field = "plane_figure";
                filePath = filePath + "plane_figure/";
            }else if (type == 2){//产品目录
                field = "product_list";
                filePath = filePath + "product_list/";
            }else if (type == 3){//msds
                materials = request.getParameter("name");
                if (StringUtils.isBlank(materials)){
                    resultVO.setMsg("上传失败：请选择物料！");
                    return resultVO;
                }
                //System.out.println(materials);
                //field = "MSDS";
                filePath = filePath + "MSDS/";
            }else if (type == 4){//物料凭证

                if (StringUtils.isNotBlank(dateTime)){//判断有没有传来时间
                    if (!MyUtils.isValidDate(dateTime)){//判断是不是时间格式
                        resultVO.setMsg("上传失败：时间格式不对！");
                        return resultVO;
                    }

                }else {
                    resultVO.setMsg("上传失败：请选择时间日期");
                    return resultVO;
                }

                //field = "materiels_evidence";这是数据库字段 不需要的
                filePath = filePath + "materiels_evidence/";


            }else {
                resultVO.setMsg("上传失败：请勿进行非法操作！");
                return resultVO;
            }

            if (StringUtils.isNotBlank(user.getCompanyShortName())){
                path = filePath + user.getCompanyShortName() + "/";//完整的文件路径--有简称的公司会生成文件夹存放，便于查询
                if (type == 3){
                    path =  path + materials + "/";
                }else if (type == 4){
                    path =  path + dateTime + "/";
                }

                path = path + fileName;
            }
            System.out.println("上传的位置|"+path);

            MyUtils.mkdir(path);//处理一下，防止文件夹不存在报错，
            File dest = new File(path);
            try {
                file.transferTo(dest);//上传到文件夹
                resultVO.setMsg("上传成功");
                //添加日志
                String content = "";

                switch (type){
                    case 1:
                        userService.uploadFile(field,path,user.getId());//保存路径到数据库   字段，值，id
                        user.setPlaneFigure(path);
                        //添加日志
                        content = "上传平面图";
                        commonService.addLog(new JTLog(user.getId(),content));
                        break;
                    case 2:
                        userService.uploadFile(field,path,user.getId());//保存路径到数据库   字段，值，id
                        user.setProductList(path);
                        //添加日志
                        content = "上传产品目录";
                        commonService.addLog(new JTLog(user.getId(),content));
                        break;
                    case 3:
                        //先查msds上传过没，有就更新
                        List<Msds> msdss = userService.getMsdsByCidAndName(user.getId(),materials);
                        if (msdss.size() > 0){
                            userService.updateMsdsById(path,msdss.get(0).getId());
                            //添加日志
                            content = "更新MSDS -> " + materials;
                            commonService.addLog(new JTLog(user.getId(),content));
                        }else {
                            userService.saveMsds(user.getId(),materials,path);
                            //添加日志
                            content = "上传MSDS -> " + materials;
                            commonService.addLog(new JTLog(user.getId(),content));
                        }
                        break;
                    case 4:
                        //时间对了，查询数据库该时间下有没有上传过,没有传过，直接写进数据库，上传过，就更新数据库src
                        List<Evidence> evidences = userService.getMaterielsEvidenceByCid(user.getId(), dateTime);
                        if (evidences.size() == 0){
                            //不存在，直接插入
                            userService.saveMaterielsEvidence(user.getId(),path,dateTime);
                            //添加日志
                            content = "上传物料凭证 -> " + dateTime;
                            commonService.addLog(new JTLog(user.getId(),content));
                        }else {
                            //已存在，更新
                            userService.updateMaterielsEvidence(path,user.getId(),dateTime);
                            resultVO.setMsg("更新成功");
                            //添加日志
                            content = "更新物料凭证 -> " + dateTime;
                            commonService.addLog(new JTLog(user.getId(),content));
                        }
                        break;
                }
                request.getSession().setAttribute("user",user);//因为其他地方需要用到session，这里就更新一下session
            } catch (IOException e) {
                resultVO.setMsg("上传失败，服务器出现错误，联系工作人员");
                System.out.println(e);
                return resultVO;
            }
        }
        return resultVO;
    }
    @RequestMapping("download")
    public void download(Integer type, HttpServletResponse response,HttpServletRequest request){
        //查询数据库目前的最新文件路径
        User user = (User)request.getSession().getAttribute("user");
        List<User> users = userService.getUserById(user.getId());
        String path = "";
       switch (type){
           case 1:
                path = users.get(0).getPlaneFigure();
               break;
           case 2:
               path = users.get(0).getProductList();
               break;
           case 3:
               String name = request.getParameter("name");
               if (StringUtils.isNotBlank(name)){
                   List<Msds> list = userService.getMsdsByCidAndName(user.getId(), name);
                   path = list.get(0).getSrc();
               }else {
                   path = "";
               }
               break;
           case 4://下载物料凭证
              if (StringUtils.isNotBlank(request.getParameter("id"))){
                  Integer id = Integer.parseInt(request.getParameter("id"));
                  //查有没有该id的数据
                  List<Evidence> list = userService.getMaterielsEvidenceById(id);
                  if (list.size() > 0){
                      path = list.get(0).getSrc();
                  }
              }
              break;
       }

       if (path != ""){
           MyUtils.downloadFile(response,path,"utf-8");
       }

    }
    @RequestMapping("toMsds")
    public ModelAndView toMsds(Integer id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/msds");
        //查询物料表，排除已经上传的
        //PageHelper.startPage(1,10);
        List<Material> materials = userService.getMaterialByIdExcludeUploaded(id);
        mv.addObject("materials",materials);
        //查询已上传的物料
        List<Msds> list = userService.getMsdsByCid(id);
        mv.addObject("msds_list",list);
        return mv;
    }
    @RequestMapping("materielData")
    public ModelAndView materielData(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/materie-data");
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        //查询物料表，排除已经上传的
        List<Material> materials_list = userService.getMaterialsExcludeRememberByCid(id);
        mv.addObject("materials_list",materials_list);
        List<Material> materials_remember = userService.getMaterialsRememberByCid(id);
        mv.addObject("materials_remember",materials_remember);
        String dateTime = request.getParameter("dateTime");
        if (StringUtils.isNotBlank(dateTime)){
            //查询是否已上传该月的凭证
            List<Evidence> evidence = userService.getMaterielsEvidenceByCid(id, dateTime);
            if (evidence.size() > 0){
                mv.addObject("evidence_down",evidence.get(0));
                mv.addObject("evidence_update",evidence.get(0));
            }else {
                mv.addObject("evidence_upload",evidence);
            }
            //根据时间查询该月物料用量，用于数据回显
            List<Material> list = userService.getMaterialsUsedByUsedTime(id, dateTime);
            if (list.size()>0){

                for (Material m1 : materials_remember){
                    for(Material m2 : list){
                        if (m1.getName().equals(m2.getName())){
                            m1.setUsed(m2.getUsed());
                        }
                    }
                }
            }
                mv.addObject("materials_remember",materials_remember);
        }else {
            dateTime = "";
        }
        mv.addObject("dateTime",dateTime);
        return mv;
    }

    /**
     * 添加物料到物料记忆表
     * @param request
     * @param material
     * @return
     */
    @RequestMapping("materialRemember")
    public String materialRemember(HttpServletRequest request,String material){
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        if (StringUtils.isNotBlank(material)){
            //根据传来的物料名称查询物料记忆表
            List<MaterialRemember> list = userService.getMaterialsRememberByCidAndName(id, material);
            if (list.size() == 0 ){
                //不存在该物料，直接添加
                userService.saveMaterialsRememberByCidAndName(id,material);
            }else {
                //已经添加过了，将他state置1
                userService.changeMaterialsRememberStateByCidAndName(1,id,material);
            }
            //添加日志
            String content = "添加物料 -> " + material;
            commonService.addLog(new JTLog(user.getId(),content));
        }

        return "redirect:materielData";
    }
    @RequestMapping("deleteMaterialRemember")
    @ResponseBody
    public ResultVO deleteMaterialRemember(String name,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        //先查一下，存在则置为0，不存在则报错
        if (StringUtils.isNotBlank(name)) {
            List<MaterialRemember> list = userService.getMaterialsRememberByCidAndName(id, name);
            if (list.size() > 0){
                userService.changeMaterialsRememberStateByCidAndName(0,id,name);
                vo.setCode(1);
                vo.setMsg("删除成功");
            }else {
                //查不到，即虽然传来的物料名正确，但是表中没有，没有的不会回显，但是却传来了，非法操作！
                vo.setMsg("删除失败！请勿进行非法操作！");
            }

        }else {
            vo.setMsg("删除失败！原因参数异常");
        }
        return vo;
    }
    @RequestMapping("saveMaterielUsed")
    @ResponseBody
    public ResultVO saveMaterielUsed(HttpServletRequest request){
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        HashMap<String, Double> map = new HashMap<>();
        String usedTime = "";//物料使用的 年月 时间

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();//键
            String parameter = request.getParameter(key);//值
            if ("dateTime".equals(key)){//键是  dateTime
                if (StringUtils.isBlank(parameter)){
                    vo.setMsg("请选择对应的时间");
                    return vo;
                }else {
                    usedTime = parameter;//传来的时间不为空
                }
            }else {
                Double value;
                if (StringUtils.isBlank(parameter)){
                    parameter = "0";
                }
                value = Double.parseDouble(parameter);
                map.put(key,value);
            }
        }
        if (!MyUtils.isValidDate(usedTime)){
            //不是时间格式 2020-08
            vo.setMsg("时间格式不对");
            return vo;
        }
        //空时间的上面已经处理了，下面的代码是上传来的时间不为空
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();

        String finalUsedTime = usedTime;
        map.forEach((k, v)->{
            System.out.println(k);
            System.out.println(v);
            //先查该年月有没有对应的数据，没有就插入，有就更新
            List<MaterialsUsed> list = userService.getMaterialsUsedByCidAndUsedTimeAndName(id, finalUsedTime, k);
            if (list.size()> 0){
                //数据库中存在了该公司该物料的该年月的使用情况，更新
                // 要判断，20天前的可以更新，且有更新资格才行



                userService.updateMaterialsUsed(v,id,k,finalUsedTime);
                vo.setCode(1);
                vo.setMsg(finalUsedTime + "数据" + "更新成功");
            }else {
                //查不到结果，直接插入数据
                userService.saveMaterialsUsed(id,k,v,finalUsedTime);
                vo.setCode(1);
                vo.setMsg(finalUsedTime + "数据" + "提交成功");
            }

        });
        if (map.size() == 0 ){
            vo.setMsg("请添加物料后再提交");
        }
        return vo;
    }
    @RequestMapping("productData")
    public ModelAndView productData(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/product-data");
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        //查询生产 产品种类表
        List<Product> productsList = userService.getProductsList();
        mv.addObject("products",productsList);
        String dateTime = request.getParameter("dateTime");
        if (StringUtils.isNotBlank(dateTime)){
            //传来的时间不为空
            if (MyUtils.isValidDate(dateTime)){
                //正常时间，回显数据
                List<Product> productsOutput = userService.getProductsOutput(id, dateTime);
                productsList.forEach(l->{
                    productsOutput.forEach(o->{
                        if (l.getId() == o.getId())
                            l.setOutput(o.getOutput());
                    });
                });
            }
        }
        mv.addObject("dateTime",dateTime);

        return mv;
    }
    @RequestMapping("saveProductOutput")
    @ResponseBody
    public ResultVO saveProductOutput(HttpServletRequest request){
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();

        String dateTime = request.getParameter("dateTime");
        if (StringUtils.isBlank(dateTime)){
            vo.setMsg("请选择日期");
            return vo;
        }
        Map<String,Double> map = new HashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String k = parameterNames.nextElement();
            String v = request.getParameter(k);
            if ("dateTime".equals(k)){
                dateTime = v;
            }else {
                Double value = 0d;
                if (StringUtils.isNotBlank(v)) {
                    value = Double.parseDouble(v);
                }
                map.put(k,value);
            }
        }
        System.out.println(map);
        //查询该月是否提交了，提交了就更新
        String finalDateTime = dateTime;
        List<Product> productsOutput = userService.getProductsOutput(id, finalDateTime);
        if (productsOutput.size() == 0){
            map.forEach((k, v)->{
                userService.saveProductsOutput(id,k,v,finalDateTime);
                vo.setCode(1);
                vo.setMsg("保存成功");
            });
        }else {
            //该月有申报，更新
            map.forEach((k, v)->{
                //查一下，没有该产品就插入，有就更新 -- 因为后面新增产品的话，新增的没法赋值
                List<Product> productsOutputByPid = userService.getProductsOutputByPid(id, finalDateTime, k);
                if (productsOutputByPid.size()>0){
                    userService.updateProductsOutput(v,finalDateTime,id,k);
                }else {
                    //插入
                    userService.saveProductsOutput(id,k,v,finalDateTime);
                }
                vo.setCode(1);
                vo.setMsg("更新成功");

            });
        }
        return vo;
    }
    @RequestMapping("equipmentList")
    public ModelAndView equipmentList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/equipment-list");
        //模板赋值
        return mv;
    }
//    @RequestMapping("equipmentDetail")
//    @ResponseBody
//    public ResultVO equipmentDetail(HttpServletRequest request){
//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        Equipment equipment = new Equipment();
//        equipment.setKinds("型号");
//        equipment.setTotal(100);
//
//        Equipment equipment2 = new Equipment();
//        equipment2.setKinds("型号");
//        equipment2.setTotal(100);
//
//        List list = new ArrayList();
//        list.add(equipment);
//        list.add(equipment2);
//        resultVO.setData(list);
//
//
//
//        return resultVO;
//    }
    @RequestMapping("equipment")
    public ModelAndView equipment(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type)){
            switch (type){
                case "1":
                    //添加空压机
                    mv.setViewName("user/machine-air");
                    break;
            }
        }
        return mv;
    }
    @RequestMapping("addEquipment")
    @ResponseBody
    public ResultVO addEquipment(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);//0=失败
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();

        ArrayList<Double> air = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            System.out.println(key);
            System.out.println(value);
            if (StringUtils.isBlank(value)){
                resultVO.setMsg("添加失败，传入参数不对！");
                return resultVO;
            }
            air.add(Double.parseDouble(value));
            //userService.addEquipmentAir();
        }
        if ("1".equals(request.getParameter("type"))){//新增空压机
            Integer type = 1;
            userService.addEquipmentAir(id,type,air.get(0),air.get(1),air.get(2),air.get(3),air.get(4),air.get(5),air.get(6),air.get(7),air.get(8));
            resultVO.setCode(1);
            resultVO.setMsg("空压机添加成功！");
        }


        //System.out.println(equipment);
        return resultVO;
    }
}
