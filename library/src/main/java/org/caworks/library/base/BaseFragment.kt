package org.caworks.library.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Gallon on 2017/6/1.
 */
abstract class BaseFragment : Fragment() {

    var viewRoot: View? = null
    lateinit var mContext: Context

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewRoot != null)
            return viewRoot
        viewRoot = View.inflate(mContext, getLayoutResId(), null)
        initView(viewRoot!!)
        return viewRoot
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (viewRoot?.parent as ViewGroup).removeView(viewRoot)
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView(viewRoot: View)
}