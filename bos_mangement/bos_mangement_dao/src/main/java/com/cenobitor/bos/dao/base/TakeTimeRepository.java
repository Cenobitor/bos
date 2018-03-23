package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:07 PM 19/03/2018
 * @Modified By:
 */
@Repository
public interface TakeTimeRepository extends JpaRepository<TakeTime,Long>{


    @Query("from TakeTime where status = 1 and  normalWorkTime <= ?1 and normalDutyTime >= ?1")
    TakeTime findIdWorkedDay(int hour);


    @Query("from TakeTime where status = 1 and  sunWorkTime <= ?1 and sunDutyTime >= ?1")
    TakeTime findIdSun(int hour);

    @Query("from TakeTime where status = 1 and  satWorkTime <= ?1 and satDutyTime >= ?1")
    TakeTime findIdSat(int hour);

}
