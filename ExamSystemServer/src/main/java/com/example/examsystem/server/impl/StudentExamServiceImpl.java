package com.example.examsystem.server.impl;

import com.example.examsystem.dao.StudentExamDao;
import com.example.examsystem.dto.StudentUpAnswerDto;
import com.example.examsystem.po.ExamRecordPo;
import com.example.examsystem.po.QuestionPo;
import com.example.examsystem.server.IStudentExamService;
import com.example.examsystem.utils.ObjectToJson;
import com.example.examsystem.vo.ExamRecordVo;
import com.example.examsystem.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 学生考试服务
 */
@Service
@Primary
public class StudentExamServiceImpl implements IStudentExamService {


    @SuppressWarnings("all")
    @Autowired
    private StudentExamDao studentExamDao;

    @Autowired
    private RedisTemplate<String  ,String> redisTemplate;



    // 获取考试信息
    @Override
    public String getExamInfo(String studentName) {
        // 通过userid获取name
        int userId = getUserIdByName(studentName);
        List<ExamRecordPo> examRecordPo = studentExamDao.findExamRecordByUserid(userId);
        List<String> result = new ArrayList<>();
        for (ExamRecordPo examRecordPo1 : examRecordPo){
            ExamRecordVo examRecordVo = poChangeVo(examRecordPo1);
            String value = ObjectToJson.objectToJson(examRecordVo);
            result.add(value);
        }
        return result.toString();
    }

    // 上传答案
    @Override
    public void upAnswer(List<StudentUpAnswerDto> studentUpAnswerDto) {
        int total = 0; // 记录分数
        for (StudentUpAnswerDto studentAnswer : studentUpAnswerDto){
            int pointValue = answerCompare(studentAnswer);
            total += pointValue;
        }
        System.out.println("学生分数 —— >" + total);
        List<String> answerList = new ArrayList<>();
        ExamRecordPo examRecordPo = null;
        for (StudentUpAnswerDto studentUpAnswerDto1 : studentUpAnswerDto){
            // 将学生成绩记录保存到数据库
            examRecordPo = examRecord(studentUpAnswerDto1 , total , answerList);
        }
        examRecordPo.setUserAnswerList(answerList.toString());
        System.out.println(examRecordPo);
        // 保存记录到数据库
        examRecordToMysql(examRecordPo);
    }

    // examRecordPo转vo
    public ExamRecordVo poChangeVo(ExamRecordPo examRecordPo){
        return ExamRecordVo.builder()
                .recordId(examRecordPo.getRecordId())
                .userName(getUserNameById(examRecordPo.getUserId()))
                .examName(getExamNameById(examRecordPo.getExamId()))
                .userAnswerList(examRecordPo.getUserAnswerList())
                .userExamEndTime(examRecordPo.getUserExamEndTime())
                .userScore(examRecordPo.getUserScore())
                .build();
    }

    public String getUserNameById(int userId){
        return studentExamDao.findUserNameById(userId);
    }

    public String getExamNameById(int examId){
        return studentExamDao.findExamNameById(examId);
    }

    // 获取试卷
    @Override
    public String getQuestion(String examName) {
        String redisResult = redisTemplate.opsForValue().get(examName);
        if (redisResult != null){
            System.out.println("redis获取的");
            return redisResult;
        }
        // 根据考试名称获取题库id
        Integer questionBankId = studentExamDao.findQuestionBankIdByExamName(examName);
        List<QuestionPo> question = studentExamDao.findQuestionByBankId(questionBankId);// 根据题库id获取试题
        String questionJson = toJson(question);
        saveToRedis(examName , questionJson);
        System.out.println("mysql数据库获取的数据");
        return questionJson;
    }


    // 答案比对
    public int answerCompare(StudentUpAnswerDto studentUpAnswerDto){
        String answer = studentExamDao.findQuestionAnswerById(studentUpAnswerDto.getQuestionId());
        int pointValue = studentExamDao.findQuestionPointValueById(studentUpAnswerDto.getQuestionId());
        System.out.println("学生答案：" + studentUpAnswerDto.getStudentAnswer() + " | " + "正确答案：" + answer +
                " (" + pointValue + ")");
        if (studentUpAnswerDto.getStudentAnswer().equals(answer)){
            System.out.println(" 正确");
            return pointValue;
        }
        return 0;
    }


    // 保存信息到数据库
    public void examRecordToMysql(ExamRecordPo examRecordPo){
        studentExamDao.addExamRecord(examRecordPo);
    }


    //考试记录
    public ExamRecordPo examRecord(StudentUpAnswerDto studentUpAnswerDto , int total , List<String> answerList){
        ExamRecordPo examRecordPo = ExamRecordPo.builder()
                .recordId(null)
                .userId(getUserIdByName(studentUpAnswerDto.getStudentName()))
                .examId(getExamIdByQuestionId(getBankIdByQuestionId(studentUpAnswerDto.getQuestionId())))
                .userExamEndTime(new Date())
                .userScore(total)
                .build();
        answerList.add(studentUpAnswerDto.getStudentAnswer());
        return examRecordPo;

    }


    // 通过试题id获取题库id
    public int getBankIdByQuestionId(int questionId){
        return studentExamDao.findQuestionBankIdById(questionId);
    }
    // 通过试题id获取考试id
    public int getExamIdByQuestionId(int questionBankId){
        return studentExamDao.findExamIdById(questionBankId);
    }

    // 通过姓名获取id
    public int getUserIdByName(String userName){
        return studentExamDao.findUserIdByName(userName);
    }

    // 将试卷保存到redis缓存
    public void saveToRedis(String key, String value) {
        // 随机数
        Random random = new Random();
        // 给每个键设置随机过期时间，目的为了缓存雪崩和避免过多占用资源
        int expireTime = 60000 + random.nextInt(10000);
        redisTemplate.opsForValue().set(key , value , expireTime , TimeUnit.MILLISECONDS);
    }

    // 转换为json
    public String toJson(List<QuestionPo> question) {
        List<String> value = new ArrayList<>();
        for (QuestionPo questionPo : question){
            String questionVo = PoToVo(questionPo);
//            String s = ObjectToJson.objectToJson(questionVo);
            value.add(questionVo);
        }
        return value.toString();
    }

    // 转换为前端需要的数据
    public String PoToVo(QuestionPo question) {
        QuestionVo questionVo = QuestionVo.builder()
                .questionId(question.getQuestionId())
                .questionText(question.getQuestionText())
                .pointValue(question.getQuestionPointValue())
                .typeSelect(question.getQuestionSelectType())
                .problem(question.getQuestionProblem())
                .build();
        switch (question.getQuestionType()){
            case 1:
                questionVo.setQuestionType("单选");
                break;
            case 2:
                questionVo.setQuestionType("多选");
                break;
            case 3:
                questionVo.setQuestionType("填空");
                break;
            case 4:
                questionVo.setQuestionType("简答");
                break;
        }
        return ObjectToJson.objectToJson(questionVo);
    }




}
