package com.begfs.mvilce.view.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.begfs.mvilce.view.mvi.*
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter

abstract class BaseActivity<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviActivity<V, P>(), VPExchange, VView<S> {

    @LayoutRes
    abstract fun layoutId(): Int

    open fun beforeLayout(activity: Activity) {}
    open fun initView(savedInstanceState: Bundle?) {}
    open fun initData(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beforeLayout(this)
        setContentView(layoutId())
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onLCE(rr: ReqRes) {
        VHandler.onLCE(rr, this)
    }

    // loading handle template
    override fun onLoading(pair: Pair<Req, Loading>) {

    }

    /**child should call super.showError*/
    override fun showError(req : Req, style : ErrorStyle, message : String, throwable: Throwable){

    }

}