package com.example.examsystem.server.impl;

import com.example.examsystem.dao.IScoreDao;
import com.example.examsystem.po.ExamRecordPo;
import com.example.examsystem.server.IScoreManagementService;
import com.example.examsystem.utils.ObjectToJson;
import com.example.examsystem.vo.ScoreInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class_Info ExamSystemServer 类描述
 * @Author 费基辉
 * @Date 2024/5/5 5:07
 * @Version 1.0
 */
@Service
@Primary
public class ScoreManagementServiceImpl implements IScoreManagementService {

    @SuppressWarnings("all")
    @Autowired
    private IScoreDao scoreDao;


    @Override
    public String getScoreInfo(Integer minScore , Integer maxScore) {
        List<ExamRecordPo> scoreInfoVo = scoreDao.findScoreInfoByScore(minScore , maxScore);

        List<String> result = new ArrayList<>();
        for (ExamRecordPo examRecordPo : scoreInfoVo){
            ScoreInfoVo value = change(examRecordPo);
            result.add(ObjectToJson.objectToJson(value));
        }
        System.out.println(result.toString());
        return result.toString();
    }

    private ScoreInfoVo change(ExamRecordPo examRecordPo) {
        return ScoreInfoVo.builder()
                .scoreId(examRecordPo.getRecordId())
                .userName(getUserNameById(examRecordPo.getUserId()))
                .examName(getExamNameById(examRecordPo.getExamId()))
                .examTime(examRecordPo.getUserExamEndTime())
                .examScore(examRecordPo.getUserScore())
                .build();
    }

    private String getExamNameById(Integer examId) {
        return scoreDao.findExamNameById(examId);
    }

    private String getUserNameById(Integer userId) {
        return scoreDao.findUserNameById(userId);
    }


}
