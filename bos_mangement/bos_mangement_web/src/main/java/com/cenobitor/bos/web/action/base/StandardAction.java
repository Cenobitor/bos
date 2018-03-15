package com.cenobitor.bos.web.action.base;


import com.cenobitor.bos.domain.base.Standard;
import com.cenobitor.bos.service.base.StandardService;
import com.cenobitor.bos.web.action.BaseAction;
import net.sf.json.JSONArray;
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
import java.util.List;

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
public class StandardAction extends BaseAction<Standard> {

    @Resource(name = "standardServiceImpl")
    private StandardService standardService;


    @Action(value = "standardAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/base/standard.html")
    })
    public String save(){

        standardService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "standardAction_findAll" )
    public String findAll() throws IOException {

        Page<Standard> page = standardService.pageQuery(null);
        List<Standard> list = page.getContent();
        //将数组转换为json字符串
        String json = JSONArray.fromObject(list).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        return NONE;
    }

    @Action(value = "standardAction_pageQuery" )
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);
        page2json(page,null);
        return NONE;
    }

    @Action(value = "standardAction_batchDel",results = {
            @Result(name = "success",location = "/pages/base/standard.html",type = "redirect")
    })
    public String batchDel(){

        return SUCCESS;
    }

}
