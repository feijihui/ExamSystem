package com.example.examsystem.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 4:12
 * @Version 1.0
 */
@Data
@Builder
public class ExamRecordVo {

    private Integer recordId; //考试记录id

    private String userName; //考生（用户name）

    private String examName; //考试name

    private String userAnswerList; //考试答案列表

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date userExamEndTime; //考生答题完成时间

    private Integer userScore; // 学生成绩
}
