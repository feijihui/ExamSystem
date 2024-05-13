package com.example.examsystem.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 考试记录
 */
@Data
@Builder
public class ExamRecordPo {


    private Integer recordId; //考试记录id

    private Integer userId; //考生（用户id）

    private Integer examId; //考试id

    private String userAnswerList; //考试答案列表

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date userExamEndTime; //考生答题完成时间

    private Integer userScore; // 学生成绩

}
