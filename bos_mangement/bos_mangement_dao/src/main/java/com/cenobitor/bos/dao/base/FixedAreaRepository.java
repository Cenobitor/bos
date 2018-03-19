package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 1:03 PM 18/03/2018
 * @Modified By:
 */
@Repository
public interface FixedAreaRepository extends JpaRepository<FixedArea,Long>{
}
