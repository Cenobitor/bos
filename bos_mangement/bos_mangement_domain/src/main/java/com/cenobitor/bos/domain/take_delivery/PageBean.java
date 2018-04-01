package com.cenobitor.bos.domain.take_delivery;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 8:31 PM 31/03/2018
 * @Modified By:
 */

@XmlRootElement(name = "pageBean")
@XmlSeeAlso(Promotion.class)
public class PageBean<T> {
    private List<T> rows;
    private long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
