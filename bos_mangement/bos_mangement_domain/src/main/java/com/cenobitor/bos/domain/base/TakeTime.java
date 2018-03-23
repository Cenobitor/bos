package com.cenobitor.bos.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description:收派时间管理
 */
@Entity
@Table(name = "T_TAKE_TIME")
public class TakeTime {
    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    private Long id; // 主键
    @Column(name = "C_NAME")
    private String name; // 收派时间名称
    @Column(name = "C_NORMAL_WORK_TIME")
    private Integer normalWorkTime; // 平常上班时间
    @Column(name = "C_NORMAL_DUTY_TIME")
    private Integer normalDutyTime; // 平常下班时间
    @Column(name = "C_SAT_WORK_TIME")
    private Integer satWorkTime; // 周六上班时间
    @Column(name = "C_SAT_DUTY_TIME")
    private Integer satDutyTime; // 周六下班时间
    @Column(name = "C_SUN_WORK_TIME")
    private Integer sunWorkTime; // 周日上班时间
    @Column(name = "C_SUN_DUTY_TIME")
    private Integer sunDutyTime; // 周日下班时间
    @Column(name = "C_STATUS")
    private String status; // 状态
    @Column(name = "C_COMPANY")
    private String company; // 所属公司

    @Column(name = "C_OPERATING_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operatingTime;// 操作时间
    @Column(name = "C_OPERATOR")
    private String operator; // 操作员
    @Column(name = "C_OPERATING_COMPANY")
    private String operatingCompany; // 操作单位

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNormalWorkTime() {
        return normalWorkTime;
    }

    public void setNormalWorkTime(Integer normalWorkTime) {
        this.normalWorkTime = normalWorkTime;
    }

    public Integer getNormalDutyTime() {
        return normalDutyTime;
    }

    public void setNormalDutyTime(Integer normalDutyTime) {
        this.normalDutyTime = normalDutyTime;
    }

    public Integer getSatWorkTime() {
        return satWorkTime;
    }

    public void setSatWorkTime(Integer satWorkTime) {
        this.satWorkTime = satWorkTime;
    }

    public Integer getSatDutyTime() {
        return satDutyTime;
    }

    public void setSatDutyTime(Integer satDutyTime) {
        this.satDutyTime = satDutyTime;
    }

    public Integer getSunWorkTime() {
        return sunWorkTime;
    }

    public void setSunWorkTime(Integer sunWorkTime) {
        this.sunWorkTime = sunWorkTime;
    }

    public Integer getSunDutyTime() {
        return sunDutyTime;
    }

    public void setSunDutyTime(Integer sunDutyTime) {
        this.sunDutyTime = sunDutyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatingCompany() {
        return operatingCompany;
    }

    public void setOperatingCompany(String operatingCompany) {
        this.operatingCompany = operatingCompany;
    }
}
