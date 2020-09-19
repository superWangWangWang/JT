package com.jiantai.controller;

import com.jiantai.entity.*;
import com.jiantai.service.impl.CommonServiceImpl;
import com.jiantai.service.impl.UserServiceImpl;
import com.jiantai.utils.MyUtils;
import com.jiantai.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommonServiceImpl commonService;

    /**
     * 修改密码页面
     * @return
     */
    @RequestMapping("toChangePassword")
    public String toChangePassword(){
        return "user/change-password";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("changePassword")
    @ResponseBody
    public ResultVO changePassword(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        User user = (User)request.getSession().getAttribute("user");

        resultVO.setCode(0);
        String oldPwd = request.getParameter("old");
        String newPwd = request.getParameter("new");
        String reNewPwd = request.getParameter("reNew");
        if (!newPwd.equals(reNewPwd)){
            resultVO.setMsg("两次新密码不一致");
            return resultVO;
        }
        //查询数据库
        List<User> list = userService.getUserById(user.getId());
        String password = list.get(0).getPassword();
        if (!password.equals(oldPwd)){
            resultVO.setMsg("旧密码错误");
            return resultVO;
        }
        //修改
        userService.changePwd(newPwd,user.getId());
        resultVO.setCode(1);
        resultVO.setMsg("修改成功");
        user.setPassword(newPwd);
        request.getSession().setAttribute("user",user);
        //添加日志
        String content = "修改密码";
        commonService.addLog(new JTLog(user.getId(),content));
        return resultVO;
    }

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
                    }else {
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
     * 上传文件接口，type: 1=平面图 2=产品目录 3=MSDS 4=物料凭证 5=保养记录表
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
        User u = (User)request.getSession().getAttribute("user");

        List<User> users = userService.getUserById(u.getId());
        User user = users.get(0);

        String field = "";//字段名
        String materials = "";//物料名称
        String path = "";//完整的文件路径
        String dateTime = request.getParameter("dateTime");//传来的时间 2020-02

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last = now.minusMonths(1);
        String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 2020-08
        int day = now.getDayOfMonth();//获取 日
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
                field = "production_equipment_list";
                filePath = filePath + "production_equipment_list/";
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
                System.out.println("进来了");
                if (day <= 20 && dateTime.equals(last_month)){//传来的时间是上一个月的
                    //当月20日前，可以修改/提交上一个月的

                }else{
                    //当月20号后，不能上传

                    if (user.getModify() == 0) {
                        resultVO.setMsg("上传失败！原因：只能于本月21号前上传上一个月的数据【联系建泰办公室取得权限即可】");
                        return resultVO;
                    }
                }
                //field = "materiels_evidence";这是数据库字段 不需要的
                filePath = filePath + "materiels_evidence/";
            }else if (type == 5){//设备运行及保养记录
                if (StringUtils.isNotBlank(dateTime)){//判断有没有传来时间
                    if (!MyUtils.isValidDate(dateTime)){//判断是不是时间格式
                        resultVO.setMsg("上传失败：时间格式不对！");
                        return resultVO;
                    }
                }else {
                    resultVO.setMsg("上传失败：请选择时间日期");
                    return resultVO;
                }
                System.out.println("进来了");
                if (day <= 20 && dateTime.equals(last_month)){//传来的时间是上一个月的
                    //当月20日前，可以修改/提交上一个月的
                }else{
                    //当月20号后，不能上传
                    if (user.getModify() == 0) {
                        resultVO.setMsg("上传失败！原因：只能于本月21号前上传上一个月的数据【联系建泰办公室取得权限即可】");
                        return resultVO;
                    }
                }
                //field = "materiels_evidence";这是数据库字段 不需要的
                filePath = filePath + "equipment_maintenance/";
            }else {
                resultVO.setMsg("上传失败：请勿进行非法操作！");
                return resultVO;
            }

            if (StringUtils.isNotBlank(user.getCompanyShortName())){
                path = filePath + user.getCompanyShortName() + "/";//完整的文件路径--有简称的公司会生成文件夹存放，便于查询
                if (type == 3){
                    path =  path + materials + "/";
                }else if (type == 4){
                    String[] d = dateTime.split("-");//2020-01  拆分年月保存
                    path =  path + d[0] +"/" + d[1] + "/";
                    //path =  path + dateTime + "/";
                } else if (type == 5){
                    String[] d = dateTime.split("-");
                    path =  path + d[0] +"/" + d[1] + "/";
                    //path =  path + dateTime + "/";
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
                        System.out.println(field);
                        System.out.println(path);
                        System.out.println(user.getId());
                        userService.uploadFile(field,path,user.getId());//保存路径到数据库   字段，值，id
                        user.setProductionEquipmentList(path);
                        //添加日志
                        content = "上传产品目录";
                        commonService.addLog(new JTLog(user.getId(),content));
                        break;
                    case 3:
                        //先查msds上传过没，有就更新
                        List<Msds> msdss = userService.getMsdsExist(user.getId(),materials);
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
                        resultVO.setCode(0);
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
                        resultVO.setCode(0);
                        break;
                    case 5:
                        //查询保养表，如果不存在就直接插入，存在就更新
                        List<EquipmentMaintenance> list = userService.getEquipmentMaintenance(user.getId(), dateTime);
                        System.out.println(list);
                        if (list.size() > 0){
                            //已经有了，更新
                            userService.updateEquipmentMaintenanceById(path,list.get(0).getId());
                            //添加日志
                            content = "重传保养记录 -> " + dateTime;
                            commonService.addLog(new JTLog(user.getId(),content));
                        }else {
                            //还没有，直接插入
                            userService.saveEquipmentMaintenance(user.getId(),dateTime,path);
                            //添加日志
                            content = "上传保养记录 -> " + dateTime;
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

    /**
     * 下载 1=平面图，2=产品名录，3=msds，4=下载物料凭证
     * @param type
     * @param response
     * @param request
     */
    @RequestMapping("download")
    public void download(Integer type, HttpServletResponse response,HttpServletRequest request){
        //查询数据库目前的最新文件路径
        User user = (User)request.getSession().getAttribute("user");
        List<User> users = userService.getUserById(user.getId());
        String path = "";
       switch (type){
           case 1://平面图
                path = users.get(0).getPlaneFigure();
               break;
           case 2://产品名录
               path = users.get(0).getProductionEquipmentList();
               break;
           case 3://msds
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
           case 5:
               //设备运行及保养记录
               String time = request.getParameter("time");//2020-07
               if (StringUtils.isNotBlank(time)){
                   //去查询是不是真的存在
                   List<EquipmentMaintenance> list = userService.getEquipmentMaintenance(users.get(0).getId(), time);
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

    /**
     * 跳转到msds页面
     * @param id
     * @return
     */
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

    /**
     * 删除msds
     * @param request
     * @return
     */
    @RequestMapping("deleteMSDS")
    @ResponseBody
    public ResultVO deleteMSDS(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("删除失败，请传id");
        User user = (User)request.getSession().getAttribute("user");
        String id = request.getParameter("id");
        System.out.println("======================================="+id);
        if (StringUtils.isNotBlank(id)){
            //传id删除-- state = 0
            List<Msds> msdsList = userService.getMsdsByCid(user.getId());
            for (int i = 0;i<msdsList.size();i++){
                if ((msdsList.get(i).getId()+"").equals(id)){
                    userService.deleteMsdsById(id,user.getId());
                    resultVO.setMsg("删除成功");
                    resultVO.setCode(1);
                    //记录日志
                    String content = "删除MSDS -> " + msdsList.get(i).getName();
                    commonService.addLog(new JTLog(user.getId(),content));
                    return resultVO;
                }
            }
                resultVO.setMsg("删除失败，不存在该id");
                resultVO.setCode(1);

        }

        return resultVO;
    }

    /**
     * 物料数据页面  回显
     * @param request
     * @return
     */
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
            System.out.println(list+"=================================");
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
        //查询物料使用的时间（2020-08），根据物料记忆表没有删除的物料（因为删除了的物料就没必要再统计了），分组，
        // 用于回显给用户看自己提交了那几个月的数据
        List<String> materialsUsedTime = userService.getMaterialsUsedTime(id);
        //System.out.println(materialsUsedTime+"==================");
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        Map map = new LinkedHashMap();//{"2020-01":"you","2020-02":"you"}
        for (int i = 1;i <= 12;i++){
            String msg = "未提交";
            for (int j = 0;j<materialsUsedTime.size();j++){
                if ((year + "-" + (i < 10 ? "0" + i : "" + i)).equals(materialsUsedTime.get(j))){
                     msg = "已提交";
                     break;
                }
            }
            map.put(year + "-" + (i < 10 ? "0" + i : "" + i),msg);
        }
        //System.out.println(map+"===================");
        mv.addObject("submitted",map);
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

    /**
     * 删除物料记忆
     * @param name
     * @param request
     * @return
     */
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
                //添加日志
                String content = "删除物料 -> " + name;
                commonService.addLog(new JTLog(user.getId(),content));
            }else {
                //查不到，即虽然传来的物料名正确，但是表中没有，没有的不会回显，但是却传来了，非法操作！
                vo.setMsg("删除失败！请勿进行非法操作！");
            }

        }else {
            vo.setMsg("删除失败！原因参数异常");
        }
        return vo;
    }

    /**
     * 保存物料使用情况
     * @param request
     * @return
     */
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
        User u = (User)request.getSession().getAttribute("user");
        List<User> users = userService.getUserById(u.getId());
        User user = users.get(0);
        Integer id = user.getId();

        String finalUsedTime = usedTime;//传来的时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last = now.minusMonths(1);
        String last_month = last.format(DateTimeFormatter.ofPattern("yyyy-MM"));// 2020-08
        int day = now.getDayOfMonth();//获取 日

        map.forEach((k, v)->{
//            System.out.println(k);
//            System.out.println(v);
            //先查该年月有没有对应的数据，没有就插入，有就更新

            List<MaterialsUsed> list = userService.getMaterialsUsedByCidAndUsedTimeAndName(id, finalUsedTime, k);
            if (list.size()> 0){
                //数据库中存在了该公司该物料的该年月的使用情况，更新
                // 要判断，20天前的可以更新，且有更新资格才行
                //String this_month = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));//2020-09
                if (day <= 20 && finalUsedTime.equals(last_month)){//传来的时间是上一个月的
                    //当月20日前，可以修改/提交上一个月的
                    userService.updateMaterialsUsed(v,id,k,finalUsedTime);
                    vo.setCode(1);
                    vo.setMsg(finalUsedTime + "数据" + "更新成功");
                    //添加日志
                    String content = "更新物料使用数据 -> " + finalUsedTime + " [" + k + "：" + list.get(0).getUsed() + "->" + v + "]";//更新物料使用数据 -> 2020-04 [干膜:20 -> ]
                    commonService.addLog(new JTLog(user.getId(),content));
                }else{
                    //当月20号后，不能修改
                    if (user.getModify() == 0) {
                        vo.setCode(0);
                        vo.setMsg(finalUsedTime + "数据" + "更新失败！原因：只能于本月21号前更新上一个月的数据【联系建泰办公室取得权限即可】");
                    }else {//有权限
                        userService.updateMaterialsUsed(v,id,k,finalUsedTime);
                        vo.setCode(1);
                        vo.setMsg(finalUsedTime + "数据" + "更新成功");
                        //添加日志
                        String content = "更新物料使用数据 -> " + finalUsedTime + " [" + k + "：" + list.get(0).getUsed() + "->" + v + "]";//更新物料使用数据 -> 2020-04 [干膜:20 -> ]
                        commonService.addLog(new JTLog(user.getId(),content));
                    }
                }

            }else {
                //查不到结果，直接插入数据
                if (day <= 20 && finalUsedTime.equals(last_month)){//传来的时间是上一个月的
                    //当月20日前，可以修改/提交上一个月的
                    userService.saveMaterialsUsed(id,k,v,finalUsedTime);
                    vo.setCode(1);
                    vo.setMsg(finalUsedTime + "数据" + "提交成功");
                    //添加日志
                    String content = "提交物料使用数据 -> " + finalUsedTime + " [" + k + "：" + v + "]";//更新物料使用数据 -> 2020-04 [干膜:20 -> ]
                    commonService.addLog(new JTLog(user.getId(),content));
                }else{
                    //当月20号后，不能提交
                    if (user.getModify() == 0) {
                        vo.setCode(0);
                        vo.setMsg(finalUsedTime + "数据" + "提交失败！原因：只能于本月21号前提交上一个月的数据【联系建泰办公室取得权限即可】");
                    }else {//有权限
                        userService.saveMaterialsUsed(id,k,v,finalUsedTime);
                        vo.setCode(1);
                        vo.setMsg(finalUsedTime + "数据" + "提交成功");
                        //添加日志
                        String content = "提交物料使用数据 -> " + finalUsedTime + " [" + k + "：" + v + "]";//更新物料使用数据 -> 2020-04 [干膜:20 -> ]
                        commonService.addLog(new JTLog(user.getId(),content));
                    }
                }

            }

        });
        if (map.size() == 0 ){
            vo.setMsg("请添加物料后再提交");
        }
        return vo;
    }

    /**
     * 产品数据页面 回显
     * @param request
     * @return
     */
    @RequestMapping("productData")
    public ModelAndView productData(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/product-data");
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        //查询生产 产品种类表
        List<Product> productsList = userService.getProductsList();
        productsList.forEach(l->{
            l.setName(l.getFirst() + " -> " + l.getSecond() + " -> " + l.getName());
        });
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

        //查询物料使用的时间（2020-08），根据物料记忆表没有删除的物料（因为删除了的物料就没必要再统计了），分组，
        // 用于回显给用户看自己提交了那几个月的数据
        List<String> productsOutputTime = userService.getProductsOutputByCid(id);
        //System.out.println(materialsUsedTime+"==================");
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        Map map = new LinkedHashMap();//{"2020-01":"you","2020-02":"you"}
        for (int i = 1;i <= 12;i++){
            String msg = "未提交";
            for (int j = 0;j<productsOutputTime.size();j++){
                if ((year + "-" + (i < 10 ? "0" + i : "" + i)).equals(productsOutputTime.get(j))){
                    msg = "已提交";
                    break;
                }
            }
            map.put(year + "-" + (i < 10 ? "0" + i : "" + i),msg);
        }
        System.out.println(map+"===================");
        mv.addObject("submitted",map);

        return mv;
    }

    /**
     * 保存产品产量
     * @param request
     * @return
     */
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
            String finalDateTime1 = dateTime;
            map.forEach((k, v)->{
                userService.saveProductsOutput(id,k,v,finalDateTime);
            });
            vo.setCode(1);
            vo.setMsg("保存成功");
            //添加日志
            String content = "提交产品产量 -> " + finalDateTime1;//提交产品产量 -> 2020-04 [干膜:20 -> ]
            commonService.addLog(new JTLog(user.getId(),content));
        }else {
            //该月有申报，更新
            String finalDateTime2 = dateTime;
            map.forEach((k, v)->{
                //查一下，没有该产品就插入，有就更新 -- 因为后面新增产品的话，新增的没法赋值
                List<Product> productsOutputByPid = userService.getProductsOutputByPid(id, finalDateTime, k);
                if (productsOutputByPid.size()>0){
                    userService.updateProductsOutput(v,finalDateTime,id,k);
                }else {
                    //插入
                    userService.saveProductsOutput(id,k,v,finalDateTime);
                }
            });
            vo.setCode(1);
            vo.setMsg("更新成功");
            //添加日志
            String content = "更新产品产量 -> " + finalDateTime2;//提交产品产量 -> 2020-04 [干膜:20 -> ]
            commonService.addLog(new JTLog(user.getId(),content));
        }
        return vo;
    }

    /**
     * 设备列表页面
     * @param request
     * @return
     */
    @RequestMapping("equipmentList")
    public ModelAndView equipmentList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/equipment-list");
        User user = (User) request.getSession().getAttribute("user");
        Integer id = user.getId();
        //模板赋值
        List<Equipment> equipmentList = userService.getEquipmentListByCid(id);
        ArrayList<Equipment> airList = new ArrayList<>();
        ArrayList<Equipment> windList = new ArrayList<>();
        ArrayList<Equipment> electricList = new ArrayList<>();
        equipmentList.forEach(l -> {
            if (l.getType() == 1){//类型(后期再加) 1=空压机，2=风机，3=电机
                airList.add(l);
            }
            if (l.getType() == 2){//类型(后期再加) 1=空压机，2=风机，3=电机
                windList.add(l);
            }
            if (l.getType() == 3){//类型(后期再加) 1=空压机，2=风机，3=电机
                electricList.add(l);
            }
        });
        mv.addObject("airList",airList);
        mv.addObject("windList",windList);
        mv.addObject("electricList",electricList);
        return mv;
    }

    /**
     * 跳转到具体设备种类的页面
     * @param request
     * @return
     */
    @RequestMapping("equipment")
    public ModelAndView equipment(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type)){
            switch (type){
                case "1":
                    //添加空压机
                    mv.setViewName("user/machine-air-add");
                    break;
                case "2"://跳转到风机添加页面
                    mv.setViewName("user/machine-wind-add");
                    break;
                case "3":
                    mv.setViewName("user/machine-electric-add");
                    break;
                default:
                    mv.setViewName("error");
                    break;
            }
        }
        return mv;
    }

    /**
     * 添加生产设备
     * @param request
     * @param equipment
     * @return
     */
    @RequestMapping("addEquipment")
    @ResponseBody
    public ResultVO addEquipment(HttpServletRequest request,Equipment equipment){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);//0=失败

        User user = (User)request.getSession().getAttribute("user");
        Integer cid = user.getId();

        switch (request.getParameter("type")){
            case "1":
                equipment.setcId(cid);
                equipment.setType(1);
                //查询是否已经添加此型号，如果有，则不作处理
                List<Equipment> equipmentList1 = userService.getEquipment(equipment);
                if (equipmentList1.size() > 0){
                    //已经存在，返回提示
                    resultVO.setCode(0);
                    resultVO.setMsg("该型号空压机已存在！请勿重复操作！");
                }else {
                    userService.addEquipmentAir(equipment);
                    resultVO.setCode(1);
                    resultVO.setMsg("空压机添加成功！");
                    //添加日志
                    String content1 = "添加生产设备 -> 空压机" ;//提交产品产量 -> 2020-04 [干膜:20 -> ]
                    commonService.addLog(new JTLog(user.getId(),content1));
                }

                break;
            case "2":
                equipment.setcId(cid);
                equipment.setType(2);
                //查询是否已经添加此型号，如果有，则不作处理
                List<Equipment> equipmentList2 = userService.getEquipment(equipment);
                if (equipmentList2.size() > 0){
                    //已经存在，返回提示
                    resultVO.setCode(0);
                    resultVO.setMsg("该型号风机已存在！请勿重复操作！");
                }else {
                    userService.addEquipmentWind(equipment);
                    resultVO.setCode(1);
                    resultVO.setMsg("风机添加成功！");
                    //添加日志
                    String content2 = "添加生产设备 -> 风机" ;//提交产品产量 -> 2020-04 [干膜:20 -> ]
                    commonService.addLog(new JTLog(user.getId(),content2));
                }

                break;
            case "3":
                equipment.setcId(cid);
                equipment.setType(3);
                //查询是否已经添加此型号，如果有，则不作处理
                List<Equipment> equipmentList3 = userService.getEquipment(equipment);
                if (equipmentList3.size() > 0){
                    //已经存在，返回提示
                    resultVO.setCode(0);
                    resultVO.setMsg("该型号电机已存在！请勿重复操作！");
                }else {
                    userService.addEquipmentElectric(equipment);
                    resultVO.setCode(1);
                    resultVO.setMsg("电机添加成功！");
                    //添加日志
                    String content3 = "添加生产设备 -> 电机" ;//提交产品产量 -> 2020-04 [干膜:20 -> ]
                    commonService.addLog(new JTLog(user.getId(),content3));
                }

                break;
            default:
                resultVO.setMsg("新增错误！暂不支持该设备类型添加");
                break;
        }

        return resultVO;
    }

    /**
     * 删除生产设备
     * @param id
     * @return
     */
    @RequestMapping("deleteEquipment")
    @ResponseBody
    public ResultVO deleteEquipment(Integer id,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        ResultVO resultVO = new ResultVO();
        List<Equipment> equipments = userService.getEquipmentById(id);
        if (equipments.size() > 0){
            userService.deleteEquipmentById(id);
            resultVO.setCode(1);
            resultVO.setMsg("删除成功");
            //添加日志

            switch (equipments.get(0).getType()){
                case 1:
                    String content1 = "删除生产设备 -> 空压机" ;
                    commonService.addLog(new JTLog(user.getId(),content1));
                    break;
                case 2:
                    String content2 = "删除生产设备 -> 风机" ;
                    commonService.addLog(new JTLog(user.getId(),content2));
                    break;
                case 3:
                    String content3 = "删除生产设备 -> 电机" ;
                    commonService.addLog(new JTLog(user.getId(),content3));
                    break;
            }

        }else {
            resultVO.setCode(0);
            resultVO.setMsg("删除失败");
        }
        return resultVO;
    }

    /**
     * 跳转到对应的设备编辑页面
     * @param request
     * @return
     */
    @RequestMapping("toEquipmentEdit")
    public ModelAndView toEquipmentEdit(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("eid");
        if (StringUtils.isNotBlank(id)){//带着id来
            //查询该id的数据然后回显，查不到就报错

            List<Equipment> equipment = userService.getEquipmentById(Integer.parseInt(id));
            if (equipment.size() > 0){
                Equipment eq = equipment.get(0);

                mv.addObject("equipment",eq);
                if (eq.getType() == 1){//空压机
                    mv.setViewName("user/machine-air-edit");
                }
                if (eq.getType() == 2){//风机
                    mv.setViewName("user/machine-wind-edit");
                }
                if (eq.getType() == 3){//电机
                    mv.setViewName("user/machine-electric-edit");
                }

            }else {
                mv.setViewName("error");
            }
        }else {
            mv.setViewName("error");
        }
        return mv;
    }

    /**
     * 修改生产设备参数
     * @param e
     * @return
     */
    @RequestMapping("equipmentEdit")
    @ResponseBody
    public ResultVO equipmentEdit(Equipment e,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        List<Equipment> equipmentList = userService.getEquipmentById(e.getId());

        if (equipmentList.size() > 0){
            //说明id正确,更新之
            userService.updateEquipment(e);
            resultVO.setMsg("更新成功");
            resultVO.setCode(1);
            Integer type = equipmentList.get(0).getType();
            if (type == 1){
                String content = "更新生产设备 -> 空压机" ;
                commonService.addLog(new JTLog(user.getId(),content));
            }
            if (type == 2){
                String content = "更新生产设备 -> 风机" ;
                commonService.addLog(new JTLog(user.getId(),content));
            }
            if (type == 3){
                String content = "更新生产设备 -> 电机" ;
                commonService.addLog(new JTLog(user.getId(),content));
            }

        }else {
            //id不存在，返回异常
            //mv.setViewName("error");
            resultVO.setMsg("更新失败，数据不存在");
        }

        return resultVO;
    }

    /**
     * 跳转到设备保养及运行记录页面
     * @param request
     * @return
     */
    @RequestMapping("equipmentMaintenance")
    public ModelAndView equipmentMaintenance(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/equipment-maintenance");
        User user = (User)request.getSession().getAttribute("user");
        Integer id = user.getId();
        String dateTime = request.getParameter("dateTime");


        // 用于回显给用户看自己提交了那几个月的数据
        List<String> productsOutputTime = userService.getMaintainTimeByCid(id);
        //System.out.println(materialsUsedTime+"==================");
        LocalDate now = LocalDate.now();
        int year = 0;
        if (StringUtils.isBlank(dateTime)){
            year = now.getYear();
        }else {
            if (dateTime.length()==4){
                year = Integer.parseInt(dateTime);
            }else {
                mv.setViewName("error");
            }


        }
        Map map = new LinkedHashMap();//{"2020-01":"you","2020-02":"you"}
        for (int i = 1;i <= 12;i++){
            String msg = "未提交";
            for (int j = 0;j<productsOutputTime.size();j++){
                if ((year + "-" + (i < 10 ? "0" + i : "" + i)).equals(productsOutputTime.get(j))){
                    msg = "已提交";
                    break;
                }
            }
            map.put(year + "-" + (i < 10 ? "0" + i : "" + i),msg);
        }
        mv.addObject("maintain_time",map);
        mv.addObject("year",year);
        return mv;
    }

    /**
     * 删除设备保养记录
     * @param request
     * @return
     */
    @RequestMapping("deleteEquipmentMaintenance")
    @ResponseBody
    public ResultVO deleteEquipmentMaintenance(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        User user = (User)request.getSession().getAttribute("user");

        String dateTime = request.getParameter("dateTime");
        if (StringUtils.isNotBlank(dateTime)){
            if (MyUtils.isValidDate(dateTime)) {
                //去查，如果有就设置state = 0，没有就报错
                List<EquipmentMaintenance> list = userService.getEquipmentMaintenance(user.getId(), dateTime);
                if (list.size() > 0){
                    userService.deleteEquipmentMaintenanceById(list.get(0).getId());
                    resultVO.setMsg("删除成功");
                    String content = "删除设备保养记录 -> " + dateTime;
                    commonService.addLog(new JTLog(user.getId(),content));
                }else {
                    resultVO.setMsg("删除失败,不存在");
                }
            }else {
                resultVO.setMsg("参数时间格式不对");
            }
        }else {
            resultVO.setMsg("删除失败");
        }


        return resultVO;
    }
}
