package com.cenobitor.bos.service.take_delivery;

import com.cenobitor.bos.domain.take_delivery.Order;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 12:14 PM 23/03/2018
 * @Modified By:
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OrderService {

    @POST
    @Path("/save")
    void save(Order order);
}
