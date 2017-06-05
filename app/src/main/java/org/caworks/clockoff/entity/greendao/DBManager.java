package org.caworks.clockoff.entity.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.info.aegis.lawpush4android.utils.Constant;
import com.info.aegis.lawpush4android.utils.MyLog;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.info.aegis.lawpush4android.model.bean.greendao.NativeQuestionBean.divider;
import static com.info.aegis.lawpush4android.utils.PublicMethod.newRandom;

/**
 * 数据库管理器
 * 通过 DBManager.getInstance(context) 获得
 * <p>
 * NOTE: 如需执行原生sql语句 getWritableDatabase().execSQL(sql)
 * <p>
 * Created by gallon on 2017/4/17.
 */

public class DBManager {
    private static DBManager mInstance;
    public static final String DB_NAME = "law_push.db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private NativeQuestionBeanDao nativeQuestionBeanDao;

    private DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 插入NativeQuestionBean (插入/插入集合 可以先查询 如果有就更新.. 待续)
     * @param isCheckUpdate 是否在插入时检查已存在，true做更新操作，false做插入操作
     * @return bean为空 -1 ; 更新操作 -2 ; otherwise 影响行数
     */
    public long insertNativeQuestionBean(NativeQuestionBean nativeQuestionBean, boolean isCheckUpdate) {
        if (nativeQuestionBean == null) {
            return -1;
        }
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
//        MyLog.e("DBManager", "nativeQuestionBean.getSimilarQuestion(): " + nativeQuestionBean.getSimilarQuestion());
        if (isCheckUpdate) {
            List<NativeQuestionBean> nativeQuestionBeans = nativeQuestionBeanDao.queryBuilder().where(NativeQuestionBeanDao.Properties.SimilarQuestion.eq(nativeQuestionBean.getSimilarQuestion())).build().list();
            if (nativeQuestionBeans.size() > 0) {
                NativeQuestionBean oldNativeQuestionBean = nativeQuestionBeans.get(0);
                oldNativeQuestionBean.setAnswer(nativeQuestionBean.getAnswer());
                oldNativeQuestionBean.setQuestion(nativeQuestionBean.getQuestion());
                oldNativeQuestionBean.setSimilarQuestion(nativeQuestionBean.getSimilarQuestion());
                oldNativeQuestionBean.setType(nativeQuestionBean.getType());
                nativeQuestionBeanDao.update(oldNativeQuestionBean);
                return -2;
            }
        }
        return nativeQuestionBeanDao.insert(nativeQuestionBean);
    }

    /**
     * 插入NativeQuestionBean集合
     * @param isCheckUpdate 是否在插入时检查已存在，true做更新操作，false做插入操作
     */
    public void insertNativeQuestionBeanList(List<NativeQuestionBean> nativeQuestionBeanList, boolean isCheckUpdate) {
        if (nativeQuestionBeanList == null || nativeQuestionBeanList.isEmpty()) {
            return;
        }
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
        if (isCheckUpdate) {
            List<NativeQuestionBean> insertList = new ArrayList<>();
            List<NativeQuestionBean> updateList = new ArrayList<>();
            List<NativeQuestionBean> nativeQuestionBeans = nativeQuestionBeanDao.queryBuilder().build().list();
            for (NativeQuestionBean nativeQuestionBean : nativeQuestionBeanList) {
                int i;
                for (i = 0; i < nativeQuestionBeans.size(); i++) {
                    if (nativeQuestionBean.getSimilarQuestion().equals(nativeQuestionBeans.get(i).getSimilarQuestion())) {
                        NativeQuestionBean oldNativeQuestionBean = nativeQuestionBeans.get(i);
                        oldNativeQuestionBean.setAnswer(nativeQuestionBean.getAnswer());
                        oldNativeQuestionBean.setQuestion(nativeQuestionBean.getQuestion());
                        oldNativeQuestionBean.setSimilarQuestion(nativeQuestionBean.getSimilarQuestion());
                        oldNativeQuestionBean.setType(nativeQuestionBean.getType());
                        updateList.add(oldNativeQuestionBean);
                        break;
                    }
                }
                if (i == nativeQuestionBeans.size()) {
                    insertList.add(nativeQuestionBean);
                }
            }
            if (insertList.size() > 0) {
                MyLog.e("DBManager", "insertList.size(): "+ insertList.size());
                nativeQuestionBeanDao.insertInTx(insertList);
            }
            if (updateList.size() > 0) {
                MyLog.e("DBManager", "updateList.size(): "+ updateList.size());
                nativeQuestionBeanDao.updateInTx(updateList);
            }
        } else {
            nativeQuestionBeanDao.insertInTx(nativeQuestionBeanList);
        }
    }

    /**
     * 删除NativeQuestionBean (暂时不用删除)
     */
    public void deleteNativeQuestionBean(String name) {
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
//        List<NativeQuestionBean> nativeQuestionBeans = nativeQuestionBeanDao.queryBuilder().where(NativeQuestionBeanDao.Properties.Name.eq(name)).build().list();
//        nativeQuestionBeanDao.deleteInTx(nativeQuestionBeans);
    }

    /**
     * 删除NativeQuestionBean全部数据
     */
    public void deleteNativeQuestionBean() {
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
        nativeQuestionBeanDao.deleteAll();
    }

    /**
     * 更新NativeQuestionBean (暂时不用更新)
     */
    public void updateNativeQuestionBean(String name, String parentValue) {
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
        //一句话查询
//        List<NativeQuestionBean> nativeQuestionBeans = nativeQuestionBeanDao.queryBuilder().where(NativeQuestionBeanDao.Properties.Name.eq(name)).build().list();
//        for (int i = 0; i < nativeQuestionBeans.size(); i++) {
//            final NativeQuestionBean nativeQuestionBean = nativeQuestionBeans.get(i);
//            nativeQuestionBean.setId(nativeQuestionBean.getId());
//            nativeQuestionBean.setParent(parentValue);
//            nativeQuestionBeanDao.update(nativeQuestionBean);
//        }
    }

    /**
     * 查询NativeQuestionBean (根据 similarQuestion)
     */
    public List<NativeQuestionBean> queryNativeQuestionBeanList(String similarQuestion) {
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
        QueryBuilder<NativeQuestionBean> qb = nativeQuestionBeanDao.queryBuilder();
        qb.where(NativeQuestionBeanDao.Properties.SimilarQuestion.eq(similarQuestion)).orderDesc(NativeQuestionBeanDao.Properties.Id);
        List<NativeQuestionBean> list = qb.list();
        return list;
    }

    /**
     * 查询NativeQuestionBean 返回全部数据
     */
    public List<NativeQuestionBean> queryNativeQuestionBeanList() {
        if (nativeQuestionBeanDao == null) {
            newNativeQuestionBeanDao();
        }
        QueryBuilder<NativeQuestionBean> qb = nativeQuestionBeanDao.queryBuilder();
        List<NativeQuestionBean> list = qb.list();
        return list;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDB() {
        if (openHelper != null) {
            openHelper.close();
            nativeQuestionBeanDao = null;
        }
    }

    /**
     * 获取 nativeQuestionBeanDao 实例
     */
    private void newNativeQuestionBeanDao() {
        if (nativeQuestionBeanDao == null) {
            synchronized (this) {
                if (nativeQuestionBeanDao == null) {
                    DaoMaster DaoMaster = new DaoMaster(getWritableDatabase());
                    DaoSession daoSession = DaoMaster.newSession();
                    nativeQuestionBeanDao = daoSession.getNativeQuestionBeanDao();
                }
            }
        }
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 用来获取本地问答库数据
     * 精确度等级 accuracy 最后输出最高精确度的答案
     *
     * 可修改参数： minValue 0-1
     */
    public static class NativeQuestionHelper {

        private float minValue = 0.95f;

        public List<NativeQuestionBean> nativeQuestionBeanList;
        private StringBuilder sb =  new StringBuilder();

        public NativeQuestionHelper(Context context, float minValue) {
            nativeQuestionBeanList = DBManager.getInstance(context).queryNativeQuestionBeanList();
            if (minValue >= 0) {
                this.minValue = minValue;
            }
        }

        /**
         * 返回命中匹配字符串 otherwise null
         * @param content
         * @return
         */
        public String answer(String content) {
            float minValue = this.minValue;
            String cut = reduceContent(content);
            MyLog.e("ChatActivity", "nativeQuestionBeanList.size(): " + nativeQuestionBeanList.size());
            float value;
            int accuracy = 0;
            NativeQuestionBean answerBean = null;

            for (NativeQuestionBean nativeQuestionBean : nativeQuestionBeanList) {
                //如果答案为空就跳过
                if (TextUtils.isEmpty(nativeQuestionBean.getAnswer())) {
                    continue;
                }

                value = Constant.getSimilarityRatio(content, nativeQuestionBean.getSimilarQuestion());

                //等级3 语音输入的字符串简单移除首位标点后与库匹配
                if (value > minValue) {
                    Log.e("lawPush4Android", "本地数据库匹配分值: "+value );
                    MyLog.e("ChatActivity", "value: " + value + " level: 3");
                    MyLog.e("ChatActivity", "getSimilarQuestion: " + nativeQuestionBean.getSimilarQuestion());
                    MyLog.e("ChatActivity", "getAnswer(): " + nativeQuestionBean.getAnswer());
                    answerBean = nativeQuestionBean;
                    minValue = value;
                    accuracy = 3;
                }

                //如果精简后太短就跳过
                if (cut == null) {
                    continue;
                }
                //如果已经有更高等级匹配就跳过
                if (accuracy == 3) {
                    continue;
                }
                //等级2
                int value2 = Constant.getEditDistance(cut, nativeQuestionBean.getSimilarQuestion());
                if (value2 == nativeQuestionBean.getSimilarQuestion().length() - cut.length()) {
                    MyLog.e("ChatActivity", "value2: " + value2 + " level: 2");
                    MyLog.e("ChatActivity", "getSimilarQuestion: " + nativeQuestionBean.getSimilarQuestion());
                    MyLog.e("ChatActivity", "getAnswer(): " + nativeQuestionBean.getAnswer());
                    answerBean = nativeQuestionBean;
                    accuracy = 2;
                    break;
                }
            }
            if (accuracy > 0) {
                Random random = newRandom();
                String[] answers = answerBean.getAnswer().split(divider);
                int r = random.nextInt(answers.length);
                return answers[r];
            }
            return null;
        }

        /**
         * 精简用户语音输入的字符串 且必须大于一定长度 提高容错率
         */
        private String reduceContent(String content) {
            sb.setLength(0);
            char[] chars = content.toCharArray();
            for (char aChar : chars) { //在、叫、是
                if (aChar == '谁' || aChar == '我' || aChar == '你' || aChar == '们'
                        || aChar == '的' || aChar == '吗' || aChar == '知' || aChar == '道'
                        || aChar == '什' || aChar == '么' || aChar == '了' || aChar == '解'
                        || aChar == '做' || aChar == '怎' || aChar == '可' || aChar == '以'
                        || aChar == '哪' || aChar == '这' || aChar == '里' || aChar == '那'
                        || aChar == '去' || aChar == '来' || aChar == '呀' || aChar == '些'
                        || aChar == '啊' || aChar == '给' || aChar == '没' || aChar == '有'
                        || aChar == '能' || aChar == '不' || aChar == '啥'
                        || aChar == '，' || aChar == ',' || aChar == '?'
                        || aChar == '？' || aChar == '!' || aChar == '！' || aChar == '.'
                        || aChar == '。'
                        ) {
                    continue;
                }
                sb.append(aChar);
            }
            MyLog.e("ChatActivity", "sb: " + sb);
            if (sb.length() > 4) {
                return sb.toString();
            }
            return null;
        }
    }
}
