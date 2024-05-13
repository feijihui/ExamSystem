package com.example.examsystem.controller;

import com.example.examsystem.dto.ManagerReviewExamDto;
import com.example.examsystem.dto.ManagerReviewQuestionDto;
import com.example.examsystem.mq.producer.LogReviewRequestProducer;
import com.example.examsystem.server.IExamManagementService;
import com.example.examsystem.server.impl.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员试卷管理
 */
@RestController
@Api(value = "管理员考试试卷" , tags = "管理员考试管理")
@RequestMapping("/reviewExam")
public class ManagerReviewExamController {


    @Autowired
    private IExamManagementService examManagementService;

    @ApiOperation(value = "是否通过审核" , notes = "")
    @PostMapping("/upCreateReview")
    public ResponseEntity<String> upCreateReviewLog(@Validated ManagerReviewExamDto managerReviewExamDto){

        if ((!managerReviewExamDto.getManagerReviewInfo().equals("通过")) && // 如果不等于通过
                (!managerReviewExamDto.getManagerReviewInfo().equals("不通过"))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("您输入的题库或者审核意见不合规，请您检查后重新输入");
        }
        examManagementService.examReview(managerReviewExamDto);
        return ResponseEntity.ok("提交成功");
    }


    @ApiOperation(value = "是否通过审核" , notes = "")
    @PostMapping("/upDeleteReview")
    public ResponseEntity<String> upDeleteReviewLog(@Validated ManagerReviewExamDto managerReviewExamDto){
        return ResponseEntity.ok("提交成功");
    }
}
