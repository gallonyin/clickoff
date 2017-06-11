package org.caworks.clockoff.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import org.caworks.clockoff.MyApp
import org.caworks.clockoff.R
import org.caworks.clockoff.entity.greendao.ClockOffBean
import org.caworks.clockoff.entity.greendao.DBManager
import org.caworks.clockoff.util.CheckUtil
import org.caworks.library.base.BaseActivity
import org.caworks.library.util.CustomToast
import org.caworks.library.util.GLog
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailActivity : BaseActivity() {

    lateinit var et_name: EditText
    lateinit var et_desc: EditText
    lateinit var et_note: EditText
    lateinit var tv_start: TextView
    lateinit var et_remind: EditText
    lateinit var et_record: EditText
    lateinit var et_complete: EditText
    lateinit var et_repair: EditText
    lateinit var tv_save: TextView
    lateinit var tv_cancel: TextView

    companion object {
        var clockOffBean: ClockOffBean? = null
        fun enterActivity(context: Context, bean: ClockOffBean?) {
            val intent = Intent(context, TaskDetailActivity::class.java)
            clockOffBean = bean
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        CustomToast.showToast(MyApp.APP, "name: " + clockOffBean?.name)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        clockOffBean = null
    }

    private fun initView() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)

        et_name = findViewById(R.id.et_name) as EditText
        et_name.run {
            setText(clockOffBean?.name)
        }
        et_desc = findViewById(R.id.et_desc) as EditText
        et_note = findViewById(R.id.et_note) as EditText
        tv_start = findViewById(R.id.tv_start) as TextView
        tv_start.run {
            setOnClickListener { startTime() }
            val split = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).split("-")
            text = if (clockOffBean == null) "${split[0]}年${split[1]}月${split[2]}日" else clockOffBean!!.start
        }
        et_remind = findViewById(R.id.et_remind) as EditText
        et_record = findViewById(R.id.et_record) as EditText
        et_complete = findViewById(R.id.et_complete) as EditText
        et_repair = findViewById(R.id.et_repair) as EditText
        tv_save = findViewById(R.id.tv_save) as TextView
        tv_cancel = findViewById(R.id.tv_cancel) as TextView
        tv_save.setOnClickListener {
            if (clockOffBean == null) clockOffBean = ClockOffBean()
            clockOffBean!!.name = et_name.text.toString()
            clockOffBean!!.desc = et_desc.text.toString()
            clockOffBean!!.note = et_note.text.toString()
            clockOffBean!!.start = tv_start.text.toString()
            clockOffBean!!.remind = et_remind.text.toString()
            clockOffBean!!.record = et_record.text.toString()
            clockOffBean!!.complete = et_complete.text.toString() //需要选择时间
            clockOffBean!!.repair = et_repair.text.toString()
            if (clockOffBean!!.id == null) {
                DBManager.getInstance(mContext).insertClockOffBean(clockOffBean, true)
            } else {
                DBManager.getInstance(mContext).updateclockOffBean(clockOffBean)
            }
            CustomToast.showToast(mContext, "保存成功")
            onBackPressed()
        }
        tv_cancel.setOnClickListener { onBackPressed() }
    }

    private fun startTime() {
        val dialog = Dialog(this)
        val window = dialog.window
        val datePicker = DatePicker(this)
        datePicker.run {
            //            val split = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).split("-")
            val split = CheckUtil.splitDate(tv_start.text.toString())
            GLog.e(split)
            init(split[0].toInt(), split[1].toInt() - 1, split[2].toInt(), { view, year, monthOfYear, dayOfMonth ->
                if (CheckUtil.isOvertime(year, monthOfYear, dayOfMonth)) {
                    CustomToast.showToast(mContext, "开始时间不能超过今天啊喂！")
                    return@init
                }
                GLog.e("year: " + year)
                GLog.e("monthOfYear: " + monthOfYear)
                GLog.e("dayOfMonth: " + dayOfMonth)
                CustomToast.showToast(mContext, "选择成功 ${year}年${monthOfYear + 1}月${dayOfMonth}日")
                tv_start.setText("${year}年${monthOfYear + 1}月${dayOfMonth}日")
                dialog.dismiss()
            })
        }
        window.setContentView(datePicker)
        window.setLayout((MyApp.SCREEN_WIDTH * 0.9).toInt(), (MyApp.SCREEN_HEIGHT * 0.9).toInt())
        dialog.show()
    }
}
