package com.cenobitor.bos.service.take_delivery.impl;

import com.cenobitor.bos.dao.base.AreaRepository;
import com.cenobitor.bos.dao.base.FixedAreaRepository;
import com.cenobitor.bos.dao.base.TakeTimeRepository;
import com.cenobitor.bos.dao.base.WorkBillRepository;
import com.cenobitor.bos.dao.take_delivery.OrderRepository;
import com.cenobitor.bos.domain.base.*;
import com.cenobitor.bos.domain.take_delivery.Order;
import com.cenobitor.bos.domain.take_delivery.WorkBill;
import com.cenobitor.bos.service.take_delivery.OrderService;
import com.cenobitor.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 12:14 PM 23/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Autowired
    private WorkBillRepository workBillRepository;


    @Override
    public void save(Order order) {
        Area sendArea = order.getSendArea();
        if (sendArea != null){
            sendArea = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(sendArea);
        }
        Area recArea = order.getRecArea();
        if (recArea != null){
            recArea = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
            order.setSendArea(recArea);
        }
        order.setOrderNum(UUID.randomUUID().toString().replace("-",""));
        order.setOrderTime(new Date());

        orderRepository.save(order);

        //自动分单
        //先根据发件地址查找customer表
        String sendAddress = order.getSendAddress();
        Customer customer = WebClient.create("http://localhost:8180/webService/customerService/fixedAreaId")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .query("sendAddress",sendAddress)
                .get(Customer.class);
        //完全匹配,返回fixedAreaId
        //判断fixedAreaId是否为空
        if (customer != null && StringUtils.isNotEmpty(customer.getFixedAreaId())){
            //非空,根据fixedAreaId查找fixedArea对象,提取定区关联的快递员
            String fixedAreaId =customer.getFixedAreaId();
            FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));

            if (automaticOrder(order, fixedArea)) return;

        }else{
            if (sendArea != null){
                Set<SubArea> subareas = sendArea.getSubareas();
                for (SubArea subarea : subareas) {
                    String keyWords = subarea.getKeyWords();
                    String assistKeyWords = subarea.getAssistKeyWords();
                    if (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)){
                        FixedArea fixedArea = subarea.getFixedArea();
                        if (fixedArea != null){
                            if (automaticOrder(order, fixedArea)) return;
                        }
                    }
                }
            }
        }
        order.setOrderType("人工分单");
    }

    private boolean automaticOrder(Order order, FixedArea fixedArea) {
        Set<Courier> couriers = fixedArea.getCouriers();
        if (couriers.size() != 0){
            //判断当前时间+2h是否在指定区间8:00~14:00 14:00~20:00
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-12);

            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            TakeTime takeTime;


            if (calendar.get(Calendar.DAY_OF_WEEK) >1 && calendar.get(Calendar.DAY_OF_WEEK) <7){
                takeTime = takeTimeRepository.findIdWorkedDay(hour);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1){
                takeTime = takeTimeRepository.findIdSun(hour);
            } else {
                takeTime = takeTimeRepository.findIdSat(hour);
            }
            //根据订单时间查找时间表返回时间表ID匹配快递员
            if (takeTime != null){
                for (Courier courier : couriers) {

                    if (courier.getTakeTime().getId() == takeTime.getId() && courier.getDeltag() == null){

                        order.setCourier(courier);
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setType("新");
                        workBillRepository.save(workBill);
                        order.setOrderType("自动分单");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
