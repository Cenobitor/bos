package com.cenobitor.bos.web.action.base;

import com.cenobitor.bos.domain.base.Customer;
import com.cenobitor.bos.domain.base.FixedArea;
import com.cenobitor.bos.domain.base.SubArea;
import com.cenobitor.bos.service.base.FixedAreaService;
import com.cenobitor.bos.web.action.BaseAction;
import net.sf.json.JsonConfig;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 12:54 PM 18/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{

    @Autowired
    private FixedAreaService fixedAreaService;

    @Action( value = "fixedAreaAction_save" ,results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String save(){
        System.out.println(getModel());
        fixedAreaService.save(getModel());
        return SUCCESS;
    }


    @Action( value = "fixedAreaAction_pageQuery" )
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});//忽略相关字段防止互相调用引起的懒加载问题

        page2json(page,jsonConfig);

        return NONE;
    }

    @Action(value = "fixedAreaAction_findCustomersUnAssociated")
    public String findCustomersUnAssociated() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/webService/customerService/findCustomersUnAssociated")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        list2json(list,null);
        return NONE;
    }

    @Action(value = "fixedAreaAction_findCustomersAssociated2FixedArea")
    public String findCustomersAssociated2FixedArea() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/webService/customerService/findCustomersAssociated2FixedArea")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", getModel().getId())
                .getCollection(Customer.class);
        list2json(list,null);
        return NONE;
    }

    private Long[] customerIds;

    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String assignCustomers2FixedArea(){
        if (courierId != null){
            WebClient
                    .create("http://localhost:8180/webService/customerService/assignCustomers2FixedArea")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("customerIds",customerIds)
                    .query("fixedAreaId",getModel().getId())
                    .put(null);
        }else {
            WebClient
                    .create("http://localhost:8180/webService/customerService/assignCustomers2FixedArea")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("fixedAreaId",getModel().getId())
                    .put(null);
        }

        return SUCCESS;
    }

    private Long courierId;
    private Long takeTimeId;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Action(value = "fixedAreaAction_associationCourierToFixedArea",results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_findSubAreaAssociated")
    public String findSubAreaAssociated() throws IOException {
        List<SubArea> list = (List<SubArea>) fixedAreaService.findSubAreaAssociated(getModel().getId());

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedArea","area"});//忽略相关字段防止互相调用引起的懒加载问题

        list2json(list,jsonConfig);
        return NONE;
    }

    @Action(value = "fixedAreaAction_findSubAreaUnAssociated")
    public String findSubAreaUnAssociated() throws IOException {
        List<SubArea> list = (List<SubArea>) fixedAreaService.findSubAreaUnAssociated();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","area"});//忽略相关字段防止互相调用引起的懒加载问题

        list2json(list,jsonConfig);
        return NONE;
    }


    private Long[] subAreaIds;

    public void setSubAreaIds(Long[] subAreaIds) {
        this.subAreaIds = subAreaIds;
    }

    @Action(value = "fixedAreaAction_associationFixedAreaToSubArea",results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String associationFixedAreaToSubArea(){
        fixedAreaService.associationFixedAreaToSubArea(getModel().getId(),subAreaIds);
        return SUCCESS;
    }


}
