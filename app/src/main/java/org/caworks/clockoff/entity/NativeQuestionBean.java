//package org.caworks.clockoff.entity;
//
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.annotation.Id;
//
///**
// * 本地问答数据库 bean
// * Created by gallon on 2017/6/4
// */
//public class ClockOffBean {
//    public static final String divider = "DIVIDER";
//    @Id(autoincrement = true)
//    private Long id;
//    private String answer;
//    private String question;
//    private String similarQuestion;
//    private int type; // (type=3 聊天)
//    public int getType() {
//        return this.type;
//    }
//    public void setType(int type) {
//        this.type = type;
//    }
//    public String getSimilarQuestion() {
//        return this.similarQuestion;
//    }
//    public void setSimilarQuestion(String similarQuestion) {
//        this.similarQuestion = similarQuestion;
//    }
//    public String getQuestion() {
//        return this.question;
//    }
//    public void setQuestion(String question) {
//        this.question = question;
//    }
//    public String getAnswer() {
//        return this.answer;
//    }
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
//    public Long getId() {
//        return this.id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    @Generated(hash = 892143451)
//    public ClockOffBean(Long id, String answer, String question,
//                              String similarQuestion, int type) {
//        this.id = id;
//        this.answer = answer;
//        this.question = question;
//        this.similarQuestion = similarQuestion;
//        this.type = type;
//    }
//    @Generated(hash = 1413326856)
//    public ClockOffBean() {
//    }
//}
