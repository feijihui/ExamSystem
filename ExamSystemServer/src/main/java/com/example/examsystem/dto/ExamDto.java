package com.example.examsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 考试信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {


    @NotBlank(message = "考试创建人不能为空")
    @ApiModelProperty("考试创建人")
    private String userName;

    @NotBlank(message = "考试的题库不能为空")
    @ApiModelProperty("需要考的题库试题")
    private String questionBankName;

    @NotBlank(message = "考试名称不能为空")
    @ApiModelProperty("考试名称")
    private String examName;

    @NotBlank(message = "考试描述信息不能为空")
    @ApiModelProperty("考试描述信息")
    private String examDescriptive;

    @NotBlank(message = "考试是否需要密码不能为空")
    @ApiModelProperty("考试是否需要密码(需要/不需要)")
    private String examMethod;

    @NotBlank(message = "考试密码不能为空")
    @ApiModelProperty("考试密码（只能为数字）")
    private Integer examPassword;

    @NotBlank(message = "考试开始时间不能为空")
    @ApiModelProperty("考试开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examStartTime;

    @NotBlank(message = "考试结束时间不能为空")
    @ApiModelProperty("考试结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date examEndTime;

    @NotBlank(message = "考试地点不能为空")
    @ApiModelProperty("考试地点")
    private String examAddress;
}
