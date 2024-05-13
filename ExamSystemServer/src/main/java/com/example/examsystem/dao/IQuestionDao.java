package com.example.examsystem.dao;

import com.example.examsystem.entity.QuestionBankEntity;
import com.example.examsystem.entity.QuestionEntity;
import com.example.examsystem.po.ManagerReviewQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class_Info ExamSystem 类描述
 * @Author 费基辉
 * @Date 2024/4/26 16:46
 * @Version 1.0
 */
public interface IQuestionDao {

    // 将题库添加到数据库
    int insertBank(@Param("bankName") String questBankName);

    // 将试题添加到数据库
    int insertQuestion(@Param("question") QuestionEntity question);

    // 删除试题
    int deleteQuestionById(@Param("questionId") int questionId);

    // 查询题库所有记录
    List<QuestionBankEntity> findQuestionBank();

    // 查询试卷
    List<QuestionEntity> findAllByBankId(@Param("questionBankId") int questionBankId);

    // 查找题库名是否存在
    QuestionBankEntity questBankIsExist(@Param("questionBank") String questionBank);

    // 通过题库id查询题库信息
    QuestionBankEntity findQuestionBankById(@Param("questionBankId") int questionBankId);

    // 通过题库id删除所对应的题库信息
    int deleteQuestionBankById(@Param("questionBankId") int questionBankId);

    // 删除题库下的所有试题
    int deleteQuestionByBankId(@Param("questionBankId") int questionBankId);

    // 保存管理员审核信息
    void addManagerReview(@Param("reviewQuestion") ManagerReviewQuestion reviewQuestion);

    // 通过题库id修改题库状态
    int updateQuestionStateById(@Param("questionBankId") int questionBankId , @Param("state") int state);

}
