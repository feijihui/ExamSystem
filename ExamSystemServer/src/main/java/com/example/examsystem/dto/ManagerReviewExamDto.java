package com.example.examsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/2 6:03
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerReviewExamDto {


    @NotBlank(message = "管理员名称不能为空")
    @ApiModelProperty("管理员名称")
    private String managerName; // 管理员名称

    @NotBlank(message = "考试名称不能为空")
    @ApiModelProperty("考试名称")
    private String examName; // 考试名称

    @NotBlank(message = "审核意见（审核通过还是不通过）不能为空")
    @ApiModelProperty("审核意见（审核通过还是不通过）")
    private String managerReviewInfo; // 审核意见（审核通过还是不通过）

    @NotBlank(message = "审核说明不能为空")
    @ApiModelProperty("审核说明")
    private String managerNotes; // 审核说明

    @NotBlank(message = "审核类型不能为空")
    @ApiModelProperty("审核类型")
    private String managerReviewType; // 审核类型

    @NotBlank(message = "不能为空")
    @ApiModelProperty("审核内容，比如试卷创建，试卷删除，考试创建，考试删除等")
    private String operationTypeNotes; // 审核内容，是审核哪一模块的，比如试卷创建，试卷删除，考试创建，考试删除等
}
