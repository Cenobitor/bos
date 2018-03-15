package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:32 PM 15/03/2018
 * @Modified By:
 */
@Repository
public interface AreaRepository extends JpaRepository<Area,Long>{
}
