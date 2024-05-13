package com.example.examsystem.server.impl;

import com.example.examsystem.dao.IQuestionDao;
import com.example.examsystem.dto.ManagerReviewQuestionDto;
import com.example.examsystem.entity.QuestionBankEntity;
import com.example.examsystem.dto.QuestionCreate;
import com.example.examsystem.entity.QuestionEntity;
import com.example.examsystem.mq.producer.DeleteQuestionBankProducer;
import com.example.examsystem.mq.producer.QuestionCreateProducer;
import com.example.examsystem.po.ManagerReviewQuestion;
import com.example.examsystem.server.IQuestionService;
import com.example.examsystem.utils.ObjectToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 试卷服务
 */
@Service
public class QuestionService implements IQuestionService {

    @SuppressWarnings("all")
    @Autowired
    private IQuestionDao questionDao;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private QuestionCreateProducer questionCreateProducer;

    @Autowired
    private DeleteQuestionBankProducer deleteQuestionBankProducer;

    // 题库信息
    private QuestionBankEntity questionBank;



    /**
     * 创建题库以及所对应的试题
     * @param questionCreate 试卷
     */
    @Override
    public int bankAndQuestionCreate(QuestionCreate questionCreate){
        int result = -1;
        boolean isExist = false;
        // 判断数据库是否有所提交的题库，没有就创建，有就直接在该题库下创建试题
        isExist = queryQuestionBankIsExist(questionCreate.getBankName());
        if (isExist){
            // 题库存在，直接在该题库基础上创建试题
            result = questionCreateByBankName(questionCreate , questionBank.getBankId());
            // 发送消息到试卷创建队列
            questionCreateProducer.sendMqToQuestionCreateQueue(questionBank.getBankName());
            return result;
        } else{ // 题库不存在，需要先创建题库，在创建试题
            // 创建数据库
            questBankCreate(questionCreate.getBankName());
            bankAndQuestionCreate(questionCreate); // 递归从新执行此方法，题库存在就会执行另一个分支
        }
        return result;
    }


    /**
     * 查询所有的题库记录
     * @return
     */
    @Override
    public String findAllToQuestionBank(){
        // 存储题库信息的容器
        String questionBank = null;
        // 先去redis中查找，如果有直接返回，如果没有，去mysql中查找返回
        questionBank = redisTemplate.opsForValue().get("questionBank");
        if (questionBank != null){
            System.out.println("从redis获取的数据");
            return questionBank;
        }
        // 从mysql中查找题库记录
        questionBank = toMysqlBankAndQuestionCount();
        // 将从mysql数据库中查询到的题库记录保存到redis，以便访问
        mysqlToRedis("questionBank" , questionBank);
        System.out.println("从mysql数据库获取的数据");
        return questionBank;
    }


    /**
     * 根据题库名查询试题
     * @param questionBankName 题库名
     * @return
     */
    @Override
    public String findByBankId(String questionBankName){
        // 先向redis查询，如果没有再到数据库查询
        String redisQuestion = redisTemplate.opsForValue().get(questionBankName);
        if (redisQuestion != null){
            System.out.println("从redis获取的信息");
            return redisQuestion;
        }
        // redis中没有，到mysql数据库查找
        String questionJson = toMysqlQuery(questionBankName);
        // 将从数据库查找的信息添加到redis数据库
        mysqlToRedis(questionBankName , questionJson);
        System.out.println("数据是从mysql获取的:");
        return questionJson;
    }


    /**
     * 通过试题id删除试题
     * @param questionId
     */
    @Override
    public void deleteByQuestionId(int questionId , String questionBankName){
        questionDao.deleteQuestionById(questionId);
        // 并一并将redis里面得数据删除掉，不然会出现mysql没有数据，但依然被查询出来结果，这时因为向redis查找得
        redisTemplate.delete(questionBankName);
    }

    // 删除题库要先通过生产者发消息给管理员审核
    public void deleteToManagerToReview(int questionBankId){
        QuestionBankEntity questionBank = questionDao.findQuestionBankById(questionBankId);
       if (questionBank != null){
           String s = ObjectToJson.objectToJson(questionBank);
           deleteQuestionBankProducer.send(s);
       }
    }

    /**
     * 通过id删除题库
     * @param questionBankId
     */
    @Override
    public String deleteByQuestionBankId(int questionBankId) {
        // 先判断是菊科是否有该id
        boolean isExist = findQuestionBankById(questionBankId);
        // 有id就删除
        if (isExist){
            deleteBankById(questionBankId);
            // 同时删除题库对应的所有的试题
            deleteQuestionByBankId(questionBankId);
            // 删除redis缓存
            redisTemplate.delete("questionBank");
            return "删除题库成功，欢迎您添加新的题库";
        }
        return "题库不存在，请您确认再提交";
    }

    // 到mysql数据库中查找试题数据
    public String toMysqlQuery(String questionBankName){
        // 将多条记录转换为一个json字符串的容器
        List<String> json = new ArrayList<>();
        // 根据题库名到题库表中查询题库id
        boolean isExist = queryQuestionBankIsExist(questionBankName);
        // 根据题库id到试题表中查询对应的试题
        List<QuestionEntity> allByBankId = null;
        if (isExist){
            allByBankId = questionDao.findAllByBankId(questionBank.getBankId());
            for (QuestionEntity questionEntity : allByBankId){
                // 每一条的会先转为json的记录，我们需要把单条放到list集合中
                json.add(ObjectToJson.objectToJson(questionEntity));
            }
        }
        if (json.size() == 0){
            // 如果json中没有数据，那么返回字符串null，并将null存入redis数据库，解决缓存穿透问题
            return "null";
        }
        // list集合返回给前台的话格式会有变化，需要转换字符串返回
        return json.toString();
    }


    // 到数据库查找所有的题库和每个题库有多少个试题
    public String toMysqlBankAndQuestionCount(){

        // 将单条查询出来并已经转为json格式的数据结合在list集合中
        List<String> questionBanks = new ArrayList<>();

        List<QuestionBankEntity> questionBankEntities = questionDao.findQuestionBank();
        for (QuestionBankEntity questionBankEntity : questionBankEntities){
            questionBanks.add(ObjectToJson.objectToJson(questionBankEntity));
        }
        if (questionBanks.size() == 0){
            return "null";
        }
        return questionBanks.toString();
    }




    // 保存管理员审批记录（删除)
    public void addManagerDeleteReview(ManagerReviewQuestionDto managerReviewQuestionDto){

        ManagerReviewQuestion reviewQuestion = new ManagerReviewQuestion();
        reviewQuestion.setManagerName(managerReviewQuestionDto.getManagerName()); // 管理员名称
        reviewQuestion.setManagerReviewQuestion(managerReviewQuestionDto.getBankId()); // 题库id
        if (managerReviewQuestionDto.getManagerReviewInfo().equals("通过")){
            reviewQuestion.setManagerReviewInfo(1);
            // 修改题库里边的审核状态，
            updateQuestionBankStateById(managerReviewQuestionDto.getBankId() , -1);
        }else if (managerReviewQuestionDto.getManagerReviewInfo().equals("不通过")){ // 不通过, 此时不通过就不做任何操作，只需保存审核信息即可
            reviewQuestion.setManagerReviewInfo(-1);
        }
        reviewQuestion.setManagerNotes(managerReviewQuestionDto.getManagerNotes()); // 审核说明
        reviewQuestion.setManagerReviewType("删除审核");// 审核类型
        reviewQuestion.setOperationTypeNotes("试卷删除");
        System.out.println(reviewQuestion);
        questionDao.addManagerReview(reviewQuestion);
    }

    // 修改题库状态字段
    public int updateQuestionBankStateById(Integer questionBankId , Integer state){
        return questionDao.updateQuestionStateById(questionBankId , state);
    }



    // 将从mysql取出的内容存到redis
    public void mysqlToRedis(String key , String value){
        // 随机数
        Random random = new Random();
        // 给每个键设置随机过期时间，目的为了缓存雪崩和避免过多占用资源
        int expireTime = 60000 + random.nextInt(10000);
        redisTemplate.opsForValue().set(key , value , expireTime , TimeUnit.MILLISECONDS);
    }

    // 根据题库id查询数据库的题库记录
    public boolean findQuestionBankById(int questionBankId){
        // 判断数据库是否有该题库
        QuestionBankEntity questionBankById = questionDao.findQuestionBankById(questionBankId);
        System.out.println("题库存在，为：" + questionBankById);
        if (questionBankById != null){
            return true;
        }
        return false;
    }

    // 通过id删除对应的题库
    public void deleteBankById(int questionBankId){
        // 删除题库
        questionDao.deleteQuestionBankById(questionBankId);
    }

    // 删除某个题库下的所有试题
    public void deleteQuestionByBankId(int questionBankId){
        questionDao.deleteQuestionByBankId(questionBankId);
    }


    // 查找数据库是否有该题库
    public boolean queryQuestionBankIsExist(String bankName){
        // 去前后空格
        questionBank = questionDao.questBankIsExist(bankName.trim());
        if (questionBank != null){
            return true;
        }
        System.out.println("题库名不存在");
        return false;
    }

    // 创建题库
    public void questBankCreate(String questionBank){
        questionDao.insertBank(questionBank);
    }


    // 创建试题
    public int questionCreateByBankName(QuestionCreate questionCreate , int questionBank){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setBankId(questionBank); // 题库id
        questionEntity.setQuestionText(questionCreate.getQuestionText()); // 题目
        questionEntity.setQuestionAnswer(questionCreate.getQuestionAnswer()); // 题目答案
        questionEntity.setQuestionPointValue(questionCreate.getQuestionPointValue()); // 题目分值
        questionEntity.setQuestionProblem(questionCreate.getQuestionProblem()); // 题目困难度
        questionEntity.setQuestionAnalyze(questionCreate.getQuestionAnalyze()); // 题目解答解析

        switch (questionCreate.getQuestionType()){
            case "单选":
                questionCreate.setQuestionType(String.valueOf(1));
                break;
            case "多选":
                questionCreate.setQuestionType(String.valueOf(2));
                break;
            case "填空":
                questionCreate.setQuestionType(String.valueOf(3));
                break;
            case "简答":
                questionCreate.setQuestionType(String.valueOf(4));
                break;
        }
        questionEntity.setQuestionType(Integer.valueOf(questionCreate.getQuestionType())); // 题目类型
        // 如果是单选题或者是多选题，就是添加选择题的选项内容
        if ((Integer.parseInt(questionCreate.getQuestionType())) == 1
                || (Integer.parseInt(questionCreate.getQuestionType()) == 2)){
            questionEntity.setQuestionSelectType(questionCreate.getQuestionSelectType());
        }
        // 并设置题库的状态为0，表示还未审核
        questionEntity.setState(0);
        // 将试题添加到数据库
        return questionDao.insertQuestion(questionEntity);
    }

    // 通过题库名查找题库id
    public int findQuestionBankId(String questionBankName){
        QuestionBankEntity questionBank = questionDao.questBankIsExist(questionBankName);
        if (questionBank == null){
            return -1;
        }
        return questionBank.getBankId();
    }

}
