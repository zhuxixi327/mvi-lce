package com.begfs.mvilce.view.base

import android.content.Context
import android.util.AttributeSet
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.LabeledLCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvi.layout.MviFrameLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviLinearLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviRelativeLayout
import io.reactivex.functions.Consumer

abstract class BaseLinearLayout<V : VPExchange, P : MviPresenter<V, LabeledLCE>, S: Any>
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

    override fun onLCE(pair: LabeledLCE) {
        val type = pair.label
        val lce = pair.data

        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(type to var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(type to failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, type to success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    // loading handle template
    open fun onLoading(loadingPair: Pair<Any, Loading>) {

    }

    // error handle template
    open fun onFailure(ePair:  Pair<Any, Throwable>) {

    }
    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, contentPair: Pair<Any, Any>) : S

    abstract fun onRender(vm: S)
}

abstract class BaseFrameLayout<V : VPExchange, P : MviPresenter<V, LabeledLCE>, S: Any>
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

    override fun onLCE(pair: LabeledLCE) {
        val type = pair.label
        val lce = pair.data

        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(type to var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(type to failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, type to success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    // loading handle template
    open fun onLoading(loadingPair: Pair<Any, Loading>) {

    }

    // error handle template
    open fun onFailure(ePair:  Pair<Any, Throwable>) {

    }
    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, contentPair: Pair<Any, Any>) : S

    abstract fun onRender(vm: S)
}

abstract class BaseRelativeLayout<V : VPExchange, P : MviPresenter<V, LabeledLCE>, S: Any>
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

    override fun onLCE(pair: LabeledLCE) {
        val type = pair.label
        val lce = pair.data

        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(type to var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                Consumer { failure -> onFailure(type to failure) },
                Consumer { //Success
                        success -> run {
                    viewModel = onReduce(viewModel, type to success.result())
                    onRender(viewModel)
                }
                }
            )
            }
        )
    }

    // loading handle template
    open fun onLoading(loadingPair: Pair<Any, Loading>) {

    }

    // error handle template
    open fun onFailure(ePair:  Pair<Any, Throwable>) {

    }
    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, contentPair: Pair<Any, Any>) : S

    abstract fun onRender(vm: S)
}
