package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.FixedArea;
import com.cenobitor.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 1:00 PM 18/03/2018
 * @Modified By:
 */
public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId);

    void associationFixedAreaToSubArea(FixedArea id, Long[] subAreaId);

    List<SubArea> findSubAreaUnAssociated();

    List<SubArea> findSubAreaAssociated(Long id);
}
