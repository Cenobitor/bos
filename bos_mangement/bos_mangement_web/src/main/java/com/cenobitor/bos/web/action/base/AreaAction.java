package com.cenobitor.bos.web.action.base;


import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.service.base.AreaService;
import com.cenobitor.bos.web.action.BaseAction;
import com.cenobitor.utils.PinYin4jUtils;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:27 PM 15/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction  extends BaseAction<Area> {

    private File file;

    public void setFile(File file) {
        this.file = file;
    }
    @Autowired
    AreaService areaService;


    @Action(value = "areaAction_save",results = {
            @Result(name = "success",location = "/pages/base/area.html",type = "redirect")
    })
    public String save(){
        areaService.save(getModel());
        return SUCCESS;
    }



    @Action(value = "areaAction_importXLS",results = {
            @Result(name = "success",location = "/pages/base/area.html",type = "redirect")
    })
    public String importXLS(){
        ArrayList<Area> list = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));

            HSSFSheet sheetAt = workbook.getSheetAt(0);

            for (Row row : sheetAt) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                //读取表格数据
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                //截取到省市区的最后一个字符
                province = province.substring(0, province.length() - 1);
                city = city.substring(0,city.length()-1);
                district = district.substring(0,district.length()-1);
                //获得城市编码
                String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district, true);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString).toUpperCase();

                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setCitycode(citycode);
                area.setPostcode(postcode);
                area.setShortcode(shortcode);
                list.add(area);
            }
            areaService.save(list);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "areaAction_pageQuery" )
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageable);

        //解决懒加载问题,灵活控制输出内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});

        page2json(page,jsonConfig);

        return NONE;
    }

}
