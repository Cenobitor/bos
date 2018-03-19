package com.cenobitor.bos.fore.web.action;

import com.aliyuncs.exceptions.ClientException;
import com.cenobitor.crm.domain.Customer;
import com.cenobitor.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;


/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:20 PM 19/03/2018
 * @Modified By:
 */
@Controller
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer model;

    @Override
    public Customer getModel() {
        if (model == null){
            model = new Customer();
        }
        return model;
    }

    @Action(value = "customerAction_sendSMS")
    public String sendSMS(){
        try {
            String code = RandomStringUtils.randomNumeric(6);
            ServletActionContext.getRequest().getSession().setAttribute("serverCode",code);
            SmsUtils.sendSms(model.getTelephone(),code);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "customerAction_regist",results = {
            @Result(name = "success",location = "/signup-success.html",type = "redirect"),
            @Result(name = "error",location = "/signup-fail.html",type = "redirect")
    })
    public String regist(){
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
        if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode) && checkcode.equalsIgnoreCase(serverCode)){
            WebClient.create("http://localhost:8180/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(model);
            return SUCCESS;
        }
        return ERROR;
    }
}
