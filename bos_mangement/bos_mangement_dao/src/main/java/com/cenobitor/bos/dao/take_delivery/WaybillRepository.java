package com.cenobitor.bos.dao.take_delivery;

import com.cenobitor.bos.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:36 PM 25/03/2018
 * @Modified By:
 */
@Repository
public interface WaybillRepository extends JpaRepository<WayBill,Long> {


}
