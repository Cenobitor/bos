package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:09 PM 28/03/2018
 * @Modified By:
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{

    List<Menu> findByParentMenuIsNull();

    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?1")
    List<Menu> findbyUid(Long id);
}
