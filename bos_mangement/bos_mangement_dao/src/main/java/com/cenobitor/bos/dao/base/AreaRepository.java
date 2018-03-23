package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 5:32 PM 15/03/2018
 * @Modified By:
 */
@Repository
public interface AreaRepository extends JpaRepository<Area,Long>{

    @Modifying
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1")
    List<Area> findQ(String q);

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
