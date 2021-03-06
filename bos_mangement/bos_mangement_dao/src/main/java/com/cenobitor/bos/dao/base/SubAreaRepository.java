package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:06 PM 16/03/2018
 * @Modified By:
 */
public interface SubAreaRepository extends JpaRepository<SubArea,Long>{

    List<SubArea> findByFixedAreaIdIsNull();

    List<SubArea> findByFixedAreaId(Long id);

    SubArea findOne(Long subAreaId);



    @Query("select a.province, count(a.province) from SubArea s inner join s.area a group by a.province")
    List<Object[]> getChartData();


//    @Modifying
//    @Query("update SubArea set fixedArea = null where fixedArea = ?1")
//    void unbindByFixedAreaId(Long fixedAreaId);
//
//    @Modifying
//    @Query("update SubArea set fixedAreaId = ?1 where id = ?2")
//    void bindFixedAreaById(Long fixedAreaId, Long subAreaId);
}
