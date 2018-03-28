package com.cenobitor.bos.web.action.system;


import com.cenobitor.bos.domain.system.Role;
import com.cenobitor.bos.domain.system.User;
import com.cenobitor.bos.service.system.UserService;
import com.cenobitor.bos.web.action.BaseAction;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
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

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;


/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:27 PM 15/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

    @Autowired
    private UserService userService;

    private String checkCode;

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    @Action(value = "userAction_login",results = {
            @Result(name = "success",location = "/index.html",type = "redirect"),
            @Result(name = "error",location = "/login.html",type = "redirect")
    })
    public String login(){
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");

        if (StringUtils.isNotEmpty(checkCode) && StringUtils.isNotEmpty(serverCode)){
            Subject subject = SecurityUtils.getSubject();

            AuthenticationToken token = new UsernamePasswordToken(
                    getModel().getUsername(),getModel().getPassword());

            try {
                subject.login(token);
                //方法的返回值有Realm中doGetAuthenticationInfo方法定义SimpleAuthenticationInfo对象的时候,第一个参数决定的
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("user",user);
                return SUCCESS;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                System.out.println("用户名或密码错误");
            }
        }
        return ERROR;
    }

    @Action(value = "userAction_logout",results = {
            @Result(name = "login",location = "/login.html",type = "redirect")
    })
    public String logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "login";
    }

    @Action(value = "userAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/system/userlist.html")
    })
    public String save() {
        userService.save(getModel());
        return SUCCESS;
    }


    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<User> page = userService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});

        page2json(page,jsonConfig);

        return NONE;
    }

}
