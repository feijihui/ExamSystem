package com.example.examsystem.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/4 8:47
 * @Version 1.0
 */
@Data
@Builder
public class QuestionVo {

    private Integer questionId;

    private String questionText; // 试卷题目

    private Integer pointValue; // 题目分数

    private String questionType; //题目类型

    private String typeSelect; // 选择题选项

    private Integer problem; // 题目难度

}