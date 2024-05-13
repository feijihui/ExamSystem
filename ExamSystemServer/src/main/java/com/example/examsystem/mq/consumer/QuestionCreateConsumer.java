package com.example.examsystem.mq.consumer;

import com.example.examsystem.mq.producer.LogReviewRequestProducer;
import com.example.examsystem.server.impl.QuestionService;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 试卷创建消费者
 *      老师创建试卷上传后后，由该消费者处理上传试卷后的逻辑
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${rabbitmq.question-create}" , autoDelete = "true") ,
                exchange = @Exchange(value = "${rabbitmq.exchange}" , type = "direct") ,
                key = "${rabbitmq.question-create-routing-key}"
        )
)
public class QuestionCreateConsumer {

    @Autowired
    private LogReviewRequestProducer logReviewRequestProducer; // 审核通知生产者


    @Autowired
    private QuestionService questionService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 消费方法
     *      具体流程
     *          1. 保存试卷到数据库，并设置状态为负数（待审核）
     *          2. 将要审核的数据（试卷）通过邮箱通知管理员进行审核
     */
    @RabbitHandler
    public void consumption(String questionBankName){
        System.out.println("消费者 --- > 试卷创建消费者：" + questionBankName);
        sendMail(questionBankName); // 发送审核通知给管理员
    }


    public void sendMail(String questionBankName){

//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setSubject("请您审核" + questionBankName + "库。");
//        simpleMailMessage.setFrom("2146321711@qq.com");
//        simpleMailMessage.setTo("2543334628@qq.com");
//        simpleMailMessage.setSentDate(new Date());
//        Context context = new Context();
//        String review = templateEngine.process("managerReview.html" , context);
////        simpleMailMessage.setText("有位老师提交了试卷，请您审核，审核地址\n" + "http://www.baidu.com/" +
////                "具体的试卷信息\n" + questionService.toMysqlQuery(questionBankName));
//        simpleMailMessage.setText(review , true);
//        javaMailSender.send(simpleMailMessage);


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("请您审核" + questionBankName + "库。");
            helper.setFrom("2146321711@qq.com");
            helper.setTo("2543334628@qq.com");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("msg" , questionService.toMysqlQuery(questionBankName));
            String review = templateEngine.process("managerReview.html" , context);
//        simpleMailMessage.setText("有位老师提交了试卷，请您审核，审核地址\n" + "http://www.baidu.com/" +
//                "具体的试卷信息\n" + questionService.toMysqlQuery(questionBankName));
            helper.setText(review , true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
