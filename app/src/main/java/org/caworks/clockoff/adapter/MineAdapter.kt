package org.caworks.clockoff.adapter

import android.content.Context
import org.caworks.clockoff.R
import org.caworks.clockoff.activity.TaskDetailActivity
import org.caworks.clockoff.entity.greendao.ClockOffBean
import org.caworks.library.base.SimpleAdapter
import org.caworks.library.base.ViewHolder

/**
 * Created by Gallon on 2017/6/7.
 */
class MineAdapter(context: Context, data: MutableList<ClockOffBean>, layoutId: Int) : SimpleAdapter<ClockOffBean>(context, data, layoutId) {

    override fun convert(holder: ViewHolder, bean: ClockOffBean, position: Int) {
        holder.setText(R.id.tv_name, bean.name)
        holder.itemView.setOnClickListener { TaskDetailActivity.enterActivity(mContext, bean) }
    }

}