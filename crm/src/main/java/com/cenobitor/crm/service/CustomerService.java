package com.cenobitor.crm.service;

import com.cenobitor.crm.domain.Customer;
import org.springframework.data.jpa.repository.Query;

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
    List<Customer> findAll();

    @GET
    @Path("/findCustomersUnAssociated")
    List<Customer> findCustomersUnAssociated();

    @GET
    @Path("/findCustomersAssociated2FixedArea")
    List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);

    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("customerIds") Long[] customerIds,
                                          @QueryParam("fixedAreaId") String fixedAreaId);

    @POST
    @Path("/save")
    void save(Customer customer);


    @GET
    @Path("/checkCustomer")
    Customer checkCustomer(@QueryParam("telephone") String telephone);

    @PUT
    @Path("/active")
    void active(@QueryParam("telephone") String telephone);


    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone") String telephone,
                   @QueryParam("password") String password);



}
