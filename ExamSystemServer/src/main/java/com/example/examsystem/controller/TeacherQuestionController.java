package com.example.examsystem.controller;

import com.example.examsystem.dto.QuestionCreate;
import com.example.examsystem.server.IQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@Api(value = "用于对试卷的crud操作" , tags = "试卷管理(老师)")
@RequestMapping("/question")
public class TeacherQuestionController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 老师添加考题
     * @param questionCreate
     * @return
     */
    @ApiOperation(value = "添加考题" , notes = "老师添加考题")
    @PostMapping("/add")
    public ResponseEntity<String> addQuestionBankAndQuestion(@RequestBody @Validated QuestionCreate questionCreate){
        System.out.println(questionCreate);
        questionService.bankAndQuestionCreate(questionCreate);
        return ResponseEntity.ok("上传成功");
    }


    /**
     * 查询所有的题库记录
     * @return
     */
    @ApiOperation(value = "查询题库" , notes = "查询所有题库信息以及题库所对应的试题数量")
    @GetMapping("/queryAllQuestionBank")
    public ResponseEntity<String> findAllToQuestionBank(){
        String result = questionService.findAllToQuestionBank();
        if (result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("获取失败");
        }
        return ResponseEntity.ok(result);
    }





    /**
     * 根据题库查询题库中的试题
     * @param questionBankName 题库名
     * @return
     */
    @ApiOperation(value = "查询试题" , notes = "根据题库查询题库中的试题")
    @GetMapping("/{queryByBankName}")
    public ResponseEntity<String> findAll(@PathVariable(value = "queryByBankName") String questionBankName){
        if (questionBankName.trim().equals("null")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("没有值哦，小乖乖");
        }
        String questionJson = questionService.findByBankId(questionBankName);
        return ResponseEntity.ok(questionJson);
    }

    /**
     * 根据试题id删除所对应的试题
     * @param questionId 试题id，用于删除数据库的
     * @param questionBankName  题库名称，用户删除redis缓存的
     * @return
     */
    @ApiOperation(value = "删除试题" , notes = "根据试题id删除所对应的试题")
    @DeleteMapping("/{questionBankName}/{questionId}")
    public ResponseEntity<String> deleteById(@PathVariable("questionId") @ApiParam(value = "该参数用于删除mysql的记录") int questionId ,
                                             @PathVariable("questionBankName") @ApiParam(value = "该参数用于删除redis的缓存") String questionBankName){
        System.out.println(questionId + " | " + questionBankName);
        if (questionId == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("没有0这个题");
        }
        questionService.deleteByQuestionId(questionId , questionBankName);
        return ResponseEntity.ok("删除成功");
    }


    /**
     * 根据上传的题库id删除对应的题库，以及删除对应的所有试题
     * url ： http://localhost:27915/question/{id}
     * @param questionBankId 题库id
     * @return
     */
    @ApiOperation(value = "删除题库" , notes = "删除题库以及题库所对应的所有的试题")
    @DeleteMapping("/{questionBankId}")
    public ResponseEntity<String> deleteQuestionBankById(@PathVariable("questionBankId") int questionBankId){
        if (questionBankId == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id不合法");
        }
        questionService.deleteToManagerToReview(questionBankId);
        return ResponseEntity.ok("您的删除题库以提交，请等管理员审核完成");
    }


}
