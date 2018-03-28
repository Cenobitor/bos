package com.cenobitor.bos.service.system.impl;

import com.cenobitor.bos.dao.system.MenuRepository;
import com.cenobitor.bos.domain.system.Menu;
import com.cenobitor.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:06 PM 28/03/2018
 * @Modified By:
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu menu) {
        Menu parentMenu = menu.getParentMenu();
        if (parentMenu != null && parentMenu.getId() == null ){
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }

    @Override
    public Page<Menu> pageQuery(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }
}

