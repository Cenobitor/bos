package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:42 PM 14/03/2018
 * @Modified By:
 */
public interface CourierService {
    void save(Courier model);
    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

    void batchDel(String ids);

    List<Courier> findAvaible();

}
