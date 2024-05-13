package com.example.examsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/4 10:09
 * @Version 1.0
 */
@Data
public class StudentUpAnswerDto {

    @NotBlank(message = "学生姓名不能为空")
    @ApiModelProperty("学生姓名")
    private String StudentName;  // 学生姓名

    @NotBlank(message = " 试题id，比对结果不能为空")
    @ApiModelProperty(" 试题id，比对结果")
    private Integer questionId; // 试题id，比对结果

    @NotBlank(message = "学生答案不能为空")
    @ApiModelProperty("学生答案")
    private String studentAnswer; // 学生答案
}
