package com.example.examsystem.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * exam映射表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam implements Serializable {

    private Integer examId; // 考试id

    private Integer questionBankId; //需要考的题库试卷id

    private Integer userId; // 考试创建人

    private String examName; // 考试名称

    private String examDescriptive; // 考试描述信息

    private Integer examMethod; // 考试方式是否需要密码（1需要，0不需要）

    private Integer examPassword; // 考试密码

    private Date examStartTime; // 考试开始时间

    private Date examEndTime; // 考试结束时间

    private Integer examTotal; // 考试总分

    private Integer examExistState; // 考试是否有效

    private String examAddress; // 考试地点
}
