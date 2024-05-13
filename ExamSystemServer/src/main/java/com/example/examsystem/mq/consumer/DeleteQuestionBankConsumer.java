package com.example.examsystem.mq.consumer;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/1 12:55
 * @Version 1.0
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${rabbitmq.delete-question-bank}" , autoDelete = "true") ,
                exchange = @Exchange(value = "${rabbitmq.exchange}" , type = "direct") ,
                key = "${rabbitmq.delete-question-bank-routing-key}"
        )
)
public class DeleteQuestionBankConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitHandler
    public void toManagerMail(String questionBank){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("您有一条删除请求");
            helper.setFrom("2146321711@qq.com");
            helper.setTo("2543334628@qq.com");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("msg" , questionBank);
            String review = templateEngine.process("managerReview.html" , context);
            helper.setText(review , true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
