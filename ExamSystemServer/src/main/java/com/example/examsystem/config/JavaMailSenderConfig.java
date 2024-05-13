package com.example.examsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * java发送邮箱到指定的邮箱地址
 */
@Component
public class JavaMailSenderConfig {

    // 自动装配JavaMailSender
    @Autowired
    private JavaMailSender javaMailSender;

    private String mailTitle;
    private String mailSender;
    private String mailRecipient;
    private String mailText;

    public JavaMailSenderConfig() {
    }


    public void sendMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(mailTitle);  // 设置邮件主题
        simpleMailMessage.setFrom(mailSender);  // 设置发件人邮箱
        simpleMailMessage.setTo(mailRecipient); // 设置收件人邮箱
        simpleMailMessage.setSentDate(new Date()); // 设置发送日期
        simpleMailMessage.setText(mailText);  // 设置邮件正文
        System.out.println("邮箱结果：" + toString());
        javaMailSender.send(simpleMailMessage);  // 发送邮件
    }


    /**
     * 获取
     * @return javaMailSender
     */
    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    /**
     * 设置
     * @param javaMailSender
     */
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 获取
     * @return mailTitle
     */
    public String getMailTitle() {
        return mailTitle;
    }

    /**
     * 设置
     * @param mailTitle
     */
    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    /**
     * 获取
     * @return mailSender
     */
    public String getMailSender() {
        return mailSender;
    }

    /**
     * 设置
     * @param mailSender
     */
    public void setMailSender(String mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 获取
     * @return mailRecipient
     */
    public String getMailRecipient() {
        return mailRecipient;
    }

    /**
     * 设置
     * @param mailRecipient
     */
    public void setMailRecipient(String mailRecipient) {
        this.mailRecipient = mailRecipient;
    }

    /**
     * 获取
     * @return mailText
     */
    public String getMailText() {
        return mailText;
    }

    /**
     * 设置
     * @param mailText
     */
    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String toString() {
        return "JavaMailSenderConfig{javaMailSender = " + javaMailSender + ", mailTitle = " + mailTitle + ", mailSender = " + mailSender + ", mailRecipient = " + mailRecipient + ", mailText = " + mailText + "}";
    }
}
