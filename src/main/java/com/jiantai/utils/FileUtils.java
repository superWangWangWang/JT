package com.jiantai.utils;

import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

public class FileUtils {
    //下载文件功能
    public static void   downloadFile(HttpServletResponse res, HttpServletRequest req, String fileName,String path) throws IOException {
        String localFilename = fileName;
        //String localFilepath = getClass().getResource("/templates/" + localFilename).getPath();
        String localFilepath = path;

        res.setContentType("multipart/form-data");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            // IE Core
            localFilename = java.net.URLEncoder.encode(localFilename, "UTF-8");
        } else {
            // Non-IE Core
            localFilename = new String((localFilename).getBytes("UTF-8"), "ISO-8859-1");
        }
        res.setHeader("Content-Disposition", "attachment;fileName=" + localFilename);

        localFilepath = URLDecoder.decode(localFilepath, "UTF-8");
        FileInputStream instream = new FileInputStream(localFilepath);
        ServletOutputStream outstream = res.getOutputStream();
        int b = 0;
        byte[] buffer = new byte[1024];
        while ((b = instream.read(buffer)) != -1) {
            outstream.write(buffer, 0, b);
        }
        instream.close();

        if (outstream != null) {
            outstream.flush();
            outstream.close();
        }

    }
}
