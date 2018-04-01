package com.cenobitor.bos.service.take_delivery;

import com.cenobitor.bos.domain.take_delivery.PageBean;
import com.cenobitor.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.awt.*;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 4:19 PM 31/03/2018
 * @Modified By:
 */
public interface PromotionService {
    void save(Promotion promotion);

    Page<Promotion> pageQuery(Pageable pageable);

    @GET
    @Path("/pageQuery")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    PageBean<Promotion> pageQuery4Fore(@QueryParam("page") int page, @QueryParam("size") int size);
}
