package com.cenobitor.activemq;

import com.aliyuncs.exceptions.ClientException;
import com.cenobitor.utils.SmsUtils;
import org.springframework.stereotype.Component;
import sun.applet.resources.MsgAppletViewer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:03 PM 25/03/2018
 * @Modified By:
 */
@Component
public class SMSConsumer implements MessageListener {


    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String tel = mapMessage.getString("tel");
            String code = mapMessage.getString("code");
            System.out.println(tel+"====="+code);
            SmsUtils.sendSms(tel,code);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
