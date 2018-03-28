package com.cenobitor.bos.dao.system;

import com.cenobitor.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:06 PM 26/03/2018
 * @Modified By:
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsername(String username);
}
