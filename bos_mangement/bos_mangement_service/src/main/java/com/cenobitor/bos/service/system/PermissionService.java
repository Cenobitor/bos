package com.cenobitor.bos.service.system;

import com.cenobitor.bos.domain.system.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:54 PM 28/03/2018
 * @Modified By:
 */
public interface PermissionService {
    Page<Permission> pageQuery(Pageable pageable);

    void save(Permission permission);

    List<Permission> findAll();

}
