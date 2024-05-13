package com.example.examsystem.server.impl;

import com.example.examsystem.dao.IExamManagementDao;
import com.example.examsystem.dto.ExamDto;
import com.example.examsystem.dto.ManagerReviewExamDto;
import com.example.examsystem.mq.producer.DeleteExamReviewProducer;
import com.example.examsystem.mq.producer.ExamCreateReviewProducer;
import com.example.examsystem.po.Exam;
import com.example.examsystem.po.ManagerReviewQuestion;
import com.example.examsystem.server.IExamManagementService;
import com.example.examsystem.utils.ObjectToJson;
import com.example.examsystem.vo.ExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 考试管理
 */
@Service
@Primary
public class ExamManagementServiceImpl implements IExamManagementService {

    @SuppressWarnings("all")
    @Autowired
    private IExamManagementDao examManagementDao;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private ExamCreateReviewProducer examCreateReviewProducer;

    @Autowired
    private DeleteExamReviewProducer deleteExamReviewProducer;

    /**
     * 添加考试
     * @param examDto
     * @return
     */
    @Override
    public int addExam(ExamDto examDto) {
        // 前端数据转换为数据库字段
        Exam exam = ExamDtoAndExamChange(examDto);
        System.out.println("考试信息：" + exam);
        // 将考试信息保存到数据库
        int result =  examManagementDao.addExam(exam);
        // 通知管理员审核
        examCreateReviewProducer.sender(exam);
        return result;
    }


    @Override
    public void deleteExam(Integer deleteExamId) {
        Exam exam = examManagementDao.findExamById(deleteExamId);
        String value = examToExamVoChange(exam);
        // 发送消息给管理员
        deleteExamReviewProducer.send(value);
    }

    @Override
    public void examReview(ManagerReviewExamDto managerReviewExamDto) {
        // 修改数据看中考试状态，通过改为1
        if (managerReviewExamDto.getManagerReviewInfo().equals("通过")){
            updateExamState();
        }
        // 数据库添加审核信息
        addReviewExamInfo(managerReviewExamDto);
    }


    @Override
    public String findAll(Integer queryExam) {
        // 先到redis查询，如果缓存没有，就到mysql查询并保存到redis
        String redisValue = redisTemplate.opsForValue().get(String.valueOf(queryExam));
        if (redisValue != null){
            System.out.println("这是从redis获取的");
            return redisValue;
        }
        String exam = null;
        if (queryExam == 0){
            // 查询已过期的考试信息
            exam = examExisted();
            // 将数据存在redis
            toRedis("0" , exam);
        } else if (queryExam == 1){
            // 查询现在需要进行考试的信息
            exam = examExist();
            // 将数据存在redis
            toRedis("1" , exam);
        }
        System.out.println("这是从mysql获取的");
        return exam;
    }


    // 查询需要考试的1
    public String examExist(){
        List<String> value = new ArrayList<>();
        String result = null;
        List<Exam> examList = examManagementDao.examsExist();
        for (Exam exam : examList){
            result = examToExamVoChange(exam);
            value.add(result);
        }
        if (examList.size() == 0){
            return "null";
        }
        return value.toString();
    }


    // 查询考试结束的0
    public String examExisted(){
        List<String> value = new ArrayList<>();
        String result = null;
        List<Exam> examList = examManagementDao.examsExisted();
        for (Exam exam : examList){
            result = examToExamVoChange(exam);
            value.add(result);
        }
        if (examList.size() == 0){
            return "null";
        }
        return value.toString();
    }

    // 将考试数存在redis
    public void toRedis(String key , String value){
        Random random = new Random();
        // 给每个键设置随机过期时间，目的为了缓存雪崩和避免过多占用资源
        int expireTime = 60000 + random.nextInt(10000);
        redisTemplate.opsForValue().set(key , value , expireTime , TimeUnit.MILLISECONDS);
    }

    // 将考试信息转换为前端需要的信息
    public String examToExamVoChange(Exam exam){
        ExamVo examVo = ExamVo.builder()
                .examId(exam.getExamId())
                .questionBankName(getQuestionBankNameById(exam.getQuestionBankId()))
                .userName(getUserNameById(exam.getUserId()))
                .examName(exam.getExamName())
                .examDescriptive(exam.getExamDescriptive())
                .examMethod(examMethodChange(exam.getExamMethod()))
                .examPassword(exam.getExamPassword())
                .examStartTime(exam.getExamStartTime())
                .examEndTime(exam.getExamEndTime())
                .examTotal(exam.getExamTotal())
//                .examExistState()
                .examAddress(exam.getExamAddress())
                .build();
        return ObjectToJson.objectToJson(examVo);
    }


    // 通过题库id获取题库名称
    public String getQuestionBankNameById(int bankId){
        return examManagementDao.findQuestionBankNameById(bankId);
    }

    // 通过用户id获取用户名称
    public String getUserNameById(int userId){
        return examManagementDao.findUserNameById(userId);
    }

    // 将考试方法转换为前端需要的数据
    public String examMethodChange(int examMethod){
        if (examMethod == 1){
            return "需要密码";
        } else if (examMethod == 0){
            return "不需要密码";
        }
        return "null";
    }

    // 修改数据库中考试状态为1
    public void updateExamState(){
        examManagementDao.updateExamExistState();
    }

    // 添加审核信息
    public void addReviewExamInfo(ManagerReviewExamDto managerReviewExamDto){
        ManagerReviewQuestion managerReviewQuestion = managerReviewChange(managerReviewExamDto);
        examManagementDao.addManagerReviewInfo(managerReviewQuestion);

    }

    public int reviewInfo(String managerReviewInfo){
        if (managerReviewInfo.equals("通过")){
            return 1;
        }
        return -1;
    }

    public ManagerReviewQuestion managerReviewChange(ManagerReviewExamDto managerReviewExamDto){
        ManagerReviewQuestion managerReviewQuestion = new ManagerReviewQuestion();
        managerReviewQuestion.setManagerId(null);
        managerReviewQuestion.setManagerName(managerReviewExamDto.getManagerName());
        managerReviewQuestion.setManagerReviewExam(getExamIdByName(managerReviewExamDto.getExamName()));
        managerReviewQuestion.setManagerReviewQuestion(0);
        managerReviewQuestion.setManagerReviewInfo(reviewInfo(managerReviewExamDto.getManagerReviewInfo()));
        managerReviewQuestion.setManagerNotes(managerReviewExamDto.getManagerNotes());
        managerReviewQuestion.setManagerReviewType("添加审核");
        managerReviewQuestion.setOperationTypeNotes("考试创建");
        return managerReviewQuestion;
    }

    // 根据考试名称获取考试id
    public int getExamIdByName(String examName){
        return examManagementDao.findExamIdByName(examName);
    }

    public Exam ExamDtoAndExamChange(ExamDto examDto){
        return Exam.builder()
                .examId(null) // 考试id
                .questionBankId(getQuestionBankIdByName(examDto.getQuestionBankName())) // 题库
                .userId(getUserIdByName(examDto.getUserName())) // 考试创建人
                .examName(examDto.getExamName()) // 考试名称
                .examDescriptive(examDto.getExamDescriptive()) // 考试描述
                .examMethod(examMethodChange(examDto.getExamMethod())) // 考试方式是否需要密码（1需要，0不需要）
                .examPassword(examDto.getExamPassword()) // 考试密码（-1表示没有密码）
                .examStartTime(examDto.getExamStartTime()) // 考试开始时间
                .examEndTime(examDto.getExamEndTime()) // 考试结束时间
                .examTotal(getExamTotal(getQuestionBankIdByName(examDto.getQuestionBankName()))) // 考试总分
                .examExistState(-1) // 考试是否有效（1有效，-1未考试，被删除取消考试，0考试结束）
                .examAddress(examDto.getExamAddress()) // 考试地点
                .build();
    }

    // 考试方式（该场考试是否需要密码）通过/不通过 ----> 1 / 0
    public int examMethodChange(String examMethod){
        if (examMethod.equals("需要密码")){
            return 1;
        } else if (examMethod.equals("不需要密码")){
            return 0;
        }
        return 0;
    }

    // 根据题库名称获取题库id
    public int getQuestionBankIdByName(String questionBankName){
        return examManagementDao.findQuestionBankIdByName(questionBankName);
    }

    // 通过创建人名称获取用户id
    public int getUserIdByName(String userName){
        return examManagementDao.findUserIdByName(userName);
    }

    // 考试总分计算
    public int getExamTotal(int questionBankId){
        int total = 0;
        List<Integer> pointValues = examManagementDao.findQuestionPointValue(questionBankId);
        for (int i : pointValues){
            total += i;
        }
        return total;
    }
}
