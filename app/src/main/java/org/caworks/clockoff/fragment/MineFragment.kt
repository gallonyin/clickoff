package org.caworks.clockoff.Base

import android.view.View
import org.caworks.clockoff.R
import org.caworks.clockoff.entity.greendao.ClockOffBean
import org.caworks.clockoff.entity.greendao.DBManager
import org.caworks.library.util.GLog
import java.util.ArrayList

/**
 * Created by Gallon on 2017/6/1.
 */
class MineFragment : BaseFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(viewRoot: View) {
        viewRoot.findViewById(R.id.bt_insert).setOnClickListener { insert() }
        viewRoot.findViewById(R.id.bt_query).setOnClickListener { query() }
    }

    fun insert() {
        GLog.e()
        val clockOffBeanList = ArrayList<ClockOffBean>()
        val clockOffBean = ClockOffBean(null, "测试名称", "测试描述", "", "开始时间", "提醒时间", "记录时间", 1, 1)
        clockOffBeanList.add(clockOffBean)

        DBManager.getInstance(mContext).insertclockOffBeanList(clockOffBeanList, true)
    }

    fun query() {
        GLog.e()
        val queryclockOffBeanList1 = DBManager.getInstance(mContext).queryclockOffBeanList("测试名称")
        GLog.e(queryclockOffBeanList1)
        val queryclockOffBeanList = DBManager.getInstance(mContext).queryclockOffBeanList()
        GLog.e(queryclockOffBeanList)
    }

}