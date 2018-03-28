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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
}
