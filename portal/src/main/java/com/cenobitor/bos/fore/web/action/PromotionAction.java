package com.cenobitor.bos.fore.web.action;

import com.cenobitor.bos.domain.take_delivery.PageBean;
import com.cenobitor.bos.domain.take_delivery.Promotion;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:49 PM 31/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends ActionSupport{
    private int pageIndex;

    private int pageSize;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Action("promotionAction_pageQuery")
    public String pageQuery() throws IOException {
        PageBean<Promotion> pageBean = WebClient.create("http://localhost:8080/webService/promotionService/pageQuery")
                .query("page", pageIndex)
                .query("size", pageSize)
                .accept(MediaType.APPLICATION_JSON)
                .get(PageBean.class);
        String json = JSONObject.fromObject(pageBean).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
}
