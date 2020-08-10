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
import com.jiantai.entity.*;
import com.jiantai.service.impl.JianTaiDataServiceImpl;
import com.jiantai.utils.DownloadUtil;
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
    private JianTaiDataServiceImpl jianTaiDataServiceImpl;

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
            List<Material> material = jianTaiDataServiceImpl.findDataToJTMaterialList();

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

            jianTaiDataServiceImpl.setCompanyInfoById(id, map);
            //添加操作日志记录
            jianTaiDataServiceImpl.addLog(new JTLog(Integer.parseInt(id), "修改企业信息</br></br>" + map.toString()));
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
            System.out.println("------------------" + add_list);
            for (Map<String, Object> map : add_list) {
                if (map != null) {
                    //添加日志
                    String material = (String) map.get("material");//物料名
                    String dosage = (String) map.get("dosage");//当月用量
                    String unit = (String) map.get("unit");
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "添加物料申报信息</br></br>" + material + " " + date + " 用量 "
                            + dosage + " " + unit
                    ));
                    map.put("date", date);
                    map.put("now", new Date());
                    map.put("userName", companyInfo.get企业简称());
                    jianTaiDataServiceImpl.addDeclareInfoToJTDeclareInfo(map);

                    // System.out.println(map);
                }
            }

            for (Map<String, Object> map : update_list) {
                if (map != null) {
                    //添加日志
                    String material = (String) map.get("material");//物料名
                    String dosage = (String) map.get("dosage");//当月用量
                    String unit = (String) map.get("unit");
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "更新物料申报信息</br></br>" + material + " " + date + " 用量 "
                            + dosage + " " + unit
                    ));
                    jianTaiDataServiceImpl.updateDeclareInfoById(map);

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

            //CompanyInfo companyInfo = jianTaiDataService.findCompanyInfoById(Integer.parseInt(id));


            JtMaterialEvidence jtMaterialEvidence = jianTaiDataServiceImpl.findEvidenceByCompanyName(companyInfo.get企业简称(), date);

            System.out.println(jtMaterialEvidence);
            if (jtMaterialEvidence != null && StringUtils.isNotBlank(jtMaterialEvidence.getFilename())) {
                result.put("jtMaterialEvidence", jtMaterialEvidence);
            } else {
                result.put("jtMaterialEvidence", null);
            }

            if (betweenDays > 31) {
                //判断right字段是否等于0 , 等于0没权限,1为有权限
                if (companyInfo.getRight() == 1) {
                    // if (companyInfo.getRight() != null && companyInfo.getRight() > 0) {
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
            List<JTDeclareRecord> list = jianTaiDataServiceImpl.findRecordById(username, date);
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
            List<JTProductRecord> products = jianTaiDataServiceImpl.findProducts();
            List<JTProductRecord> jtProductRecord = jianTaiDataServiceImpl.findProductRecordByDate(companyInfo.get企业简称(), date);

            for (JTProductRecord record : jtProductRecord) {

                for (JTProductRecord product : products) {
                    if (product.getProduct().equals(record.getProduct())) {
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
            //根据id查询物料详情
            List<JTDeclareRecord> declares = jianTaiDataServiceImpl.getJtDeclareRecordById(id);
            String material = declares.get(0).getMaterial();//物料名称
            String unit = declares.get(0).getUnit();//物料成分
            String datetime = declares.get(0).getDatetime();//成分含量
            String dosage = declares.get(0).getDosage();//当月用量
            jianTaiDataServiceImpl.delRecordById(id);
            //添加日志
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "删除物料申报信息</br></br>" + material + " " + datetime + " 用量 " + dosage + " " + unit));
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

            JTProductRecord jtProductRecord = jianTaiDataServiceImpl.findUnitByCompanyAndDate(date, product, companyInfo.get企业简称());

            if (jtProductRecord != null) {
                //List<JTProductRecord> products = jianTaiDataServiceImpl.getJtProductRecordById(jtProductRecord.getId());

                jianTaiDataServiceImpl.setYieldById(jtProductRecord.getId(), yield);
                //添加日志 更新企业产品生产数据
                Double y = jtProductRecord.getYield();//原产量
                jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "更新企业产品生产数据</br></br>" + product + " 原产量 " + y + " 修改为 " + yield));
            } else {
                jianTaiDataServiceImpl.addProductRecord(product, new Date(), date, yield, companyInfo.get企业简称());
                //添加日志 更新企业产品生产数据
                jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "添加企业产品生产数据</br></br>" + product + " 产量 " + yield));

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

    /**
     * 企业物料使用数据上传凭证
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/file/uploadEvidence", method = RequestMethod.POST)
    @ResponseBody
    public String uploadEvidence(@RequestParam(value = "file") MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        // 创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        // 创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        // 文件名
        // 从session提出登录信息
        String date = request.getParameter("date");
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
        // 文件上传目录
        String originalFilename = file.getOriginalFilename();
        String localPath = "D:\\upload\\evidence\\";
        //判断是否存在这个文件夹，没有则创建
        if (!new File(localPath).exists()) {
            new File(localPath).mkdirs();
        }
        String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));//获取文件名
        filename += "_" + companyInfo.getUser();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀名
        originalFilename = filename + suffixName;


        //System.out.println("路径:" + localPath);
        try {
            if (file != null) {
                JtMaterialEvidence jtMaterialEvidence = jianTaiDataServiceImpl.findEvidenceByCompanyName(companyInfo.get企业简称(), date);
                System.out.println(jtMaterialEvidence);
                if (jtMaterialEvidence != null && StringUtils.isNotBlank(jtMaterialEvidence.getFilename())) {
                    File delFile = new File(localPath + jtMaterialEvidence.getFilename());
                    delFile.delete();
                    jianTaiDataServiceImpl.updateMaterialEvidenceById(jtMaterialEvidence.getId(), originalFilename);
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "更新 " + date + " 企业物料使用数据凭证"));
                } else {
                    jianTaiDataServiceImpl.addMaterialEvidenceById(originalFilename, companyInfo.get企业简称(), date);
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "上传 " + date + " 企业物料使用数据凭证"));
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

    /**
     * 上传平面图压缩包  一个公司只有一个压缩包
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/file/uploadPlan", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPlan(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
                             HttpServletResponse response) {
        //创建map2用于放下文件名
        Map<String, Object> map2 = new HashMap<String, Object>();
        //创建map用于放下上传成功或上传失败的对应数据
        Map<String, Object> map = new HashMap<String, Object>();
        //文件名
        //从session提出登录信息
        CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");

        //项目目录String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";

        //文件上传目录
        String localPath = "D:\\upload\\plan\\";
        String originalFilename = file.getOriginalFilename();
        //判断是否存在这个文件夹，没有则创建
        if (!new File(localPath).exists()) {
            new File(localPath).mkdirs();
        }
        String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));//获取文件名
        filename += "_" + companyInfo.getUser();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀名
        originalFilename = filename + suffixName;


        //System.out.println("路径:" + localPath);
        try {
            if (file != null) {
                if (StringUtils.isNotBlank(companyInfo.getPlanName())) {
                    File delFile = new File(localPath + companyInfo.getPlanName());
                    delFile.delete();
                    jianTaiDataServiceImpl.setCompanyInfoPlanNameById(companyInfo.getId(), originalFilename);
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "更新平面图"));
                } else {
                    jianTaiDataServiceImpl.setCompanyInfoPlanNameById(companyInfo.getId(), originalFilename);
                    //添加日志

                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "上传平面图"));
                }
                CompanyInfo companyInfo2 = jianTaiDataServiceImpl.findCompanyInfoById(companyInfo.getId());
                //修改文件名后把session中的用户更新一下
                request.getSession().setAttribute("LOGIN_USER", companyInfo2);
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
            return JSON.toJSONString(map);
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

    /**
     * 上传msds压缩文件
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
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
            if (m != null) {
                if (file.getOriginalFilename().equals(m.get("filename"))) {
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
        if (!new File(localPath).exists()) {
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
                JTMsdsUpload jtMsdsUpload = jianTaiDataServiceImpl.findMsdsFilenameByCompanyAndMateriel(companyInfo.get企业简称(), materialName);
                if (jtMsdsUpload != null) {
                    new File(localPath + jtMsdsUpload.getMsdsFilename()).delete();
                    jianTaiDataServiceImpl.setMsdsFilename(originalFilename, jtMsdsUpload.getId(), new Date());
                    //添加日志
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "更新MSDS</br></br>" + materialName + " 文件名：" + originalFilename));
                } else {
                    jianTaiDataServiceImpl.addMsdsFileInfo(new Date(), materialName, companyInfo.get企业简称(), originalFilename);
                    //添加日志
                    jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "上传MSDS</br></br>" + materialName + " 文件名：" + originalFilename));
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

    /**
     * 生产设备明录上传
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
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
                String productName = companyInfo.getProductName();
                if (StringUtils.isNotBlank(productName)) {
                    File delFile = new File(localPath + productName);
                    delFile.delete();

                }
                // 修改用户对应的文件名
                jianTaiDataServiceImpl.setCompanyInfoProductNameById(companyInfo.getId(), originalFilename);
                CompanyInfo companyInfo2 = jianTaiDataServiceImpl.findCompanyInfoById(companyInfo.getId());
                // 修改文件名后把session中的用户更新一下
                request.getSession().setAttribute("LOGIN_USER", companyInfo2);


                // 判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {

                    new File(localPath).mkdirs();
                }
                // 复制到当前文件夹
                file.transferTo(new File(localPath + originalFilename));
                //添加日志
                jianTaiDataServiceImpl.addLog(new JTLog(companyInfo.getId(), "上传生产设备明录 " + originalFilename));
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
            JTMsdsUpload jtMsdsUpload = jianTaiDataServiceImpl.findMsdsFilenameByCompanyAndMateriel(companyInfo.getUser(), materialName);
            System.out.println(jtMsdsUpload);
            if (jtMsdsUpload != null && StringUtils.isNotBlank(jtMsdsUpload.getMsdsFilename())) {
                System.out.println("文件存在" + jtMsdsUpload);
                result.put("filename", jtMsdsUpload.getMsdsFilename());
            }

        } catch (Exception e) {

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
            System.out.println("初始化文件名:" + companyInfo.getPlanName());
            result.put("filename", companyInfo.getPlanName());

        } catch (Exception e) {
            //result.put("result", "删除失败, 请重新操作");
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
            //result.put("result", "删除失败, 请重新操作");
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
        CompanyInfo companyInfo2 = jianTaiDataServiceImpl.findCompanyInfoById(companyInfo.getId());
        //String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";
        String folder = request.getParameter("folder");//文件夹名称
        String filename = request.getParameter("filename");//物料名
        String localPath = "D:\\upload\\" + folder + "\\";
        String filename_str = "";
        if ("msds".equals(folder)) {//MSDS目录
            filename_str = filename;
            localPath += filename_str;
        } else if ("plan".equals(folder)) {//平面图目录
            filename_str = companyInfo2.getPlanName();
            localPath += filename_str;
        } else if ("evidence".equals(folder)) {//佐证文件目录
            String date = request.getParameter("date");
            JtMaterialEvidence jtMaterialEvidence = jianTaiDataServiceImpl.findEvidenceByCompanyName(companyInfo.get企业简称(), date);
            filename_str = jtMaterialEvidence.getFilename();
            localPath += filename_str;
        } else if ("product".equals(folder)) {//产品设备文件目录
            filename_str = companyInfo2.getProductName();
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



    //查找文件
    @SuppressWarnings("finally")
    @PostMapping(value = "/record/findImgsByLocal")
    @ResponseBody
    public String findImgsByLocal(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CompanyInfo companyInfo = (CompanyInfo) request.getSession().getAttribute("LOGIN_USER");
            //String localPath = request.getSession().getServletContext().getRealPath("/") + "\\page\\views\\upload\\file\\";
            String localPath = "D:\\upload\\plan\\";
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
