package com.cenobitor.bos.service.take_delivery.impl;

import com.cenobitor.bos.dao.take_delivery.PromotionRepository;
import com.cenobitor.bos.domain.take_delivery.PageBean;
import com.cenobitor.bos.domain.take_delivery.Promotion;
import com.cenobitor.bos.service.take_delivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:19 PM 31/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> pageQuery(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> pageQuery4Fore(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        Page<Promotion> p = promotionRepository.findAll(pageable);
        PageBean<Promotion> pageBean = new PageBean<>();
        pageBean.setRows(p.getContent());
        pageBean.setTotal(p.getTotalElements());
        return pageBean;
    }


}
