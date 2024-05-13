package com.example.examsystem.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/1 12:54
 * @Version 1.0
 */
@Component
public class DeleteQuestionBankProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // 获取删除队列的交换器名称
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    // 获取删除队列的路由键
    @Value("${rabbitmq.delete-question-bank-routing-key}")
    private String routingKey;

    public void send(String questionBank){
        amqpTemplate.convertAndSend(exchangeName , routingKey , questionBank);
    }

}
