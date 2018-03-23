package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.take_delivery.WayBill;
import com.cenobitor.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:07 PM 23/03/2018
 * @Modified By:
 */
@Repository
public interface WorkBillRepository extends JpaRepository<WorkBill,Long>{
}
