package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:56 PM 28/03/2018
 * @Modified By:
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
    /*
    * select p from t_permission p
    *   inner join T_ROLE_PERMISSION rp on rp.c_permisson_id = p.c_id
    *   inner join t_role r on r.c_id = rp.c_role_id
    *   inner join t_user_role tr on tr.c_cuser_id = rp.c_role_id
    *   inner join t_user u where u.c_id = ?
    * */
    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?1")
    List<Permission> findbyUid(Long id);
}
