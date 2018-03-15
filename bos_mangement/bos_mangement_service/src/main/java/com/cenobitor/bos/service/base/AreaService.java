package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList; /**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:30 PM 15/03/2018
 * @Modified By:
 */
public interface AreaService {
    void save(ArrayList<Area> list);
    void save(Area area);

    Page<Area> pageQuery(Pageable pageable);
}
