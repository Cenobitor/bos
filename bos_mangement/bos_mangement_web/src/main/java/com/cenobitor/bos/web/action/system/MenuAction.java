package com.cenobitor.bos.web.action.system;

import com.cenobitor.bos.domain.base.SubArea;
import com.cenobitor.bos.domain.system.Menu;
import com.cenobitor.bos.service.system.MenuService;
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
 * @Date: Created in 4:01 PM 28/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu>{

    @Autowired
    private MenuService menuService;

    @Action(value = "menuAction_findAll")
    public String findAll() throws IOException {
        List<Menu> list = menuService.findAll();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu"});
        list2json(list,jsonConfig);
        return NONE;
    }

    @Action(value = "menuAction_save",results = {
            @Result(name = "success",type = "redirect",location = "/pages/system/menu.html")
    })
    public String save() {
        menuService.save(getModel());
        return NONE;
    }

    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) - 1, rows);
        Page<Menu> page = menuService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu"});//忽略相关字段防止互相调用引起的懒加载问题

        page2json(page,jsonConfig);

        return NONE;
    }


}
