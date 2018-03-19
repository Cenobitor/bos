package com.cenobitor.bos.service.base;

import com.cenobitor.bos.dao.base.TakeTimeRepository;
import com.cenobitor.bos.domain.base.TakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:06 PM 19/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
