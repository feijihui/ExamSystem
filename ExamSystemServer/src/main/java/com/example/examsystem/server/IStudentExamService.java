package com.example.examsystem.server;

import com.example.examsystem.dto.StudentUpAnswerDto;
import com.example.examsystem.vo.QuestionVo;

import java.util.List;

/**
 * 学生考试服务
 */
public interface IStudentExamService {
    String getQuestion(String examName); // 获取试卷

    void upAnswer(List<StudentUpAnswerDto> studentUpAnswerDto); // 学生上传答案

    String getExamInfo(String studentName);
}
