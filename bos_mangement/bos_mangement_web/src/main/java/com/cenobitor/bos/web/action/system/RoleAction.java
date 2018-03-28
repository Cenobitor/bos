package com.cenobitor.bos.web.action.system;

import com.cenobitor.bos.domain.system.Permission;
import com.cenobitor.bos.domain.system.Role;
import com.cenobitor.bos.service.system.PermissionService;
import com.cenobitor.bos.service.system.RoleService;
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
 * @Date: Created in 7:01 PM 28/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

    @Autowired
    public RoleService roleService;


    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Role> page = roleService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users","permissions","menus"});

        page2json(page,jsonConfig);

        return NONE;
    }

    private Long[] permissionIds;

    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    private String menuIds;

    public void setMenuIds(String menuIds){
        this.menuIds = menuIds;
    }

    @Action(value = "roleAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/system/role.html")
    })
    public String save() {
        roleService.save(getModel(),menuIds,permissionIds);
        return SUCCESS;
    }


    @Action(value = "roleAction_findAll")
    public String findAll() throws IOException {
        List<Role> list = roleService.findAll();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users","permissions","menus"});
        list2json(list,jsonConfig);
        return NONE;
    }
}
