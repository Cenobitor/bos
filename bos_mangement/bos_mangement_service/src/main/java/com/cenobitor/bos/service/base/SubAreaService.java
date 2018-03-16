package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:05 PM 16/03/2018
 * @Modified By:
 */
public interface SubAreaService {
    void save(SubArea subArea);

    Page<SubArea> pageQuery(Pageable pageable);

    void save(ArrayList<SubArea> list);
}
