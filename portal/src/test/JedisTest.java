package com.cenobitor.bos.fore.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 2:02 PM 21/03/2018
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JedisTest {
    @Autowired
    private RedisTemplate<String,String> template;

    @Test
    public void test01(){
        template.opsForValue().set("name","zhangsan");
    }

    @Test
    public void test02(){
        template.delete("name");
    }

    @Test
    public void test03(){
        template.opsForValue().set("name","李四",10, TimeUnit.SECONDS);
    }

    @Test
    public void test04(){

        System.out.println(template.opsForValue().get("13267138821"));
    }

}
