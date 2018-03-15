package com.cenobitor.bos.web.action.base;

import com.cenobitor.bos.domain.base.Courier;
import com.cenobitor.bos.domain.base.Standard;
import com.cenobitor.bos.service.base.CourierService;
import com.cenobitor.bos.web.action.BaseAction;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:30 PM 14/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {


    @Resource(name = "courierServiceImpl")
    private CourierService courierService;

    @Action(value = "courierAction_save",results = {
            @Result(name = "success",location = "/pages/base/courier.html",type = "redirect")
    })
    public String save(){

        courierService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {
        //构造查询条件
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String courierNum = getModel().getCourierNum();
                String company = getModel().getCompany();
                String type = getModel().getType();
                Standard standard = getModel().getStandard();
                ArrayList<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(courierNum)){
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class),courierNum);
                    list.add(p1);
                }
                if (StringUtils.isNotEmpty(company)){
                    Predicate p2 = cb.like(root.get("company").as(String.class), company);
                    list.add(p2);
                }
                if (StringUtils.isNotEmpty(type)){
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if (standard != null && StringUtils.isNotEmpty(standard.getName())){
                    String name = standard.getName();
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                    list.add(p4);
                }
                if (list.size() == 0 ){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }

        };

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Courier> page = courierService.pageQuery(specification,pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});

        page2json(page,jsonConfig);

        return NONE;
    }

    //批量作废
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_batchDel",results = {
            @Result(name = "success",location = "/pages/base/courier.html",type = "redirect")
    })
    public String batchDel(){
        courierService.batchDel(ids);
        return SUCCESS;
    }
}
