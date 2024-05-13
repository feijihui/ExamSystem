package com.example.examsystem.po;

import lombok.Data;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/4 8:47
 * @Version 1.0
 */
@Data
public class QuestionPo {

    private Integer questionId;

    private String questionText; // 试卷题目

    private Integer questionPointValue; // 题目分数

    private Integer questionType; //题目类型

    private String questionSelectType; // 选择题选项

    private Integer questionProblem; // 题目难度

    @Override
    public String toString() {
        return "QuestionPo{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", questionPointValue=" + questionPointValue +
                ", questionType=" + questionType +
                ", questionSelectType='" + questionSelectType + '\'' +
                ", questionProblem=" + questionProblem +
                '}';
    }
}