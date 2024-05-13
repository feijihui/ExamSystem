package com.example.examsystem.server;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 5:07
 * @Version 1.0
 */
public interface IScoreManagementService {
    String getScoreInfo(Integer minScore , Integer maxScore);
}

