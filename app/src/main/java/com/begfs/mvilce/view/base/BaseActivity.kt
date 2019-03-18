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
import com.begfs.mvilce.view.mvi.LabeledLCE

abstract class BaseActivity<V : VPExchange, P : MviPresenter<V, LabeledLCE>, S: Any>
    : MviActivity<V, P>(), VPExchange {

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    @LayoutRes
    abstract fun layoutId(): Int

    open fun beforeLayout(activity: Activity) {}
    open fun initView(savedInstanceState: Bundle?) {}
    open fun initData(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = initViewMode()

        beforeLayout(this)
        setContentView(layoutId())
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

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