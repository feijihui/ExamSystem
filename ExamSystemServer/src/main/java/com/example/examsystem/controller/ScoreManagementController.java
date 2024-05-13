package com.example.examsystem.controller;

import com.example.examsystem.server.IScoreManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 4:48
 * @Version 1.0
 */
@Api(tags = "成绩统计分析")
@RestController
@RequestMapping("/score")
public class ScoreManagementController {

    @Autowired
    private IScoreManagementService scoreManagementService;

    @ApiOperation("成绩管理")
    @PostMapping("/scoreNumber")
    public ResponseEntity<String> getScore(Integer minScore , Integer maxScore){
        if (minScore == null || maxScore == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("错误成绩信息");
        }
        System.out.println("dfhjksdfdfd");
        String result = scoreManagementService.getScoreInfo(minScore , maxScore);
        return ResponseEntity.ok(result);
    }
}
