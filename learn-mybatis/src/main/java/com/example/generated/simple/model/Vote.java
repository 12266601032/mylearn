package com.example.generated.simple.model;

import javax.annotation.Generated;

public class Vote {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.579+08:00", comments="Source field: vote.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.585+08:00", comments="Source field: vote.create_time")
    private Long createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.faq_id")
    private String faqId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.modify_time")
    private Long modifyTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.type")
    private String type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.user_id")
    private String userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.585+08:00", comments="Source field: vote.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.585+08:00", comments="Source field: vote.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.create_time")
    public Long getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.create_time")
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.faq_id")
    public String getFaqId() {
        return faqId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.faq_id")
    public void setFaqId(String faqId) {
        this.faqId = faqId == null ? null : faqId.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.586+08:00", comments="Source field: vote.modify_time")
    public Long getModifyTime() {
        return modifyTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.modify_time")
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.status")
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.type")
    public String getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.type")
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.user_id")
    public String getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.587+08:00", comments="Source field: vote.user_id")
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}