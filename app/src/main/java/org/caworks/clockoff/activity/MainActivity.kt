package org.caworks.clockoff.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Menu
import org.caworks.library.base.BaseFragment
import org.caworks.clockoff.Base.MineFragment
import org.caworks.clockoff.R
import org.caworks.library.base.BaseActivity
import java.util.ArrayList

class MainActivity : BaseActivity() {

    val fragments = ArrayList<BaseFragment>()

    companion object {
        /**
         * 进入 MainActivity 的入口
         */
        fun enterActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val vp_container = findViewById(R.id.vp_container) as ViewPager
        vp_container.adapter = object: FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
    }
}
