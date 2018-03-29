package com.cenobitor.bos.service.jobs;

import com.cenobitor.bos.dao.base.WorkBillRepository;
import com.cenobitor.bos.domain.take_delivery.WorkBill;
import com.cenobitor.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:14 PM 29/03/2018
 * @Modified By:
 */
@Component
public class WorkbillJob {

    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendEmail(){
        List<WorkBill> list = workBillRepository.findAll();
        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";
        for (WorkBill workBill : list) {
            emailBody+=workBill.getId()+"\t"+
                    workBill.getCourier()+"\t"+
                    workBill.getPickstate()+"\t"+
                    workBill.getBuildtime().toLocaleString()+"\t"+"<br/>";
        }
        MailUtils.sendMail("1037345628@qq.com","工单信息统计",emailBody);
        System.out.println("邮件已发送");
    }

}
