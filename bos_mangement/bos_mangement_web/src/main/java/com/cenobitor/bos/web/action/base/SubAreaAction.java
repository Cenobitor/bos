package com.cenobitor.bos.web.action.base;

import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.domain.base.FixedArea;
import com.cenobitor.bos.domain.base.SubArea;
import com.cenobitor.bos.service.base.SubAreaService;
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

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:59 PM 16/03/2018
 * @Modified By:
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {


    @Autowired
    private SubAreaService subAreaService;

    @Action(value = "subAreaAction_save",results = {
            @Result(name = "success",location = "/pages/base/sub_area.html" ,type = "redirect")
    })
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "subAreaAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> page = subAreaService.pageQuery(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});//忽略相关字段防止互相调用引起的懒加载问题

        page2json(page,jsonConfig);

        return NONE;
    }

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Action(value = "subAreaAction_importXLS" ,results = {
            @Result(name = "success",location = "/pages/base/sub_area.html",type = "redirect")
    })
    public String importXLS(){

        ArrayList<SubArea> list = new ArrayList<>();
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

                String startNum = row.getCell(4).getStringCellValue();
                String endNum = row.getCell(5).getStringCellValue();
                Character single = Character.valueOf(row.getCell(6).getStringCellValue().charAt(0));
                String assistKeyWords = row.getCell(7).getStringCellValue();
                //截取到省市区的最后一个字符
                province = province.substring(0, province.length() - 1);
                city = city.substring(0,city.length()-1);
                district = district.substring(0,district.length()-1);

                Area area = new Area();
                area.setCity(city);
                area.setDistrict(district);
                area.setProvince(province);

                SubArea subArea = new SubArea();

                subArea.setStartNum(startNum);
                subArea.setEndNum(endNum);
                subArea.setSingle(single);
                subArea.setAssistKeyWords(assistKeyWords);
                subArea.setArea(area);

                list.add(subArea);
            }
            subAreaService.save(list);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
