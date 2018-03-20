package com.cenobitor.bos.service.base.impl;

import com.cenobitor.bos.dao.base.CourierRepository;
import com.cenobitor.bos.dao.base.FixedAreaRepository;
import com.cenobitor.bos.dao.base.SubAreaRepository;
import com.cenobitor.bos.dao.base.TakeTimeRepository;
import com.cenobitor.bos.domain.base.*;
import com.cenobitor.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        Page<FixedArea> page = fixedAreaRepository.findAll(pageable);
        return page;
    }

    @Override
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        // 建立快递员和时间的关联
        courier.setTakeTime(takeTime);
        // 建立快递员和定区的关联
        fixedArea.getCouriers().add(courier);
    }

    @Override
    public void associationFixedAreaToSubArea(Long fixedAreaId, Long[] subAreaIds) {

        if (fixedAreaId != null){
            FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
            List<SubArea> subAreas = subAreaRepository.findByFixedAreaId(fixedAreaId);
            for (SubArea subArea : subAreas) {
                subArea.setFixedArea(null);
            }
            if (subAreaIds != null && subAreaIds.length > 0){
                for (Long subAreaId : subAreaIds) {
                    SubArea subArea = subAreaRepository.findOne(subAreaId);
                    subArea.setFixedArea(fixedArea);
                }
            }
        }
    }

    @Override
    public List<SubArea> findSubAreaUnAssociated() {
        return subAreaRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<SubArea> findSubAreaAssociated(Long id) {
        return subAreaRepository.findByFixedAreaId(id);
    }
}
