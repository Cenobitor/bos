package com.cenobitor.bos.service.system;

import com.cenobitor.bos.domain.system.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:05 PM 28/03/2018
 * @Modified By:
 */

public interface MenuService {
    List<Menu> findAll();

    void save(Menu menu);

    Page<Menu> pageQuery(Pageable pageable);
}
