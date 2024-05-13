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
 * 删除考试审核
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${rabbitmq.exam-delete}" , autoDelete = "true") ,
                exchange = @Exchange(value = "${rabbitmq.exchange}" , type = "direct") ,
                key = "${rabbitmq.exam-delete-routing-key}"
        )
)
public class DeleteExamReviewConsumer {


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
