package org.caworks.clockoff.entity.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.caworks.library.util.GLog;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理器
 * 通过 DBManager.getInstance(context) 获得
 * <p>
 * NOTE: 如需执行原生sql语句 getWritableDatabase().execSQL(sql)
 * <p>
 * Created by gallon on 2017/6/4
 */

public class DBManager {
    private static DBManager mInstance;
    private static final String DB_NAME = "clock_off.db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private ClockOffBeanDao clockOffBeanDao;

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
     * 插入clockOffBean (插入/插入集合 可以先查询 如果有就更新.. 待续)
     * @param isCheckUpdate 是否在插入时检查已存在，true做更新操作，false做插入操作
     * @return bean为空 -1 ; 更新操作 -2 ; otherwise 影响行数
     */
    public long insertClockOffBean(ClockOffBean clockOffBean, boolean isCheckUpdate) {
        if (clockOffBean == null) {
            return -1;
        }
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
//        GLog.e("DBManager", "clockOffBean.getSimilarQuestion(): " + clockOffBean.getSimilarQuestion());
//        if (isCheckUpdate) {
//            List<ClockOffBean> clockOffBeanList = clockOffBeanDao.queryBuilder().where(ClockOffBeanDao.Properties.SimilarQuestion.eq(clockOffBean.getSimilarQuestion())).build().list();
//            if (clockOffBeanList.size() > 0) {
//                ClockOffBean oldClockOffBean = clockOffBeanList.get(0);
//                oldClockOffBean.setAnswer(clockOffBean.getAnswer());
//                oldClockOffBean.setQuestion(clockOffBean.getQuestion());
//                oldClockOffBean.setSimilarQuestion(clockOffBean.getSimilarQuestion());
//                oldClockOffBean.setType(clockOffBean.getType());
//                clockOffBeanDao.update(oldClockOffBean);
//                return -2;
//            }
//        }
        return clockOffBeanDao.insert(clockOffBean);
    }

    /**
     * 插入clockOffBean集合
     * @param isCheckUpdate 是否在插入时检查已存在，true做更新操作，false做插入操作
     */
    public void insertclockOffBeanList(List<ClockOffBean> clockOffBeanList, boolean isCheckUpdate) {
        if (clockOffBeanList == null || clockOffBeanList.isEmpty()) {
            return;
        }
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
//        if (isCheckUpdate) {
//            List<ClockOffBean> insertList = new ArrayList<>();
//            List<ClockOffBean> updateList = new ArrayList<>();
//            List<ClockOffBean> clockOffBeans = clockOffBeanDao.queryBuilder().build().list();
//            for (ClockOffBean clockOffBean : clockOffBeanList) {
//                int i;
//                for (i = 0; i < clockOffBeans.size(); i++) {
//                    if (clockOffBean.getSimilarQuestion().equals(clockOffBeans.get(i).getSimilarQuestion())) {
//                        ClockOffBean oldClockOffBean = clockOffBeans.get(i);
//                        oldClockOffBean.setAnswer(clockOffBean.getAnswer());
//                        oldClockOffBean.setQuestion(clockOffBean.getQuestion());
//                        oldClockOffBean.setSimilarQuestion(clockOffBean.getSimilarQuestion());
//                        oldClockOffBean.setType(clockOffBean.getType());
//                        updateList.add(oldClockOffBean);
//                        break;
//                    }
//                }
//                if (i == clockOffBeans.size()) {
//                    insertList.add(clockOffBean);
//                }
//            }
//            if (insertList.size() > 0) {
//                GLog.e("DBManager", "insertList.size(): "+ insertList.size());
//                clockOffBeanDao.insertInTx(insertList);
//            }
//            if (updateList.size() > 0) {
//                GLog.e("DBManager", "updateList.size(): "+ updateList.size());
//                clockOffBeanDao.updateInTx(updateList);
//            }
//            return;
//        }
        clockOffBeanDao.insertInTx(clockOffBeanList);
    }

    /**
     * 删除clockOffBean (暂时不用删除)
     */
    public void deleteclockOffBean(String name) {
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
//        List<ClockOffBean> clockOffBeans = clockOffBeanDao.queryBuilder().where(ClockOffBeanDao.Properties.Name.eq(name)).build().list();
//        clockOffBeanDao.deleteInTx(clockOffBeans);
    }

    /**
     * 删除clockOffBean全部数据
     */
    public void deleteclockOffBean() {
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
        clockOffBeanDao.deleteAll();
    }

    /**
     * 更新clockOffBean (暂时不用更新)
     */
    public void updateclockOffBean(String name, String parentValue) {
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
        //一句话查询
//        List<ClockOffBean> clockOffBeans = clockOffBeanDao.queryBuilder().where(ClockOffBeanDao.Properties.Name.eq(name)).build().list();
//        for (int i = 0; i < clockOffBeans.size(); i++) {
//            final ClockOffBean clockOffBean = clockOffBeans.get(i);
//            clockOffBean.setId(clockOffBean.getId());
//            clockOffBean.setParent(parentValue);
//            clockOffBeanDao.update(clockOffBean);
//        }
    }

    /**
     * 查询clockOffBean (根据 name)
     */
    public List<ClockOffBean> queryclockOffBeanList(String name) {
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
        QueryBuilder<ClockOffBean> qb = clockOffBeanDao.queryBuilder();
        qb.where(ClockOffBeanDao.Properties.Name.eq(name)).orderDesc(ClockOffBeanDao.Properties.Id);
        List<ClockOffBean> list = qb.list();
        return list;
    }

    /**
     * 查询clockOffBean 返回全部数据
     */
    public List<ClockOffBean> queryclockOffBeanList() {
        if (clockOffBeanDao == null) {
            newClockOffBeanDao();
        }
        QueryBuilder<ClockOffBean> qb = clockOffBeanDao.queryBuilder();
        List<ClockOffBean> list = qb.list();
        return list;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDB() {
        if (openHelper != null) {
            openHelper.close();
            clockOffBeanDao = null;
        }
    }

    /**
     * 获取 clockOffBeanDao 实例
     */
    private void newClockOffBeanDao() {
        if (clockOffBeanDao == null) {
            synchronized (this) {
                if (clockOffBeanDao == null) {
                    DaoMaster DaoMaster = new DaoMaster(getWritableDatabase());
                    DaoSession daoSession = DaoMaster.newSession();
                    clockOffBeanDao = daoSession.getClockOffBeanDao();
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
     * 打卡工具类
     */
    public static class ClockOffHelper {

    }
}
