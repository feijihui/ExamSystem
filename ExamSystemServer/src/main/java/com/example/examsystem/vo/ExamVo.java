package com.example.examsystem.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 考试信息（前端展示）
 */
@Data
@Builder
public class ExamVo {


    private Integer examId; // 考试id

    private String questionBankName; //需要考的题库试卷名称

    private String userName; // 考试创建人

    private String examName; // 考试名称

    private String examDescriptive; // 考试描述信息

    private String examMethod; // 考试方式是否需要密码（1需要，0不需要）

    private Integer examPassword; // 考试密码

    private Date examStartTime; // 考试开始时间

    private Date examEndTime; // 考试结束时间

    private Integer examTotal; // 考试总分

    private String examAddress; // 考试地点



}
