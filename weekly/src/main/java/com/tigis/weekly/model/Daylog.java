package com.tigis.weekly.model;

import java.util.Date;

public class Daylog
{
	private Integer daylogId;

    private Integer dayNo;

    private String content;

    private String addContent;

    private Integer daylogWeeklogId;

    private Date createTime;

    private Integer delFlag;

    public Integer getDaylogId() {
        return daylogId;
    }

    public void setDaylogId(Integer daylogId) {
        this.daylogId = daylogId;
    }

    public Integer getDayNo() {
        return dayNo;
    }

    public void setDayNo(Integer dayNo) {
        this.dayNo = dayNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddContent() {
        return addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public Integer getDaylogWeeklogId() {
        return daylogWeeklogId;
    }

    public void setDaylogWeeklogId(Integer daylogWeeklogId) {
        this.daylogWeeklogId = daylogWeeklogId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
