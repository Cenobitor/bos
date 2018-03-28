package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:56 PM 28/03/2018
 * @Modified By:
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
}
