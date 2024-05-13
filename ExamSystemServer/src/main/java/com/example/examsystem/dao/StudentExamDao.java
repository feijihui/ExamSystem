package com.example.examsystem.dao;

import com.example.examsystem.po.ExamRecordPo;
import com.example.examsystem.po.QuestionPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/4 8:55
 * @Version 1.0
 */
public interface StudentExamDao {

    Integer findQuestionBankIdByBankName(@Param("questionBankName") String questionBankName); //通过题库名称获取题库id

    Integer findQuestionBankIdByExamName(@Param("examName") String examName); // 通过考试名称获取题库id

    List<QuestionPo> findQuestionByBankId(@Param("questionBankId") Integer questionBankId); // 通过题库id获取试题

    String findQuestionAnswerById(@Param("questionId") Integer questionId); // 获取考题正确答案

    int findQuestionPointValueById(@Param("questionId") Integer questionId); // 获取考题分数

    int findExamIdById(int questionBankId); // 获取考试id

    int findUserIdByName(String userName); // 获取用户id

    int findQuestionBankIdById(int questionId);

    void addExamRecord(@Param("examRecordPo") ExamRecordPo examRecordPo);

    List<ExamRecordPo> findExamRecordByUserid(int userId);

    String findUserNameById(int userId);

    String findExamNameById(int examId);
}
