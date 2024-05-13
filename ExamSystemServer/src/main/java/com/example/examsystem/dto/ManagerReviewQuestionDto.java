package com.example.examsystem.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 管理员审核试卷信息
 */
public class ManagerReviewQuestionDto implements Serializable {


    private Integer managerId; // id

    @NotBlank(message = "管理员名称不能为空")
    private String managerName; // 管理员名称

    @NotBlank(message = "审核的试卷名称不能为空")
    private String managerReviewQuestion; // 管理员审核的试卷（题库）

    private String managerReviewRime; // 审核时间

    @NotBlank(message = "审核意见不能为空")
    private String managerReviewInfo; // 审核意见（通过/不通过）

    private String managerNotes; // 审核意见说明



    private Integer bankId; // 用于管理员上传审核信息后，记录查找的题库id值，管理员审核表需要保存审核的题库id，用于消费者消费方法的时候不用重新获取题库id








    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public ManagerReviewQuestionDto() {
    }

    public ManagerReviewQuestionDto(Integer managerId, String managerName, String managerReviewQuestion, String managerReviewRime, String managerReviewInfo, String managerNotes) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerReviewQuestion = managerReviewQuestion;
        this.managerReviewRime = managerReviewRime;
        this.managerReviewInfo = managerReviewInfo;
        this.managerNotes = managerNotes;
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
     * @return managerReviewQuestion
     */
    public String getManagerReviewQuestion() {
        return managerReviewQuestion;
    }

    /**
     * 设置
     * @param managerReviewQuestion
     */
    public void setManagerReviewQuestion(String managerReviewQuestion) {
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
    public String getManagerReviewInfo() {
        return managerReviewInfo;
    }

    /**
     * 设置
     * @param managerReviewInfo
     */
    public void setManagerReviewInfo(String managerReviewInfo) {
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

    public String toString() {
        return "ManagerReviewQuestionDto{managerId = " + managerId + ", managerName = " + managerName + ", managerReviewQuestion = " + managerReviewQuestion + ", managerReviewRime = " + managerReviewRime + ", managerReviewInfo = " + managerReviewInfo + ", managerNotes = " + managerNotes + "}";
    }
}
