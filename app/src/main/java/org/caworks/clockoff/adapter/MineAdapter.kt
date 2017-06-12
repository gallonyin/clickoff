package org.caworks.clockoff.adapter

import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import com.blankj.utilcode.util.TimeUtils
import org.caworks.clockoff.R
import org.caworks.clockoff.activity.TaskDetailActivity
import org.caworks.clockoff.entity.greendao.ClockOffBean
import org.caworks.clockoff.entity.greendao.DBManager
import org.caworks.library.base.SimpleAdapter
import org.caworks.library.base.ViewHolder
import org.caworks.library.util.CustomToast
import org.caworks.library.util.GLog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Gallon on 2017/6/7.
 */
class MineAdapter(context: Context, data: MutableList<ClockOffBean>, layoutId: Int) : SimpleAdapter<ClockOffBean>(context, data, layoutId) {

    override fun convert(holder: ViewHolder, bean: ClockOffBean, position: Int) {
        holder.run {
            setText(R.id.tv_name, bean.name)
            getView<Button>(R.id.bt_clock_off).setOnClickListener { confirmClockOff(bean) }
            itemView.setOnClickListener { TaskDetailActivity.enterActivity(mContext, bean) }
        }
    }

    /**
     * 确认打卡对话框
     */
    private fun confirmClockOff(bean: ClockOffBean) {
        val builder = AlertDialog.Builder(mContext).apply {
            setTitle("提示")
            setMessage("今天已经完成了该任务？")
            setPositiveButton("确认打卡", { dialog, which ->
                val today = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyyMMdd", Locale.getDefault()))
                val records = bean.record.split("##")
                GLog.e("today: " + today)
                GLog.e("records: " + records)
                if (today.equals(records[records.size - 1])) {
                    CustomToast.showToast(mContext, "今天已经打卡")
                } else {
                    CustomToast.showToast(mContext, "打卡成功")
                    bean.record += "##${today}"
                    bean.complete = (bean.complete.toInt() + 1).toString()
                    DBManager.getInstance(mContext).updateclockOffBean(bean)
                }
            })
            setNegativeButton("我再等等", { dialog, which -> CustomToast.showToast(mContext, "已取消") })
        }
    }

}