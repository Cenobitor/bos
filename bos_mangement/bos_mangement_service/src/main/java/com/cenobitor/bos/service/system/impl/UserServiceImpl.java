package com.cenobitor.bos.service.system.impl;

import com.cenobitor.bos.dao.system.UserRepository;
import com.cenobitor.bos.domain.system.User;
import com.cenobitor.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 9:03 PM 28/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> pageQuery(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
