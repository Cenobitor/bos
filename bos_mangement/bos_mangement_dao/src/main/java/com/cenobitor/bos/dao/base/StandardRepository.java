package com.cenobitor.bos.dao.base;

import com.cenobitor.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:20 PM 12/03/2018
 * @Modified By:
 */
//第一个泛型:实体类型
//第二个泛型:主键类型
@Repository
public interface StandardRepository extends JpaRepository<Standard,Long>{

}
