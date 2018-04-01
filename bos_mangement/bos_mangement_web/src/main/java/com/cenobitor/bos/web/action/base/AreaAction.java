package com.cenobitor.bos.web.action.base;


import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.service.base.AreaService;
import com.cenobitor.bos.web.action.BaseAction;
import com.cenobitor.utils.FileUtils;
import com.cenobitor.utils.PinYin4jUtils;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    private AreaService areaService;


    @Action(value = "areaAction_save",results = {
            @Result(name = "success",location = "/pages/base/area.html",type = "redirect")
    })
    public String save(){
        areaService.save(getModel());
        return SUCCESS;
    }

    private String q;

    public void setQ(String q) {
        this.q = q;
    }

    @Action(value = "areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list;
        if (StringUtils.isNotEmpty(q)){
            list = areaService.findQ(q);
        }else {
            Page<Area> page = areaService.pageQuery(null);
            list = page.getContent();
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});

        list2json(list,jsonConfig);

        return NONE;
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

    @Action(value = "areaAction_exportExcel")
    public String exportExcel() throws IOException {

        Page<Area> page = areaService.pageQuery(null);
        List<Area> list = page.getContent();

        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();
        //3.创建标题行
        HSSFRow titlerRow = sheet.createRow(0);
        titlerRow.createCell(0).setCellValue("省");
        titlerRow.createCell(1).setCellValue("市");
        titlerRow.createCell(2).setCellValue("区");
        titlerRow.createCell(3).setCellValue("邮编");
        titlerRow.createCell(4).setCellValue("简码");
        titlerRow.createCell(5).setCellValue("城市编码");

        //4.遍历数据,创建数据行
        for (Area area : list) {
            //获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
        //5.创建文件名
        String fileName = "区域数据统计.xls";
        //6.获取输出流对象
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();

        //7.获取mimeType
        ServletContext servletContext = ServletActionContext.getServletContext();
        String mimeType = servletContext.getMimeType(fileName);
        //8.获取浏览器信息,对文件名进行重新编码
        HttpServletRequest request = ServletActionContext.getRequest();
        fileName = FileUtils.filenameEncoding(fileName, request);

        //9.设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //10.写出文件,关闭流
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();

        return NONE;
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

    @Action(value = "areaAction_chart")
    public String chart() throws IOException {
        List<Object[]> list = areaService.getChartData();
        list2json(list,null);

        return NONE;
    }

}
