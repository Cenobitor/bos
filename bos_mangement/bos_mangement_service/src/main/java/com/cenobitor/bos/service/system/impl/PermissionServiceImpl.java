package com.cenobitor.bos.service.system.impl;

import com.cenobitor.bos.dao.system.PermissionRepository;
import com.cenobitor.bos.domain.system.Permission;
import com.cenobitor.bos.service.system.PermissionService;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:55 PM 28/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> pageQuery(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
}
