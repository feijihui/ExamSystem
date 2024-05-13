package com.example.examsystem.mq.producer;

import com.example.examsystem.dto.ManagerReviewQuestionDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 审核请求生产者，管理员发送审核结果，由该生产者发送审核结果到审核请求信息队列，由消费者消费
 */
@Component
public class LogReviewRequestProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // 获取审核请求的交换器
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    // 获取审核请求的路由键
    @Value("${rabbitmq.review-routing-key}")
    private String routingKey;

    /**
     * 发送消息给审核信息请求消息队列，给消费者消费
     * @param managerReviewQuestionDto 审核审核信息
     */
    public void sendMqToReview(ManagerReviewQuestionDto managerReviewQuestionDto){
        System.out.println("审核请求生产者");
        amqpTemplate.convertAndSend(exchangeName , routingKey , managerReviewQuestionDto);
    }
}
