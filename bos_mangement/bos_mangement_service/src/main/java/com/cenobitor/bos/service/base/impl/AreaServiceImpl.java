package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.AreaRepository;
import com.cenobitor.bos.domain.base.Area;
import com.cenobitor.bos.service.base.AreaService;
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
 * @Date: Created in 5:30 PM 15/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Override
    public void save(ArrayList<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public void save(Area area) {
        areaRepository.save(area);
    }

    @Override
    public Page<Area> pageQuery(Pageable pageable) {
        Page<Area> page = areaRepository.findAll(pageable);
        return page;
    }

    @Override
    public List<Area> findQ(String q) {
        q = "%"+q.toUpperCase()+"%";
        List<Area> list = areaRepository.findQ(q);
        return list;
    }
}
