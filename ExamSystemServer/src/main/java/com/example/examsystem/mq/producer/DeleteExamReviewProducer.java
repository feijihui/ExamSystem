package com.example.examsystem.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 删除考试审核
 */
@Component
public class DeleteExamReviewProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // 获取删除队列的交换器名称
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    // 获取删除队列的路由键
    @Value("${rabbitmq.exam-delete-routing-key}")
    private String routingKey;

    public void send(String questionBank){
        amqpTemplate.convertAndSend(exchangeName , routingKey , questionBank);
    }
}
