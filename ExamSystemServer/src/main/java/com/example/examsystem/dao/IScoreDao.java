package com.example.examsystem.dao;

import com.example.examsystem.po.ExamRecordPo;
import com.example.examsystem.vo.ScoreInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 5:08
 * @Version 1.0
 */
public interface IScoreDao {
    List<ExamRecordPo> findScoreInfoByScore(@Param("minScore") int minScore, @Param("maxScore") int maxScore);

    String findExamNameById(@Param("examId") Integer examId);

    String findUserNameById(@Param("userId") Integer userId);
}
