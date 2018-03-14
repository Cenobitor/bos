package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.StandardRepository;
import com.cenobitor.bos.service.base.StandardService;
import com.cenobitor.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:18 PM 12/03/2018
 * @Modified By:
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {

    @Resource(name = "standardRepository")
    private StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> pageQuery(Pageable pageable) {
        Page<Standard> page = standardRepository.findAll(pageable);

        return page;
    }
}
