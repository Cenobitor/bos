package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:42 PM 14/03/2018
 * @Modified By:
 */
public interface CourierService {
    void save(Courier model);
    Page<Courier> pageQuery(Pageable pageable);

    void batchDel(String ids);

}
