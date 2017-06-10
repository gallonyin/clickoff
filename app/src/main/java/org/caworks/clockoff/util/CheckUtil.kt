package org.caworks.clockoff.util

import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Gallon on 2017/6/10.
 */
public class CheckUtil {

    /**
     * 判断日期是否在未来
     */
    companion object {
        fun isOvertime(year: Int, monthOfYear: Int, dayOfMonth: Int): Boolean {
            val split = TimeUtils.date2String(Date(System.currentTimeMillis()), SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
                    .split("-")
            val todayYear = split[0].toInt()
            val todayMonth = split[1].toInt()
            val todayDay = split[2].toInt()
            if (todayYear < year) return false
            if (todayYear > year) return true
            if (todayMonth < monthOfYear) return false
            if (todayMonth > monthOfYear) return true
            if (todayDay < dayOfMonth) return false
            if (todayDay > dayOfMonth) return true
            return false
        }
    }
}