package com.cenobitor.bos.web.action.take_delivery;

import com.cenobitor.bos.domain.take_delivery.WayBill;
import com.cenobitor.bos.service.take_delivery.WaybillService;
import com.cenobitor.bos.web.action.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:41 PM 25/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WayBillAction extends BaseAction<WayBill>{
    @Autowired
    private WaybillService waybillService;

    @Action(value = "waybillAction_save")
    public String findAll() throws IOException {
        String msg = "0";
        try {
            waybillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();
            msg = "1";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(msg);
        return NONE;
    }

}
