package org.caworks.clockoff.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import org.caworks.library.base.BaseFragment
import org.caworks.clockoff.Base.MineFragment
import org.caworks.clockoff.MyApp
import org.caworks.clockoff.R
import org.caworks.clockoff.entity.greendao.ClockOffBean
import org.caworks.library.base.BaseActivity
import org.caworks.library.util.CustomToast
import java.util.ArrayList

class TaskDetailActivity : BaseActivity() {

    val fragments = ArrayList<BaseFragment>()

    companion object {
        var clockOffBean: ClockOffBean? = null
        fun enterActivity(context: Context, bean: ClockOffBean) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun initView() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
        fragments.add(MineFragment())
    }
}
