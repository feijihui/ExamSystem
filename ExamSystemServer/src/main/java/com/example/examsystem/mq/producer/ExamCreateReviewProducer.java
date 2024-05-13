package com.example.examsystem.mq.producer;

import com.example.examsystem.po.Exam;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/2 5:21
 * @Version 1.0
 */
@Component
public class ExamCreateReviewProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.exam-create-routing-key}")
    private String routingKey;

    public void sender(Exam exam){
        amqpTemplate.convertAndSend(exchangeName , routingKey , exam);
    }

}
