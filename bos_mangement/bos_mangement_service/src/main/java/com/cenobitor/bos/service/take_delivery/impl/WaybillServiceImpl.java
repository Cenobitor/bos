package com.cenobitor.bos.service.take_delivery.impl;

import com.cenobitor.bos.dao.take_delivery.WaybillRepository;
import com.cenobitor.bos.domain.take_delivery.WayBill;
import com.cenobitor.bos.service.take_delivery.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:39 PM 25/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class WaybillServiceImpl implements WaybillService {
    @Autowired
    private WaybillRepository waybillRepository;

    @Override
    public void save(WayBill wayBill) {
        waybillRepository.save(wayBill);
    }
}
