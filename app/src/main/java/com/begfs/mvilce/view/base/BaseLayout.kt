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

abstract class BaseLinearLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>, S: Any>
    : MviLinearLayout<V, P>, VPExchange {

    constructor(context: Context) : super(context) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initViewLocal(context)
    }

    private fun initViewLocal(context: Context) {
        viewModel = initViewMode()
        initView(context)
    }

    abstract fun initView(context: Context)

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, content: Any) : S

    abstract fun onRender(vm: S)

    override fun onLCE(lce: LCE<ZResult<Any>>) {
        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }
}

abstract class BaseFrameLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>, S: Any>
    : MviFrameLayout<V, P>, VPExchange {

    constructor(context: Context) : super(context) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initViewLocal(context)
    }

    private fun initViewLocal(context: Context) {
        viewModel = initViewMode()
        initView(context)
    }

    abstract fun initView(context: Context)

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, content: Any) : S

    abstract fun onRender(vm: S)

    override fun onLCE(lce: LCE<ZResult<Any>>) {
        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }
}

abstract class BaseRelativeLayout<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>, S: Any>
    : MviRelativeLayout<V, P>, VPExchange {

    constructor(context: Context) : super(context) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViewLocal(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initViewLocal(context)
    }

    private fun initViewLocal(context: Context) {
        viewModel = initViewMode()
        initView(context)
    }

    abstract fun initView(context: Context)

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, content: Any) : S

    abstract fun onRender(vm: S)

    override fun onLCE(lce: LCE<ZResult<Any>>) {
        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    open fun onLoading(loading: Loading) {

    }

    // loading handle template
    open fun onFailure(e: Throwable) {

    }
}
