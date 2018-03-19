package com.cenobitor.crm.service.impl;

import com.cenobitor.crm.dao.CustomerRepository;
import com.cenobitor.crm.domain.Customer;
import com.cenobitor.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:09 PM 18/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssociated() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(Long[] customerIds, String fixedAreaId) {
        if (StringUtils.isNotEmpty(fixedAreaId)){
            customerRepository.unbindByFixedAreaId(fixedAreaId);
            if (customerIds != null && customerIds.length > 0){
                for (Long id : customerIds) {
                    customerRepository.bindFixedAreaById(fixedAreaId,id);
                }
            }
        }
    }
}
