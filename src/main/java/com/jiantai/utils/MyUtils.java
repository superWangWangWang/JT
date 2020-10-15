package com.jiantai.utils;

import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class MyUtils {
    /**
     *  创建文件上传路径
     * @param path
     */
    public static void mkdir(String path) {
        File fd = null;
        try {
            fd = new File(path);
            if (!fd.exists()) {
                fd.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fd = null;
        }
    }

    /**
     * 下载本地文件
     * @param response
     * @param filePath
     * @param encode
     */
    public static void downloadFile(HttpServletResponse response, String filePath, String encode) {
        response.setContentType("text/html;charset=" + encode);
        try {
            // 读到流中
            InputStream inStream = new FileInputStream(filePath); // 文件的存放路径
            // path是指欲下载的文件的路径
            File file = new File(filePath);
            // 取得文件名
            String fileName = file.getName();
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(encode), "ISO8859-1") + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException  e) {
            System.out.println(e);
        }
    }

    /**
     * 校验8位字符串是否为正确的日期格式
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean result = true;
        //判断字符串长度是否为8位
        if(str.length() == 7){//2020-08
            // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            try {
                // 设置lenient为false.
                // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
                format.setLenient(false);
                format.parse(str);
            } catch (ParseException e) {
                // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                result = false;
            }
        }else{
            result = false;
        }

        return result;
    }

    /**
     * 字符串匹配将大写字符改为小写加下划线
     * @param param
     * @return
     */
    public static String upperCharToUnderLine(String param) {
        Pattern  p= Pattern.compile("[A-Z]");
        if(param==null ||param.equals("")){
            return "";
        }
        StringBuilder builder=new StringBuilder(param);
        Matcher mc=p.matcher(param);
        int i=0;
        while (mc.find()) {
            System.out.println(builder.toString());
            System.out.println("mc.start():" + mc.start() + ", i: " + i);
            System.out.println("mc.end():" + mc.start() + ", i: " + i);
            builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());
            i++;
        }

        if('_' == builder.charAt(0)){
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if(agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else if(agent.contains ("Safari")) {
            filename = new String (filename.getBytes ("utf-8"),"ISO8859-1");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
