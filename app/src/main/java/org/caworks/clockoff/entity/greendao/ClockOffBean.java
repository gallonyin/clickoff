package org.caworks.clockoff.entity.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 打卡 bean
 * Created by gallon on 2017/6/4
 */
@Entity
public class ClockOffBean {
    public static final String DIVIDER = "##";
    @Id(autoincrement = true)
    private Long id;
    private String name; //名称
    private String desc; //描述(含补卡)
    private String note; //备注(尽量不用)
    private String start; //开始时间 170223
    private String remind; //提醒时间 0#1##123 [0]开关 [1]一周内天数 [2]一天内时间
    private String record; //完成日期记录(含补卡) 0170223#1170224 首位数0补卡 1完成
    private int complete; //完成次数(含补卡)
    private int repair; //补卡次数
    public int getRepair() {
        return this.repair;
    }
    public void setRepair(int repair) {
        this.repair = repair;
    }
    public int getComplete() {
        return this.complete;
    }
    public void setComplete(int complete) {
        this.complete = complete;
    }
    public String getRecord() {
        return this.record;
    }
    public void setRecord(String record) {
        this.record = record;
    }
    public String getRemind() {
        return this.remind;
    }
    public void setRemind(String remind) {
        this.remind = remind;
    }
    public String getStart() {
        return this.start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 38730133)
    public ClockOffBean(Long id, String name, String desc, String note,
            String start, String remind, String record, int complete, int repair) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.note = note;
        this.start = start;
        this.remind = remind;
        this.record = record;
        this.complete = complete;
        this.repair = repair;
    }
    @Generated(hash = 1776049650)
    public ClockOffBean() {
    }
}
