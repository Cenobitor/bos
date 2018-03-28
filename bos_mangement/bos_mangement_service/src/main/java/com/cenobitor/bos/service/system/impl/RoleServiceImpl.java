package com.cenobitor.bos.service.system.impl;

import com.cenobitor.bos.dao.system.RoleRepository;
import com.cenobitor.bos.domain.system.Menu;
import com.cenobitor.bos.domain.system.Permission;
import com.cenobitor.bos.domain.system.Role;
import com.cenobitor.bos.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:03 PM 28/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> pageQuery(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


     /*menuIds: 11,341,113,118,114,116,112,111,115,117,14,146
    name: 张三
    keyword: 经理
    description: afdssd
    permissionIds: 1002
    permissionIds: 1003
    permissionIds: 1004*/

    @Override
    public void save(Role role, String menuIds, Long[] permissionIds) {
        Role role1 = roleRepository.save(role);

        //利用持久态对象特点,更新数据,减少连接数据库次数.
        if (StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuId));
                role1.getMenus().add(menu);
            }
        }

        if (permissionIds != null && permissionIds.length>0){
            for (Long permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                role1.getPermissions().add(permission);
            }
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
