package com.example.examsystem.controller;

import com.example.examsystem.dto.ExamDto;
import com.example.examsystem.server.IExamManagementService;
import com.example.examsystem.vo.ExamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考试管理
 */
@RestController
@Api(tags = "考试管理")
@RequestMapping("/exam")
public class ExamManagementController {

    @Autowired
    private IExamManagementService examManagementService;


    /**
     * 老师添加考试
     * @return
     */
    @ApiOperation(value = "创建考试")
    @PostMapping("/addExam")
    public ResponseEntity<String> addExam(@RequestBody ExamDto examDto){
        int result = examManagementService.addExam(examDto);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("保存到数据库出错了，请联系相关人员处理");
        }
        return ResponseEntity.ok("创建考试成功");
    }


    // 删除考试
    @ApiOperation(value = "删除考试")
    @DeleteMapping("/{deleteExamId}")
    public ResponseEntity<String> deleteExam(@PathVariable("deleteExamId") @ApiParam("考试id") Integer deleteExamId){
        if (deleteExamId == null || deleteExamId == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("输入不和规，找不到对应资源，请认真输入");
        }
        examManagementService.deleteExam(deleteExamId);
        return ResponseEntity.ok("删除成功");
    }

    // 修改考试
    @ApiOperation(value = "修改考试信息")
    @PutMapping("/updateExam")
    public ResponseEntity<String> updateExam(){
        // TODO
        return ResponseEntity.ok("修改成功");
    }


    /**
     * 查询所有考试
     * @return
     */
    @ApiOperation(value = "查询考试")
    @GetMapping("/{queryExam}")
    public ResponseEntity<String> queryAllExam(@PathVariable("queryExam") @ApiParam("考试类型（未考式1/考试结束0）") Integer queryExam){
        if (queryExam == null || queryExam != 0 && queryExam != 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("输入不和规，找不到对应资源，请认真输入");
        }
        String exam = examManagementService.findAll(queryExam);
        return ResponseEntity.ok(exam);
    }

}
