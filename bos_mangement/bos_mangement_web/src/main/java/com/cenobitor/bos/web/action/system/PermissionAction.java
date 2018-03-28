package com.cenobitor.bos.web.action.system;

import com.cenobitor.bos.domain.system.Menu;
import com.cenobitor.bos.domain.system.Permission;
import com.cenobitor.bos.service.system.PermissionService;
import com.cenobitor.bos.web.action.BaseAction;
import net.sf.json.JsonConfig;
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
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:52 PM 28/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission>{

   @Autowired
   public PermissionService permissionService;


    @Action(value = "permissionAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Permission> page = permissionService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});

        page2json(page,jsonConfig);

        return NONE;
    }
    @Action(value = "permissionAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/system/permission.html")
    })
    public String save() {
        permissionService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "permissionAction_findAll")
    public String findAll() throws IOException {
        List<Permission> list = permissionService.findAll();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        list2json(list,jsonConfig);
        return NONE;
    }
}
