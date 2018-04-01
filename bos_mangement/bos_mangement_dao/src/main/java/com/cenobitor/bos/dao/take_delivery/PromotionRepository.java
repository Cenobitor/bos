package com.cenobitor.bos.dao.take_delivery;

import com.cenobitor.bos.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:21 PM 31/03/2018
 * @Modified By:
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long>{
}
