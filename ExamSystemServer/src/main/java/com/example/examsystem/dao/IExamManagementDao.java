package com.example.examsystem.dao;

import com.example.examsystem.po.Exam;
import com.example.examsystem.po.ManagerReviewQuestion;
import com.example.examsystem.vo.ExamVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/2 1:57
 * @Version 1.0
 */
public interface IExamManagementDao {
    // 添加考试
    int addExam(@Param("exam") Exam exam);

    // 通过题库名称获取题库id
    int findQuestionBankIdByName(@Param("questionBankName") String questionBankName);

    // 通过用户姓名获取用户id
    int findUserIdByName(@Param("userName") String userName);

    // 计算考试总分
    List<Integer> findQuestionPointValue(@Param("questionBankId") int questionBankId);

    // 管理员通过审核修改考试状态
    void updateExamExistState();

    // 通过考试名称获取考试id
    int findExamIdByName(String examName);

    // 添加管理员审核信息
    void addManagerReviewInfo(@Param("managerReviewQuestion") ManagerReviewQuestion managerReviewQuestion);

    // 获取所有的考试信息（只获取待考试信息）
    List<Exam> examsExist();

    // 获取所有的考试信息（只获取完成考试的考试信息）
    List<Exam> examsExisted();

    // 通过题库id获取题库名称
    String findQuestionBankNameById(int bankId);

    // 通过用户id获取用户名称
    String findUserNameById(int userId);

    // 查找考试
    Exam findExamById(Integer deleteExamId);
}
