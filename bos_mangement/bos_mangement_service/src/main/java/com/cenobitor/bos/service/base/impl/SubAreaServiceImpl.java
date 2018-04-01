package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.SubAreaRepository;
import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.domain.base.SubArea;
import com.cenobitor.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:05 PM 16/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;
    @Override
    public void save(SubArea subArea) {
        subAreaRepository.save(subArea);

    }

    @Override
    public Page<SubArea> pageQuery(Pageable pageable) {
        Page<SubArea> page = subAreaRepository.findAll(pageable);
        return page;
    }

    @Override
    public void save(ArrayList<SubArea> list) {
        subAreaRepository.save(list);
    }

    @Override
    public List<SubArea> findAll() {
        return subAreaRepository.findAll();
    }


    @Override
    public List<Object[]> getChartData() {
        return subAreaRepository.getChartData();
    }
}
