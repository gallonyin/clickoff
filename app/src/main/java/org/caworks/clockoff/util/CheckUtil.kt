package org.caworks.clockoff.util

import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Gallon on 2017/6/10.
 */
public class CheckUtil {

    companion object {
        /**
         * 判断日期是否在未来 true在未来 false在过去或今天
         */
        fun isOvertime(year: Int, monthOfYear: Int, dayOfMonth: Int): Boolean {
            val split = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
                    .split("-")
            val todayYear = split[0].toInt()
            val todayMonth = split[1].toInt()
            val todayDay = split[2].toInt()
            if (year < todayYear) return false
            if (year > todayYear) return true
            if (monthOfYear + 1 < todayMonth) return false
            if (monthOfYear + 1 > todayMonth) return true
            if (dayOfMonth < todayDay) return false
            if (dayOfMonth > todayDay) return true
            return false
        }

        /**
         * 分割字符串 tv_start 例：xx年xx月xx日
         */
        fun splitDate(date: String): IntArray {
            val sb = StringBuilder()
            var split = IntArray(3)
            date.forEach {
                if (it == '年') {
                    split[0] = sb.toString().toInt()
                    sb.setLength(0)
                } else if (it == '月') {
                    split[1] = sb.toString().toInt()
                    sb.setLength(0)
                } else if (it == '日') {
                    split[2] = sb.toString().toInt()
                    sb.setLength(0)
                } else {
                    sb.append(it)
                }
            }
            return split
        }
    }
}