package com.cenobitor.bos.service.system;

import com.cenobitor.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 9:03 PM 28/03/2018
 * @Modified By:
 */
public interface UserService {
    void save(User user, Long[] roleIds);

    Page<User> pageQuery(Pageable pageable);
}
