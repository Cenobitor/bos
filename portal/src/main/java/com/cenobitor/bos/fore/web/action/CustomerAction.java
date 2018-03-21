package com.cenobitor.bos.fore.web.action;

import com.aliyuncs.exceptions.ClientException;
import com.cenobitor.crm.domain.Customer;
import com.cenobitor.utils.MailUtils;
import com.cenobitor.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


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
//        try {
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        ServletActionContext.getRequest().getSession().setAttribute("serverCode",code);
//            SmsUtils.sendSms(model.getTelephone(),code);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        return NONE;
    }

    @Action(value = "customerAction_checkTelephone")
    public String checkTelephone() throws IOException {
        if (StringUtils.isNotEmpty(model.getTelephone())){

            Customer customer = WebClient.create("http://localhost:8180/webService/customerService/checkCustomer")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .get(Customer.class);
            if (customer == null ){
                ServletActionContext.getResponse().getWriter().write("1");
                return NONE;
            }
        }
        return NONE;
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
            //生成激活码
            String activeCode = RandomStringUtils.randomNumeric(32);
            redisTemplate.opsForValue().set(model.getTelephone(),activeCode,1, TimeUnit.DAYS);
            String emailBody = "感谢您注册本网站的账号,请在24小时之内点击<a href='http://localhost:8280/customerAction_active.action?activeCode="
                    +activeCode+"&telephone="+model.getTelephone()+"'>本链接</a>激活您的账号";
            System.out.println(emailBody);
            MailUtils.sendMail(model.getEmail(),"激活账号邮件",emailBody);

            return SUCCESS;
        }
        return ERROR;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }


    @Action(value = "customerAction_active",results = {
            @Result(name = "success",location = "/login.html",type = "redirect"),
            @Result(name = "error",location = "/signup-fail.html",type = "redirect")
    })
    public String active(){
        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
        if (StringUtils.isNotEmpty(activeCode)
                && StringUtils.isNotEmpty(model.getTelephone())
                && activeCode.equals(serverCode)){
            WebClient.create("http://localhost:8180/webService/customerService/active")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("telephone",model.getTelephone())
                    .put(null);
            return SUCCESS;
        }
        return ERROR;
    }

    @Action(value = "customerAction_checkValidateCode")
    public String checkValidateCode() throws IOException {
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        if (checkcode != null && checkcode.equals(validateCode)){
            ServletActionContext.getResponse().getWriter().write("1");
        }
        return NONE;
    }


    @Action(value = "customerAction_login",results = {
            @Result(name = "success",location = "/index.html",type = "redirect"),
            @Result(name = "unactived",location = "signup-success.html",type = "redirect")
    })
    public String login(){

        Customer customer = WebClient.create("http://localhost:8180/webService/customerService/login")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .query("password", model.getPassword())
                .get(Customer.class);
        if (customer != null && customer.getType() != null && customer.getType() == 1){
            ServletActionContext.getRequest().getSession().setAttribute("user",customer);
            return SUCCESS;
        }
        return "unactived";
    }
}

