package com.example.examsystem.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 试卷创建生产者，老师提交试卷后会由该生产者发送到试卷创建队列由消费者消费
 */
@Component
public class QuestionCreateProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // 获取试卷创建的交换器名称
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    // 获取试卷创建的路由键
    @Value("${rabbitmq.question-create-routing-key}")
    private String routingKey;

    /**
     * 发送消息到队列，给消费者处理
     *      将要创建额试题通过生产者发送到考试创建队列，由消费者消费
     */
    public void sendMqToQuestionCreateQueue(String questionBankName){
        System.out.println("考试创建生产者");
        amqpTemplate.convertAndSend(exchangeName , routingKey , questionBankName);
    }


}
