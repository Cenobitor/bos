package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:43 PM 14/03/2018
 * @Modified By:
 */
@Repository
public interface CourierRepository extends JpaRepository<Courier,Long> {
    @Modifying
    @Query("update Courier set deltag = 1 where id = ?")
    void updateDelTagsById(Long id);
}
