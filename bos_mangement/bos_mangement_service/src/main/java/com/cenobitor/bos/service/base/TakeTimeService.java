package com.cenobitor.bos.service.base;

import com.cenobitor.bos.domain.base.TakeTime;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:05 PM 19/03/2018
 * @Modified By:
 */
public interface TakeTimeService {
    List<TakeTime> findAll();
}
