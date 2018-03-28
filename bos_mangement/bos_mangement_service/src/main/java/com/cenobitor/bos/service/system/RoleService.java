package com.cenobitor.bos.service.system;

import com.cenobitor.bos.domain.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:03 PM 28/03/2018
 * @Modified By:
 */
public interface RoleService {
    Page<Role> pageQuery(Pageable pageable);

    void save(Role model, String menuIds, Long[] permissionIds);

    List<Role> findAll();

}
