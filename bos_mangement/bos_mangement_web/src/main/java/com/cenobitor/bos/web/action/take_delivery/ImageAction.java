package com.cenobitor.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.aspectj.util.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 9:17 PM 25/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class ImageAction extends ActionSupport {

    private File imgFile;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    private String imgFileFileName;

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    @Action(value = "imageAction_upload")
    public String upload() throws IOException {
        Map<String,Object> map = new HashMap<>();
        try {

            String dirPath = "/upload/";
            ServletContext servletContext = ServletActionContext.getServletContext();
            String realPath = servletContext.getRealPath(dirPath);

            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString().replaceAll("-","")+suffix;
            File destFile = new File(realPath+"/"+fileName);

            FileUtil.copyFile(imgFile,destFile);

            String contextPath = ServletActionContext.getServletContext().getContextPath();

            map.put("error",0);
            map.put("url",contextPath+dirPath+fileName);


        } catch (IOException e) {
            map.put("error",1);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);

        return NONE;
    }

    @Action(value = "imageAction_manager")
    public String manager() throws IOException {

        String dirPath = "upload";
        ServletContext servletContext = ServletActionContext.getServletContext();
        String realPath = servletContext.getRealPath(dirPath);

        File saveDir = new File(realPath);

        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if(saveDir.listFiles() != null) {
            for (File file : saveDir.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        //封装回写客户端的数据
        HashMap<String, Object> map = new HashMap<>();
        //指定所有文件的信息
        map.put("file_list",fileList);
        //指定保存的文件夹路径
        //路径格式:/upload
        map.put("current_url",ServletActionContext.getServletContext().getContextPath()+"/"+dirPath+"/");

        //向客户端回写数据
        String json = JSONObject.fromObject(map).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        return NONE;
    }
}
