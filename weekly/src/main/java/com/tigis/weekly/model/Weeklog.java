package com.tigis.weekly.model;

import java.util.Date;

public class Weeklog
{
	private Integer weeklogId;

    private Integer weeklogUserId;

    private Integer myScore;

    private Integer leadScore;

    private Date createtime;

    private String mainContent;

    private String planContent;

    private Integer weeklogGroupId;

    private String remark;

    private Integer delFlag;

    public Integer getWeeklogId() {
        return weeklogId;
    }

    public void setWeeklogId(Integer weeklogId) {
        this.weeklogId = weeklogId;
    }

    public Integer getWeeklogUserId() {
        return weeklogUserId;
    }

    public void setWeeklogUserId(Integer weeklogUserId) {
        this.weeklogUserId = weeklogUserId;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }

    public Integer getLeadScore() {
        return leadScore;
    }

    public void setLeadScore(Integer leadScore) {
        this.leadScore = leadScore;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public Integer getWeeklogGroupId() {
        return weeklogGroupId;
    }

    public void setWeeklogGroupId(Integer weeklogGroupId) {
        this.weeklogGroupId = weeklogGroupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
