package com.cenobitor.crm.dao;

import com.cenobitor.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:11 PM 18/03/2018
 * @Modified By:
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?1")
    void unbindByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = ?1 where id = ?2")
    void bindFixedAreaById(String fixedAreaId, Long id);

    Customer findByTelephone(String telephone);

    @Modifying
    @Query("update Customer set type = 1 where telephone = ?1")
    void active(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

    Customer findByAddress(String sendAddress);
}
