package com.cenobitor.utils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:52 PM 25/03/2018
 * @Modified By:
 */
public class SmsUtilsTest {

    @Test
    public void test01() throws ClientException {
        SendSmsResponse sendSmsResponse = SmsUtils.sendSms("13267138821","123546");
        System.out.println(sendSmsResponse.getCode());
    }

    public static void main(String[] args) throws ClientException {
        SmsUtils.sendSms("13267138821","12356");
    }

}