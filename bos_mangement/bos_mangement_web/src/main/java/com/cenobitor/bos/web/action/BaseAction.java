package com.cenobitor.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 6:22 PM 15/03/2018
 * @Modified By:
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    private T model;

    @Override
    public T getModel() {
        if(model==null) {
            Class<? extends BaseAction> childClazz = this.getClass();
            ParameterizedType parameterizedType = (ParameterizedType) childClazz.getGenericSuperclass();
            Type[] types = parameterizedType.getActualTypeArguments();
            Class<T> clazz = (Class<T>) types[0];
            try {
                model = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    protected int page ;
    protected int rows ;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    public void page2json(Page<T> page,JsonConfig jsonConfig) throws IOException {

        long totalElements = page.getTotalElements();
        List<T> list = page.getContent();

        Map<String,Object> map = new HashMap<>();

        map.put("total",totalElements);
        map.put("rows",list);

        String json;
        //解决懒加载问题,灵活控制输出内容
        if (jsonConfig == null){
            json = JSONObject.fromObject(map).toString();
        }else {
            json = JSONObject.fromObject(map,jsonConfig).toString();
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void list2json(List list,JsonConfig jsonConfig) throws IOException {
        String json;
        //解决懒加载问题,灵活控制输出内容
        if (jsonConfig == null){

            json = JSONArray.fromObject(list).toString();
        }else {
            json = JSONArray.fromObject(list,jsonConfig).toString();
        }
        System.out.println(json);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
