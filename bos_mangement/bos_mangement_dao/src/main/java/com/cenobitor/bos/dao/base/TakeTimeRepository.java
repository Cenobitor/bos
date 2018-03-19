package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:07 PM 19/03/2018
 * @Modified By:
 */
@Repository
public interface TakeTimeRepository extends JpaRepository<TakeTime,Long>{


}
