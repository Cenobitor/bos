package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.FixedAreaRepository;
import com.cenobitor.bos.domain.base.FixedArea;
import com.cenobitor.bos.domain.base.SubArea;
import com.cenobitor.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 1:01 PM 18/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        Page<FixedArea> page = fixedAreaRepository.findAll(pageable);
        return page;
    }
}
