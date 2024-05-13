package com.example.examsystem.server;

import com.example.examsystem.dto.QuestionCreate;
import org.springframework.http.ResponseEntity;


public interface IQuestionService {

    // 创建题库以及对应的试题
    int bankAndQuestionCreate(QuestionCreate questionCreate);

    // 查询所有的题库记录以及某个题库对应的有多少道试题
    String findAllToQuestionBank();

    // 根据题库名查询试题
    String findByBankId(String questionBankName);

    // 通过试题id删除试题
    void deleteByQuestionId(int questionId , String questionBankName);

    // 通过id删除题库
    String deleteByQuestionBankId(int questionBankId);

    //
    void deleteToManagerToReview(int questionBankId);


}
