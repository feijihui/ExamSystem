package com.example.examsystem.mq.consumer;

import com.example.examsystem.dao.IQuestionDao;
import com.example.examsystem.dto.ManagerReviewQuestionDto;
import com.example.examsystem.po.ManagerReviewQuestion;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;


/**
 * 审核请求信息消费者
 *      管理员审核好老师创建的试卷后，上传审核结果，由该消费者处理审核后的逻辑
 *          1. 审核不通过
 *          2. 审核通过
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${rabbitmq.review-request}" , autoDelete = "true") ,
                exchange = @Exchange(value = "${rabbitmq.exchange}" , type = "direct") ,
                key = "${rabbitmq.review-routing-key}"
        )
)
public class LogReviewRequestConsumer {

        @SuppressWarnings("all")
        @Autowired
        private IQuestionDao questionDao;


        /**
         * 消费方法
         *      消费流程
         *              1. 审核不通过，不修改试卷，就为0，代表审核不通过
         *              2. 审核通过，就将试卷状态修改为正数（ > 0 )
         * @param managerReviewQuestionDto 审核信息
         */
        @RabbitHandler
        public void consumption(ManagerReviewQuestionDto managerReviewQuestionDto){
                // 添加管理员审批记录
                addManagerReview(managerReviewQuestionDto);
        }

        // 保存管理员审批记录
        public void addManagerReview(ManagerReviewQuestionDto managerReviewQuestionDto){

                ManagerReviewQuestion reviewQuestion = new ManagerReviewQuestion();
                reviewQuestion.setManagerName(managerReviewQuestionDto.getManagerName()); // 管理员名称
                reviewQuestion.setManagerReviewQuestion(managerReviewQuestionDto.getBankId()); // 题库id
                if (managerReviewQuestionDto.getManagerReviewInfo().equals("通过")){
                        reviewQuestion.setManagerReviewInfo(1);
                        // 修改题库里边的审核状态，
                        int a = updateQuestionBankStateById(managerReviewQuestionDto.getBankId() , 1);
                        System.out.println("审核通过，修改为1 ， 题库id：" + managerReviewQuestionDto.getBankId());
                }else if (managerReviewQuestionDto.getManagerReviewInfo().equals("不通过")){ // 不通过
                        reviewQuestion.setManagerReviewInfo(-1);
                        // 修改题库里边的审核状态，
                        int a = updateQuestionBankStateById(managerReviewQuestionDto.getBankId() , -1);
                        System.out.println("审核不通过，修改为-1 ， 题库id：" + managerReviewQuestionDto.getBankId());
                }
                reviewQuestion.setManagerNotes(managerReviewQuestionDto.getManagerNotes()); // 审核说明
                reviewQuestion.setManagerReviewType("添加审核");// 审核类型
                reviewQuestion.setOperationTypeNotes("试卷创建");
                System.out.println(reviewQuestion);
                questionDao.addManagerReview(reviewQuestion);
        }

        // 修改题库状态字段
        public int updateQuestionBankStateById(Integer questionBankId , Integer state){
                return questionDao.updateQuestionStateById(questionBankId , state);
        }


}
