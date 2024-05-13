package com.example.examsystem.controller;

import com.example.examsystem.dto.StudentUpAnswerDto;
import com.example.examsystem.server.IStudentExamService;
import com.example.examsystem.vo.QuestionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/4 8:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/studentExam")
@Api(tags = "学生考试")
public class StudentExamController {

    @Autowired
    private IStudentExamService studentExamService;

    @ApiOperation("获取试卷")
    @GetMapping("/{examName}")
    public String getQuestion(@PathVariable("examName") @ApiParam("考试名称") String examName){
        return studentExamService.getQuestion(examName);
    }


    @ApiOperation("学生上传答案")
    @PostMapping("/upAnswer")
    public String studentAnswer(@RequestBody List<StudentUpAnswerDto> studentUpAnswerDto){
        for (StudentUpAnswerDto studentUpAnswerDto1 : studentUpAnswerDto){
            System.out.println(studentUpAnswerDto1);
        }
        studentExamService.upAnswer(studentUpAnswerDto);
        return "答案上传成功";
    }


    @ApiOperation("分数查询")
    @GetMapping("/queryScore/{studentName}")
    private String queryTotal(@PathVariable("studentName") String studentName){
        return studentExamService.getExamInfo(studentName);
    }
}
