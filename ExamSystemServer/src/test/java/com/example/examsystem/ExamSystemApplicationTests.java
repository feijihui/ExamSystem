package com.example.examsystem;

import com.example.examsystem.dao.IExamManagementDao;
import com.example.examsystem.dao.IQuestionDao;
import com.example.examsystem.dao.StudentExamDao;
import com.example.examsystem.entity.QuestionBankEntity;
import com.example.examsystem.mq.producer.LogReviewRequestProducer;
import com.example.examsystem.mq.producer.QuestionCreateProducer;
import com.example.examsystem.po.QuestionPo;
import com.example.examsystem.server.impl.QuestionService;
import com.example.examsystem.utils.ObjectToJson;
import com.example.examsystem.vo.QuestionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ExamSystemApplicationTests {

    @SuppressWarnings("all")
    @Autowired
    private IQuestionDao questionDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;


    @Autowired
    LogReviewRequestProducer logReviewRequestProducer;

    @Autowired
    QuestionCreateProducer questionCreateProducer;


    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StudentExamDao studentExamDao;


    @Test
    void contextLoads() {
        List<QuestionPo> questionByBankId = studentExamDao.findQuestionByBankId(33);
        System.out.println(questionByBankId);


    }

}
