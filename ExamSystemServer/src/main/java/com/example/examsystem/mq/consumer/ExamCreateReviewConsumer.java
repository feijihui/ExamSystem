package com.example.examsystem.mq.consumer;

import com.example.examsystem.po.Exam;
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
 * @Class_Info ExamSystemServer 创建考试申请
 * @Author 费基辉
 * @Date 2024/5/2 5:22
 * @Version 1.0
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${rabbitmq.exam-create}" , autoDelete = "true") ,
                exchange = @Exchange(value = "${rabbitmq.exchange}" , type = "direct") ,
                key = "${rabbitmq.exam-create-routing-key}"
        )
)
public class ExamCreateReviewConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送邮件叫管理员审核考试创建申请
     */
    @RabbitHandler
    public void toManagerMail(Exam exam){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("您有一条删除请求");
            helper.setFrom("2146321711@qq.com");
            helper.setTo("2543334628@qq.com");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("msg" , exam);
            String review = templateEngine.process("managerReview.html" , context);
            helper.setText(review , true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
