package com.example.examsystem.server;

import com.example.examsystem.dto.ExamDto;
import com.example.examsystem.dto.ManagerReviewExamDto;
import com.example.examsystem.vo.ExamVo;

import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/2 1:45
 * @Version 1.0
 */
public interface IExamManagementService {

    // 添加考试
    int addExam(ExamDto examDto);

    void examReview(ManagerReviewExamDto managerReviewExamDto);

    // 获取所有考试信息
    String findAll(Integer queryExam);

    // 删除考试
    void deleteExam(Integer deleteExamId);
}
