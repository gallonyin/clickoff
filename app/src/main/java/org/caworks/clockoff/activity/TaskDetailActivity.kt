package org.caworks.clockoff.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.CalendarView
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

    companion object {
        lateinit var clockOffBean: ClockOffBean
        fun enterActivity(context: Context, bean: ClockOffBean) {
            val intent = Intent(context, TaskDetailActivity::class.java)
            clockOffBean = bean
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        CustomToast.showToast(MyApp.APP, "name: " + clockOffBean.name)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun initView() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)

        val et_name = findViewById(R.id.et_name) as EditText
        val et_desc = findViewById(R.id.et_desc) as EditText
        val et_note = findViewById(R.id.et_note) as EditText
        val tv_start = findViewById(R.id.tv_start) as TextView
        tv_start.setOnClickListener { startTime() }
        val et_remind = findViewById(R.id.et_remind) as EditText
        val et_record = findViewById(R.id.et_record) as EditText
        val et_complete = findViewById(R.id.et_complete) as EditText
        val et_repair = findViewById(R.id.et_repair) as EditText
        val tv_save = findViewById(R.id.tv_save) as TextView
        val tv_cancel = findViewById(R.id.tv_cancel) as TextView
        tv_save.setOnClickListener {
            clockOffBean.name = et_name.text.toString()
            clockOffBean.desc = et_desc.text.toString()
            clockOffBean.note = et_note.text.toString()
            clockOffBean.start = tv_start.text.toString()
            clockOffBean.remind = et_remind.text.toString()
            clockOffBean.record = et_record.text.toString()
            clockOffBean.complete = et_complete.text.toString().toInt() //需要选择时间
            clockOffBean.repair = et_repair.text.toString().toInt()
            DBManager.getInstance(mContext).updateclockOffBean(clockOffBean)
            CustomToast.showToast(mContext, "保存成功")
            onBackPressed()
        }
        tv_cancel.setOnClickListener { onBackPressed() }
    }

    private fun startTime() {
        val dialog = Dialog(this)
        val window = dialog.window
//        val calendarView = CalendarView(this)
//        calendarView.run {
//            setOnDateChangeListener { view, year, month, dayOfMonth ->
//                GLog.e("view: " + view)
//                GLog.e("year: " + year)
//                GLog.e("month: " + month)
//                GLog.e("dayOfMonth: " + dayOfMonth)
//            }
//        }
//        window.setContentView(calendarView)
        val split = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
                .split("-")
        val datePicker = DatePicker(this)
        datePicker.run {
            init(split[0].toInt(), split[1].toInt(), split[2].toInt(), { view, year, monthOfYear, dayOfMonth ->
                GLog.e(CheckUtil.isOvertime(year, monthOfYear, dayOfMonth))
                GLog.e("view: " + view)
                GLog.e("year: " + year)
                GLog.e("monthOfYear: " + monthOfYear)
                GLog.e("dayOfMonth: " + dayOfMonth)
            })
        }
        window.setContentView(datePicker)

        window.setLayout((MyApp.SCREEN_WIDTH * 0.9).toInt(), (MyApp.SCREEN_HEIGHT * 0.9).toInt())
        dialog.show()
    }
}
