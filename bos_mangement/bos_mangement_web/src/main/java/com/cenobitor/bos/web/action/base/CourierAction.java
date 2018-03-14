package com.cenobitor.bos.web.action.base;

import com.cenobitor.bos.domain.base.Courier;
import com.cenobitor.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
 * @Date: Created in 3:30 PM 14/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

    private Courier model;

    @Override
    public Courier getModel() {
        if (model == null){
            model = new Courier();
        }
        return model;
    }
    @Resource(name = "courierServiceImpl")
    private CourierService courierService;

    @Action(value = "courierAction_save",results = {
            @Result(name = "success",location = "/pages/base/courier.html",type = "redirect")
    })
    public String save(){
        courierService.save(model);
        return SUCCESS;
    }



    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Courier> page = courierService.pageQuery(pageable);
        long totalElements = page.getTotalElements();
        List<Courier> list = page.getContent();

        Map<String,Object> map = new HashMap<>();

        map.put("total",totalElements);
        map.put("rows",list);

        //解决懒加载问题,灵活控制输出内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        String json = JSONObject.fromObject(map,jsonConfig).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        return NONE;
    }



    //批量作废
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_batchDel",results = {
            @Result(name = "success",location = "/pages/base/courier.html",type = "redirect")
    })
    public String batchDel(){
        courierService.batchDel(ids);
        return SUCCESS;
    }
}
