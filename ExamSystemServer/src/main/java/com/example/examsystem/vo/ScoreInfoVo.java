package com.example.examsystem.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 5:17
 * @Version 1.0
 */
@Data
@Builder
public class ScoreInfoVo {

    private Integer scoreId;
    private String userName;
    private String examName;
    private Date examTime;
    private Integer examScore;
}
