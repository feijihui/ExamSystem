package com.example.examsystem.po;

import lombok.Builder;

/**
 * manager_review_question表
 */
public class ManagerReviewQuestion {


    private Integer managerId; // id

    private String managerName; // 管理员名称

    private Integer managerReviewExam; // 管理员审核的考试id，0代表为空该字段无效

    private Integer managerReviewQuestion; // 管理员审核的试卷（题库）id, 0代表为空该字段无效

    private String managerReviewRime; // 审核时间

    private Integer managerReviewInfo; // 审核意见（审核通过还是不通过）

    private String managerNotes; // 审核说明

    private String managerReviewType; // 审核类型

    private String operationTypeNotes; // 审核内容，是审核哪一模块的，比如试卷创建，试卷删除，考试创建，考试删除等

    public ManagerReviewQuestion() {
    }

    public ManagerReviewQuestion(Integer managerId, String managerName, Integer managerReviewExam, Integer managerReviewQuestion, String managerReviewRime, Integer managerReviewInfo, String managerNotes, String managerReviewType, String operationTypeNotes) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerReviewExam = managerReviewExam;
        this.managerReviewQuestion = managerReviewQuestion;
        this.managerReviewRime = managerReviewRime;
        this.managerReviewInfo = managerReviewInfo;
        this.managerNotes = managerNotes;
        this.managerReviewType = managerReviewType;
        this.operationTypeNotes = operationTypeNotes;
    }

    /**
     * 获取
     * @return managerId
     */
    public Integer getManagerId() {
        return managerId;
    }

    /**
     * 设置
     * @param managerId
     */
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    /**
     * 获取
     * @return managerName
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * 设置
     * @param managerName
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    /**
     * 获取
     * @return managerReviewExam
     */
    public Integer getManagerReviewExam() {
        return managerReviewExam;
    }

    /**
     * 设置
     * @param managerReviewExam
     */
    public void setManagerReviewExam(Integer managerReviewExam) {
        this.managerReviewExam = managerReviewExam;
    }

    /**
     * 获取
     * @return managerReviewQuestion
     */
    public Integer getManagerReviewQuestion() {
        return managerReviewQuestion;
    }

    /**
     * 设置
     * @param managerReviewQuestion
     */
    public void setManagerReviewQuestion(Integer managerReviewQuestion) {
        this.managerReviewQuestion = managerReviewQuestion;
    }

    /**
     * 获取
     * @return managerReviewRime
     */
    public String getManagerReviewRime() {
        return managerReviewRime;
    }

    /**
     * 设置
     * @param managerReviewRime
     */
    public void setManagerReviewRime(String managerReviewRime) {
        this.managerReviewRime = managerReviewRime;
    }

    /**
     * 获取
     * @return managerReviewInfo
     */
    public Integer getManagerReviewInfo() {
        return managerReviewInfo;
    }

    /**
     * 设置
     * @param managerReviewInfo
     */
    public void setManagerReviewInfo(Integer managerReviewInfo) {
        this.managerReviewInfo = managerReviewInfo;
    }

    /**
     * 获取
     * @return managerNotes
     */
    public String getManagerNotes() {
        return managerNotes;
    }

    /**
     * 设置
     * @param managerNotes
     */
    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    /**
     * 获取
     * @return managerReviewType
     */
    public String getManagerReviewType() {
        return managerReviewType;
    }

    /**
     * 设置
     * @param managerReviewType
     */
    public void setManagerReviewType(String managerReviewType) {
        this.managerReviewType = managerReviewType;
    }

    /**
     * 获取
     * @return operationTypeNotes
     */
    public String getOperationTypeNotes() {
        return operationTypeNotes;
    }

    /**
     * 设置
     * @param operationTypeNotes
     */
    public void setOperationTypeNotes(String operationTypeNotes) {
        this.operationTypeNotes = operationTypeNotes;
    }

    public String toString() {
        return "ManagerReviewQuestion{managerId = " + managerId + ", managerName = " + managerName + ", managerReviewExam = " + managerReviewExam + ", managerReviewQuestion = " + managerReviewQuestion + ", managerReviewRime = " + managerReviewRime + ", managerReviewInfo = " + managerReviewInfo + ", managerNotes = " + managerNotes + ", managerReviewType = " + managerReviewType + ", operationTypeNotes = " + operationTypeNotes + "}";
    }
}
