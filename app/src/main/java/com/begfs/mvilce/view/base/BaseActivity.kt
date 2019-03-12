package com.begfs.mvilce.view.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer
import com.begfs.mvilce.adt.ZResult

abstract class BaseActivity<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>>
    : MviActivity<V, P>(), VPExchange {

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

    override fun onLCE(lce: LCE<ZResult<Any>>) {
        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(var1) },
            Consumer {
                var1 -> var1.onFailureOrSuccess(
                    Consumer { failure -> onFailure(failure) },
                    Consumer { result -> onSuccess(result) }
                )
            }
        )
    }

    // loading handle template
    open fun onLoading(loading: Loading) {

    }

    // error handle template
    open fun onFailure(e: Throwable) {

    }

    abstract fun onSuccess(result: ZResult<Any>)

}