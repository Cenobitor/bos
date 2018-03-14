package com.cenobitor.bos.web.action.base;


import com.cenobitor.bos.domain.base.Standard;
import com.cenobitor.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;

import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:54 PM 12/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    @Resource(name = "standardServiceImpl")
    private StandardService standardService;

    private Standard model;

    @Override
    public Standard getModel() {
        if (model == null){
            model = new Standard();
        }
        return model;
    }

    @Action(value = "standardAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/base/standard.html")
    })
    public String save(){
        standardService.save(model);
        return SUCCESS;
    }

    //分页查询方法
    //使用属性驱动介绍页面提交的分页查询参数
    private int page ; // 当前页码
    private int rows ; //每页显示多少条数据

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows =  rows;
    }

    @Action(value = "standardAction_findAll" )
    public String findAll() throws IOException {

        Page<Standard> page = standardService.pageQuery(null);
        List<Standard> list = page.getContent();
        //将数组转换为json字符串
        String json = JSONArray.fromObject(list).toString();
        System.out.println(json);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        return NONE;
    }

    @Action(value = "standardAction_pageQuery" )
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);
        long totalElements = page.getTotalElements();
        List<Standard> list = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("total",totalElements);
        map.put("rows",list);
        String json = JSONObject.fromObject(map).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(json);

        return NONE;
    }

    @Action(value = "standardAction_batchDel",results = {
            @Result(name = "success",location = "/pages/base/standard.html",type = "redirect")
    })
    public String batchDel(){

        return SUCCESS;
    }

}
