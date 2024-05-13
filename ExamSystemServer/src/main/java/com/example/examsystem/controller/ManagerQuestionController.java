package com.example.examsystem.controller;

import com.example.examsystem.dto.ManagerReviewQuestionDto;
import com.example.examsystem.mq.producer.LogReviewRequestProducer;
import com.example.examsystem.server.impl.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员试卷管理
 */
@RestController
@Api(value = "管理员管理试卷" , tags = "管理员试卷管理")
@RequestMapping("/reviewQuestion")
public class ManagerQuestionController {

    @Autowired
    private LogReviewRequestProducer logReviewRequestProducer; // 审核日志

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "是否通过审核" , notes = "")
    @PostMapping("/upCreateReview")
    public ResponseEntity<String> upCreateReviewLog(@Validated ManagerReviewQuestionDto managerReviewQuestionDto){
        System.out.println(managerReviewQuestionDto);
        int questionBankId = questionService.findQuestionBankId(managerReviewQuestionDto.getManagerReviewQuestion());
        if ((questionBankId == -1) || // 如果为-1后面就不用判断了
                (!managerReviewQuestionDto.getManagerReviewInfo().equals("通过")) && // 如果不等于通过
                (!managerReviewQuestionDto.getManagerReviewInfo().equals("不通过"))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("您输入的题库或者审核意见不合规，请您检查后重新输入");
        }
        managerReviewQuestionDto.setBankId(questionBankId);
        logReviewRequestProducer.sendMqToReview(managerReviewQuestionDto);
        return ResponseEntity.ok("提交成功");
    }


    @ApiOperation(value = "是否通过审核" , notes = "")
    @PostMapping("/upDeleteReview")
    public ResponseEntity<String> upDeleteReviewLog(@Validated ManagerReviewQuestionDto managerReviewQuestionDto){
        int questionBankId = questionService.findQuestionBankId(managerReviewQuestionDto.getManagerReviewQuestion());
        if ((questionBankId == -1) || // 如果为-1后面就不用判断了
                (!managerReviewQuestionDto.getManagerReviewInfo().equals("通过")) && // 如果不等于通过
                        (!managerReviewQuestionDto.getManagerReviewInfo().equals("不通过"))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("您输入的题库或者审核意见不合规，请您检查后重新输入");
        }
        managerReviewQuestionDto.setBankId(questionBankId);
        // 保存审核信息
        questionService.addManagerDeleteReview(managerReviewQuestionDto);
        questionService.deleteByQuestionBankId(managerReviewQuestionDto.getBankId());
        return ResponseEntity.ok("提交成功");
    }
}
