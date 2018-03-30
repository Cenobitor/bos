package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:04 PM 28/03/2018
 * @Modified By:
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    @Query("select r from Role r inner join r.users u where u.id = ?1")
    List<Role> findbyUid(Long id);
}
