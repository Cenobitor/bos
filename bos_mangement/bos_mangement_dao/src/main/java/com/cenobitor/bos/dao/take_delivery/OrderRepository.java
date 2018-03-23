package com.cenobitor.bos.dao.take_delivery;


import com.cenobitor.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 12:15 PM 23/03/2018
 * @Modified By:
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{


}
