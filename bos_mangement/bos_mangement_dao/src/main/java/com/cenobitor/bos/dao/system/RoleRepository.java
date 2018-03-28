package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:04 PM 28/03/2018
 * @Modified By:
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
}
