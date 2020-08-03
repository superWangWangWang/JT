package com.jiantai.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiantai.entity.CompanyInfo;
import com.jiantai.entity.JTDeclareRecord;
import com.jiantai.entity.JTMsdsUpload;
import com.jiantai.entity.JTProductRecord;
import com.jiantai.entity.JtMaterialEvidence;
import com.jiantai.entity.Material;
import com.jiantai.vo.VO;
import com.jiantai.service.JianTaiDataService;
import com.jiantai.utils.MyDateUtil;

@Controller
@RequestMapping("/data")
public class JianTaiDataController {

    /**
     * 由于我是一个人开发前后端,不是很专业,所以很多地方的注释都是后来加上去的,如果有什么看不明白,请谅解
     * 如果有什么看不懂的请加微信询问:13267690357
     */



    @Autowired
    private JianTaiDataService jianTaiDataService;

    //record目录下index.html对应的方法
    //查找物料列表
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/findDataToJTMaterialList")
    @ResponseBody
    public Map findDataToJTMaterialList(HttpServletRequest request) {
        //创建map
        Map<String, Object> result = new HashMap<>();
        //ModelAndView mv= new ModelAndView();
        //mv.setViewName("record/index2");
        try {
            List<Material> material = jianTaiDataService.findDataToJTMaterialList();

            //返回集合到html
            result.put("result", material);

            //mv.addObject("name", "aaaaaaa");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
            //return JSON.toJSONString(result);
        }
    }


    //record目录下index.html对应的方法
    //修改/设置企业信息
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/setCompanyInfoById")
    @ResponseBody
    public String setCompanyInfoById(HttpServletRequest request) {
        //创建map
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //html传回来的数组字符串
            String arrStr = request.getParameter("arr");
            //公司对应的id
            String id = request.getParameter("id");

            //调用Gson(解析数组字符串)
            Gson gson = new Gson();
            //将数组字符串转化成map
            Map<String, String> map = gson.fromJson(arrStr, Map.class);
			/*//从session中获取登录信息
			CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
			 */
            //传入id和map集合
            jianTaiDataService.setCompanyInfoById(id, map);

            // result.put("result", material);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JSON.toJSONString(result);
        }
    }


    //record目录下index.html对应的方法
    //这个方法因为业务需求的改变已经被放弃
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/addDeclareInfoToJTDeclareInfo")
    @ResponseBody
    public String addDeclareInfoToJTDeclareInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String add_arr = request.getParameter("add_arr");
            String update_arr = request.getParameter("update_arr");
            String date = request.getParameter("date");

            Gson gson = new Gson();
            List<Map<String, Object>> add_list =

                    gson.fromJson(add_arr, new TypeToken<List<Map<String, Object>>>() {
                    }.getType());

            List<Map<String, Object>> update_list =

                    gson.fromJson(update_arr, new TypeToken<List<Map<String, Object>>>() {
                    }.getType());

            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

            for (Map<String, Object> map : add_list) {
                if (map != null) {
                    map.put("date", date);
                    map.put("now", new Date());
                    map.put("userName", companyInfo.get企业简称());
                    jianTaiDataService.addDeclareInfoToJTDeclareInfo(map);
                    // System.out.println(map);
                }
            }

            for (Map<String, Object> map : update_list) {
                if (map != null) {
                    jianTaiDataService.updateDeclareInfoById(map);
                    // System.out.println(map);
                }
            }
            // result.put("result", material);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JSON.toJSONString(result);
        }
    }

    //record目录下index.html对应的方法
    //这个方法因为业务需求的改变已经被放弃
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/findRecordByUsername")
    @ResponseBody
    public Map findRecordById(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String username = request.getParameter("username");
            String date = request.getParameter("date");
            String id = request.getParameter("id");
            String date_flag = date + "-15";

            String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            // 获取日期
            Date date1 = MyDateUtil.parseDate(date_flag, "yyyy-MM-dd");
            Date date2 = MyDateUtil.parseDate(now, "yyyy-MM-dd");
            long betweenDays = (date2.getTime() - date1.getTime()) / (1000L * 3600L * 24L);
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            System.out.println("==========="+companyInfo);

            //CompanyInfo companyInfo = jianTaiDataService.findCompanyInfoById(Integer.parseInt(id));


            JtMaterialEvidence jtMaterialEvidence = jianTaiDataService.findEvidenceByCompanyName(companyInfo.get企业简称(),date);

            System.out.println(jtMaterialEvidence);
            if(jtMaterialEvidence != null && StringUtils.isNotBlank(jtMaterialEvidence.getFilename())){
                result.put("jtMaterialEvidence", jtMaterialEvidence);
            } else {
                result.put("jtMaterialEvidence", null);
            }

            if (betweenDays > 31) {
                //判断right字段是否等于0 , 等于0没权限,1为有权限
                if (companyInfo.getRight() != null && companyInfo.getRight() > 0) {
                    result.put("change", true);
                    System.out.println("超过31天但是有权限,能改");
                } else {
                    result.put("change", false);
                    System.out.println("不能改，日期超过31天..");
                }

            } else {
                result.put("change", true);
                System.out.println("能改");

            }

            System.out.println(username + "." + date);
            //分页
            //PageHelper.startPage(1, 1);
            List<JTDeclareRecord> list = jianTaiDataService.findRecordById(username, date);
            //PageInfo pageInfo = new PageInfo<>(list);
            result.put("result", list);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
            //return JSON.toJSONString(result);
        }
    }

    // record目录下index.html对应的方法
    // 通过日期查找产品
    @SuppressWarnings("finally")
    @RequestMapping(value = "/record/findProductRecordByDate")
    @ResponseBody
    public VO findProductRecordByDate(HttpServletRequest request) {
        VO ve = new VO();
        try {
            String date = request.getParameter("date");
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            List<JTProductRecord> products = jianTaiDataService.findProducts();
            List<JTProductRecord> jtProductRecord = jianTaiDataService.findProductRecordByDate(companyInfo.get企业简称(),date);

            for (JTProductRecord record : jtProductRecord) {

                for (JTProductRecord product : products) {
                    if(product.getProduct().equals(record.getProduct())){
                        product.setYield(record.getYield());
                    }
                }

            }

            for (JTProductRecord p : products) {
                System.out.println(p);
            }


            ve.setData(products);
            ve.setCode(0);
            ve.setCount(products.size());
            ve.setMsg("");

            // 返回集合到html
            //result.put("result", material);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ve;
        }
    }
    //根据id删除插入记录
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/delRecordById")
    @ResponseBody
    public String delRecordById(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);

            jianTaiDataService.delRecordById(id);
        } catch (Exception e) {
            result.put("result", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            return JSON.toJSONString(result);
        }
    }

    /**
     * page/views/record目录下的index.html页面的方法
     */
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/updateUnitByProduct")
    @ResponseBody
    public String updateUnitByProduct(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String date = request.getParameter("date");
            String yield = request.getParameter("yield");
            String product = request.getParameter("product");
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

            JTProductRecord jtProductRecord = jianTaiDataService.findUnitByCompanyAndDate(date, product, companyInfo.get企业简称());

            if(jtProductRecord != null){
                jianTaiDataService.setYieldById(jtProductRecord.getId(), yield);
            } else {
                jianTaiDataService.addProductRecord(product, new Date(), date, yield, companyInfo.get企业简称());
            }



        } catch (Exception e) {
            result.put("err", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            return JSON.toJSONString(result);
        }
    }



    // page/views/record目录下的index.html页面的方法
    // 上传文件(压缩包格式)
    @RequestMapping(value = "/file/uploadEvidence", method = RequestMethod.POST)
    @ResponseBody
    public String uploadEvidence(@RequestParam(value = "file") MultipartFile file,
                                              HttpServletRequest request, HttpServletResponse response) {
        // 创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        // 创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        // 文件名
        String filename = "";
        // 从session提出登录信息
        String date = request.getParameter("date");
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
        // 文件上传目录
        String localPath = "D:\\upload\\evidence\\";
        try {
            JtMaterialEvidence jtMaterialEvidence = jianTaiDataService.findEvidenceByCompanyName(companyInfo.get企业简称(), date);
            if (file != null) {
                // 生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");

                // 得到 文件名(随机数+uuid+后缀)
                if (jtMaterialEvidence != null) {// 替换
                    // 当用户不是第一次上传,走这里替换上传文件名
                    // 文件名从登录信息里提出,意思是若不是第一次上传,继续用回之前新建的uuid作为文件名(文件名上传至数据库对应于每个登录用户)
                    filename = jtMaterialEvidence.getFilename();
                    jianTaiDataService.updateMaterialEvidenceById(jtMaterialEvidence.getId(), filename);
                } else {// 新建
                    // 当用户第一次上传,走这里新建上传文件名
                    filename = (int) ((Math.random()) * 100000000) + uuid + ".rar";
                    jianTaiDataService.addMaterialEvidenceById(filename, companyInfo.get企业简称(), date);
                }

                // 修改用户对应的文件名

                // 判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {

                    new File(localPath).mkdirs();
                }
                // 复制到当前文件夹
                file.transferTo(new File(localPath + filename));

            }
        } catch (Exception e) {
            System.err.println(e);
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", filename);
            System.out.println("上传失败");
            return JSON.toJSONString(map);

        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", filename);
        System.out.println("上传成功");
        //return map;
        return JSON.toJSONString(map);
    }

    //page/views/upload目录下的upload.html页面的方法
    //上传文件(压缩包格式)

    @RequestMapping(value = "/file/uploadPlan", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPlan(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
                                          HttpServletResponse response) {
        //创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        //创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        //文件名
        String filename = "";
        //从session提出登录信息
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

        //项目目录String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";

        //文件上传目录
        String localPath = "D:\\upload\\imgs\\";
        try {
            if (file != null) {
                // 生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                // 获得文件类型（可以判断如果不是图片，禁止上传）
                //String contentType = file.getContentType();
                // 获得文件后缀名
                //String suffixName = contentType.substring(contentType.indexOf("/") + 1);


                // 得到 文件名(随机数+uuid+后缀)
                if(companyInfo.getPlanName() != null && StringUtils.isNotBlank(companyInfo.getPlanName())){//替换
                    //当用户不是第一次上传,走这里替换上传文件名
                    //文件名从登录信息里提出,意思是若不是第一次上传,继续用回之前新建的uuid作为文件名(文件名上传至数据库对应于每个登录用户)
                    filename = companyInfo.getPlanName();
                } else {//新建
                    //当用户第一次上传,走这里新建上传文件名
                    filename = (int) ((Math.random()) * 100000000) + uuid + ".rar"; //+ suffixName;
                    //修改用户对应的文件名
                    jianTaiDataService.setCompanyInfoPlanNameById(companyInfo.getId(), filename);

                    CompanyInfo companyInfo2 = jianTaiDataService.findCompanyInfoById(companyInfo.getId());
                    //修改文件名后把session中的用户更新一下
                    request.getSession().setAttribute("LOGIN_USER", companyInfo2);
                }

                // 判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {

                    new File(localPath).mkdirs();
                }
                // 复制到当前文件夹
                file.transferTo(new File(localPath + filename));

            }
        } catch (Exception e) {
            System.out.println(e);
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", filename);
            System.out.println("上传失败");
            return JSON.toJSONString(map);
            //return map;

        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", filename);
        System.out.println("上传成功");
        return JSON.toJSONString(map);
        //return map;
    }

    //page/views/upload目录下的msds.html页面的方法
    //上传文件(pdf格式)
    @RequestMapping(value = "/file/uploadMsds", method = RequestMethod.POST)
    @ResponseBody
    public String uploadMSDS(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
                                          HttpServletResponse response) {
        //创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        //创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        //html(layui框架文件上传功能自动传入的)传入的reuslt字符串
        String result = request.getParameter("result");

        Gson gson = new Gson();
        //将result字符串转化为List<Map<String, Object>>
        List<Map<String, Object>> update_list =

                gson.fromJson(result, new TypeToken<List<Map<String, Object>>>() {
                }.getType());
        String materialName = "";
        for (Map<String, Object> m : update_list) {
            if(m != null){
                if(file.getOriginalFilename().equals(m.get("filename"))){
                    materialName = (String) m.get("material");
                }
            }

        }


        //从session提出登录信息
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

        //项目目录String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";
        String originalFilename = file.getOriginalFilename();
        String localPath = "D:\\upload\\msds\\";
        //判断是否存在这个文件夹，没有则创建
        if(!new File(localPath).exists()){
            new File(localPath).mkdirs();
        }

        String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));//获取文件名
        filename += "_" + companyInfo.getUser();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀名
        originalFilename = filename + suffixName;
        //System.out.println("路径:" + localPath);
        try {

            if (file != null) {

                // 复制到当前文件夹
                file.transferTo(new File(localPath + originalFilename));
                JTMsdsUpload jtMsdsUpload = jianTaiDataService.findMsdsFilenameByCompanyAndMateriel(companyInfo.getUser(), materialName);

                if(jtMsdsUpload != null){
                    jianTaiDataService.setMsdsFilename(originalFilename, jtMsdsUpload.getId(), new Date());
                } else {
                    jianTaiDataService.addMsdsFileInfo(new Date(), materialName, companyInfo.getUser(), originalFilename);

                }
            }
        } catch (Exception e) {

            System.out.println(e);
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", filename);
            System.out.println("上传失败");
            //return map;
            return JSON.toJSONString(map);
        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", filename);
        System.out.println("上传成功");
        //return map;
        return JSON.toJSONString(map);
    }


    //page/views/upload目录下的upload.html页面的方法
    //上传文件(压缩包格式)

    @RequestMapping(value = "/file/uploadProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadProduct(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
                                             HttpServletResponse response) {
        //创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        //创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        //文件名
        String originalFilename = file.getOriginalFilename();
        //从session提出登录信息
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

        String localPath = "D:\\upload\\product\\";// 文件上传目录
        String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));//获取文件名
        filename += "_" + companyInfo.getUser();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀名
        originalFilename = filename + suffixName;

        try {
            if (file != null) {
                // 修改用户对应的文件名
                jianTaiDataService.setCompanyInfoProductNameById(companyInfo.getId(), originalFilename);
                CompanyInfo companyInfo2 = jianTaiDataService.findCompanyInfoById(companyInfo.getId());
                // 修改文件名后把session中的用户更新一下
                request.getSession().setAttribute("LOGIN_USER", companyInfo2);


                // 判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {

                    new File(localPath).mkdirs();
                }
                // 复制到当前文件夹
                file.transferTo(new File(localPath + originalFilename));

            }
        } catch (Exception e) {
            System.err.println(e);
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", filename);
            System.out.println("上传失败");
            //return JSON.toJSONString(map);
            return map;

        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", filename);
        System.out.println("上传成功");
        return map;
        //return JSON.toJSONString(map);
    }

    //
    @SuppressWarnings("finally")
    @PostMapping(value = "/downloadFile/findMaterialByMaterialName")
    @ResponseBody
    public Map findMaterialByMaterialName(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String materialName = request.getParameter("materialName");
            //从session提出登录信息
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            JTMsdsUpload jtMsdsUpload = jianTaiDataService.findMsdsFilenameByCompanyAndMateriel(companyInfo.getUser(), materialName);
            System.out.println(jtMsdsUpload);
            if(jtMsdsUpload != null && StringUtils.isNotBlank(jtMsdsUpload.getMsdsFilename())){
                System.out.println("文件存在" + jtMsdsUpload);
                result.put("filename", jtMsdsUpload.getMsdsFilename());
            }

        } catch (Exception e) {
            result.put("result", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            //return JSON.toJSONString(result);
            return result;
        }
    }

    //初始化 plan.html
    @SuppressWarnings("finally")
    @PostMapping(value = "/downloadFile/initToPlan")
    @ResponseBody
    public Map initToPlan(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            System.out.println(companyInfo);
            System.out.println("初始化文件名:"+ companyInfo.getPlanName());
            result.put("filename", companyInfo.getPlanName());

        } catch (Exception e) {
            result.put("result", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            return result;
            // return JSON.toJSONString(result);
        }
    }

    //初始化 product.html
    @SuppressWarnings("finally")
    @PostMapping(value = "/downloadFile/initToProduct")
    @ResponseBody
    public Map initToProduct(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            System.out.println(companyInfo);
            result.put("filename", companyInfo.getProductName());

        } catch (Exception e) {
            result.put("result", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            return result;
            //return JSON.toJSONString(result);
        }
    }


    //下载文件接口
    @RequestMapping(value = "/file/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {



        //获取路径和文件名
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
        CompanyInfo companyInfo2 = jianTaiDataService.findCompanyInfoById(companyInfo.getId());
        //String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";
        String folder = request.getParameter("folder");//文件夹名称
        String filename = request.getParameter("filename");//物料名
        String localPath = "D:\\upload\\" + folder + "\\";
        String filename_str = "";
        if("msds".equals(folder)){//MSDS目录
            filename_str = filename;
            localPath += filename_str;
        } else if ("imgs".equals(folder)){//平面图目录
            filename_str = companyInfo2.getPlanName();
            localPath += filename_str;
        } else if ("evidence".equals(folder)){//佐证文件目录
            String date = request.getParameter("date");
            JtMaterialEvidence jtMaterialEvidence = jianTaiDataService.findEvidenceByCompanyName(companyInfo.get企业简称(), date);
            filename_str = jtMaterialEvidence.getFilename();
            localPath += filename_str;
        } else if ("product".equals(folder)){//产品设备文件目录
            filename_str = companyInfo2.getProductName();
            localPath += filename_str;
        }



        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ filename_str);
        File file = new File(localPath);
        downloadFile(file, response);
        System.out.println("下载目录文件为:" + localPath);
        return null;
    }

    //下载文件功能
    public static void downloadFile(File file, HttpServletResponse response){
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //查找文件
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/findImgsByLocal")
    @ResponseBody
    public String findImgsByLocal(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            //String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";
            String localPath = "D:\\upload\\imgs\\";
            ArrayList<String> filelist = new ArrayList<>();


            result.put("filelist", filelist);
            result.put("username", companyInfo.getUser());

        } catch (Exception e) {
            result.put("result", "删除失败, 请重新操作");
            e.printStackTrace();
        } finally {
            return JSON.toJSONString(result);
        }
    }


}
