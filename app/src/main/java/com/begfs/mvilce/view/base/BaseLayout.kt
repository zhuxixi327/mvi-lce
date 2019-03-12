package com.begfs.mvilce.view.base

import android.content.Context
import android.util.AttributeSet
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvi.layout.MviFrameLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviLinearLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviRelativeLayout
import io.reactivex.functions.Consumer

abstract class BaseLinearLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>>
    : MviLinearLayout<V, P>, VPExchange {
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView(context)
    }

    abstract fun initView(context: Context)

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

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }

    abstract fun onSuccess(result: ZResult<Any>)
}

abstract class BaseFrameLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>> : MviFrameLayout<V, P>,
    VPExchange {
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView(context)
    }

    abstract fun initView(context: Context)

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

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }

    abstract fun onSuccess(result: ZResult<Any>)
}

abstract class BaseRelativeLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>> : MviRelativeLayout<V, P>,
    VPExchange {
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView(context)
    }

    abstract fun initView(context: Context)

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

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }

    abstract fun onSuccess(result: ZResult<Any>)
}
