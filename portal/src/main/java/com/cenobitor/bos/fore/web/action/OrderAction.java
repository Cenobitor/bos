package com.cenobitor.bos.fore.web.action;

import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 12:03 PM 23/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model;

    @Override
    public Order getModel() {
        if (model == null){
            model = new Order();
        }
        return model;
    }

    private String sendAreaInfo;
    private String recAreaInfo;

    public String getSendAreaInfo() {
        return sendAreaInfo;
    }

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public String getRecAreaInfo() {
        return recAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "orderAction_add",results = {
            @Result(name = "success",location = "/index.html",type = "redirect"),
            @Result(name = "error",location = "/order.html")
    })
    public String add(){
        if (StringUtils.isNotEmpty(sendAreaInfo)){
            //安徽省/芜湖市/弋江区
            String[] sendAreaInfos = sendAreaInfo.split("/");

            String province = sendAreaInfos[0];
            String city = sendAreaInfos[1];
            String district = sendAreaInfos[2];

            province = province.substring(0,province.length()-1);
            city = city.substring(0,city.length()-1);
            district = district.substring(0,district.length()-1);

            Area sendArea = new Area();
            sendArea.setProvince(province);
            sendArea.setCity(city);
            sendArea.setDistrict(district);

            model.setSendArea(sendArea);

        } else if (StringUtils.isNotEmpty(recAreaInfo)){
            //安徽省/芜湖市/弋江区
            String[] recAreaInfos = recAreaInfo.split("/");

            String province = recAreaInfos[0];
            String city = recAreaInfos[1];
            String district = recAreaInfos[2];

            province = province.substring(0,province.length()-1);
            city = city.substring(0,city.length()-1);
            district = district.substring(0,district.length()-1);

            Area recArea = new Area();
            recArea.setProvince(province);
            recArea.setCity(city);
            recArea.setDistrict(district);

            model.setRecArea(recArea);

        }else {
            return ERROR;
        }
        WebClient.create("http://localhost:8080/webService/orderService/save")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(model);
        return SUCCESS;
    }

}
