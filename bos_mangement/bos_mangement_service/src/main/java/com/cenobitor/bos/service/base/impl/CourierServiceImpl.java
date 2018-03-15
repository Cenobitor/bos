package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.CourierRepository;
import com.cenobitor.bos.domain.base.Courier;
import com.cenobitor.bos.service.base.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:42 PM 14/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void save(Courier model) {
        courierRepository.save(model);

    }

    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        Page<Courier> page = courierRepository.findAll(specification,pageable);
        return page;
    }

    @Override
    public void batchDel(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] strings = ids.split(",");
            for (String string : strings) {
                courierRepository.updateDelTagsById(Long.parseLong(string));
            }
        }
    }
}
