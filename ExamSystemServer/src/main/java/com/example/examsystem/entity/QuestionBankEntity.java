package com.example.examsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题库表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankEntity {

    // 题库id
     private Integer bankId;

     // 题库名称
    private String bankName;

    // 创建时间
    private String bankCreateTime;

    // 创建人
    private String bankCreateUserName;

    // 该题库一共有多少个试题
    private String questionCount;

    private String existStates;

    @Override
    public String toString() {
        return "QuestionBankEntity{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", bankCreateTime='" + bankCreateTime + '\'' +
                ", bankCreateUserName='" + bankCreateUserName + '\'' +
                ", questionCount='" + questionCount + '\'' +
                ", existStates='" + existStates + '\'' +
                '}';
    }
}
