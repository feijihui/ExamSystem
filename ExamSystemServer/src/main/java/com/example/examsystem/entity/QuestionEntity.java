package com.example.examsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 试卷表
 */
public class QuestionEntity {

    // 题目id
    private Integer questionId;

    // 所属题库id
    private Integer bankId;

    // 题目内容
    private String questionText;

    // 题目类型：1（单选）、2（多选）、3（填空）、4（简答）
    private Integer questionType;

    // 题目答案
    private String questionAnswer;

    // 选择题选择项
    private String questionSelectType;

    // 分值
    private Integer questionPointValue;

    // 题目难度
    private Integer questionProblem;

    // 题目分析
    private String questionAnalyze;

    // 审核状态
    private Integer state;

    public QuestionEntity() {
    }

    public QuestionEntity(Integer questionId, Integer bankId, String questionText, Integer questionType, String questionAnswer, String questionSelectType, Integer questionPointValue, Integer questionProblem, String questionAnalyze, Integer state) {
        this.questionId = questionId;
        this.bankId = bankId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.questionAnswer = questionAnswer;
        this.questionSelectType = questionSelectType;
        this.questionPointValue = questionPointValue;
        this.questionProblem = questionProblem;
        this.questionAnalyze = questionAnalyze;
        this.state = state;
    }

    /**
     * 获取
     * @return questionId
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置
     * @param questionId
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取
     * @return bankId
     */
    public Integer getBankId() {
        return bankId;
    }

    /**
     * 设置
     * @param bankId
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     * 获取
     * @return questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * 设置
     * @param questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * 获取
     * @return questionType
     */
    public Integer getQuestionType() {
        return questionType;
    }

    /**
     * 设置
     * @param questionType
     */
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    /**
     * 获取
     * @return questionAnswer
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * 设置
     * @param questionAnswer
     */
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    /**
     * 获取
     * @return questionSelectType
     */
    public String getQuestionSelectType() {
        return questionSelectType;
    }

    /**
     * 设置
     * @param questionSelectType
     */
    public void setQuestionSelectType(String questionSelectType) {
        this.questionSelectType = questionSelectType;
    }

    /**
     * 获取
     * @return questionPointValue
     */
    public Integer getQuestionPointValue() {
        return questionPointValue;
    }

    /**
     * 设置
     * @param questionPointValue
     */
    public void setQuestionPointValue(Integer questionPointValue) {
        this.questionPointValue = questionPointValue;
    }

    /**
     * 获取
     * @return questionProblem
     */
    public Integer getQuestionProblem() {
        return questionProblem;
    }

    /**
     * 设置
     * @param questionProblem
     */
    public void setQuestionProblem(Integer questionProblem) {
        this.questionProblem = questionProblem;
    }

    /**
     * 获取
     * @return questionAnalyze
     */
    public String getQuestionAnalyze() {
        return questionAnalyze;
    }

    /**
     * 设置
     * @param questionAnalyze
     */
    public void setQuestionAnalyze(String questionAnalyze) {
        this.questionAnalyze = questionAnalyze;
    }

    /**
     * 获取
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public String toString() {
        return "QuestionEntity{questionId = " + questionId + ", bankId = " + bankId + ", questionText = " + questionText + ", questionType = " + questionType + ", questionAnswer = " + questionAnswer + ", questionSelectType = " + questionSelectType + ", questionPointValue = " + questionPointValue + ", questionProblem = " + questionProblem + ", questionAnalyze = " + questionAnalyze + ", state = " + state + "}";
    }
}
