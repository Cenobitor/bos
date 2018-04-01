package com.cenobitor.bos.domain.take_delivery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 宣传任务
 */
@Entity
@Table(name = "T_PROMOTION")
public class Promotion {

    // Fields

    @Id
    @GeneratedValue()
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE", length = 510)
    private String title; // 标题
    @Column(name = "TITLE_IMG", length = 510)
    private String titleImg;// 封面
    @Column(name = "ACTIVE_SCOPE", length = 510)
    private String activeScope; // 活动范围
    @Column(name = "START_DATE")
    private Date startDate; // 促销开始时间
    @Column(name = "END_DATE")
    private Date endDate; // 促销结束时间
    @Column(name = "STATUS")
    private String status; // 状态 1：有效 2: 过期
    @Column(name = "DESCRIPTION", length = 50000)
    private String description; // 内容

    public Promotion() {}

    public Promotion(String title, String titleImg, String activeScope,
            Date startDate, Date endDate, String status, String description) {
        this.title = title;
        this.titleImg = titleImg;
        this.activeScope = activeScope;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTitleImg() {
        return this.titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }


    public String getActiveScope() {
        return this.activeScope;
    }

    public void setActiveScope(String activeScope) {
        this.activeScope = activeScope;
    }


    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
