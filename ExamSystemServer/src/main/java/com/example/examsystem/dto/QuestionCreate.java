package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreate {

    // 题库名
    @NotBlank(message = "题库名不能为空")
    private String bankName;

    // 题目内容
    @NotBlank(message = "题目内容不能为空")
    private String questionText;

    // 题目类型
    @NotBlank(message = "题目类型不能为空")
    private String questionType;

    // 题目答案
    @NotBlank(message = "题目答案不能为空")
    private String questionAnswer;

    // 选择题选择项
    //@NotBlank
    private String questionSelectType;

    // 题目分值
    @NotNull(message = "题目分值不能为空")
    private Integer questionPointValue;

    // 题目难度
    @NotNull(message = "题目难度不能为空")
    private Integer questionProblem;

    // 题目解析
    @NotBlank(message = "题目解析不能为空")
    private String questionAnalyze;
}
