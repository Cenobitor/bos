package com.cenobitor.bos.web.action.base;

import com.cenobitor.bos.domain.base.TakeTime;
import com.cenobitor.bos.service.base.TakeTimeService;
import com.cenobitor.bos.web.action.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 2:59 PM 19/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class TakeTimeAction extends BaseAction<TakeTime> {


    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "takeTimeAction_findAll")
    public String findAll() throws IOException {
        List<TakeTime> list = takeTimeService.findAll();
        list2json(list,null);
        return NONE;
    }

}
