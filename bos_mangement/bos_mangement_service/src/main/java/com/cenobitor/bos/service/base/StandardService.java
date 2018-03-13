package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:17 PM 12/03/2018
 * @Modified By:
 */
public interface StandardService {
    void save(Standard standard);

    Page<Standard> pageQuery(Pageable pageable);
}
