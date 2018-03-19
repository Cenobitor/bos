package com.cenobitor.crm.service;

import com.cenobitor.crm.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 3:07 PM 18/03/2018
 * @Modified By:
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {
    @GET
    @Path("/findAll")
    public List<Customer> findAll();

    @GET
    @Path("/findCustomersUnAssociated")
    public List<Customer> findCustomersUnAssociated();

    @GET
    @Path("/findCustomersAssociated2FixedArea")
    public List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);

    @PUT
    @Path("/assignCustomers2FixedArea")
    public void assignCustomers2FixedArea(@QueryParam("customerIds") Long[] customerIds,
                                          @QueryParam("fixedAreaId") String fixedAreaId);

}
