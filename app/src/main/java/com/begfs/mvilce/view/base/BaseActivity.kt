package com.begfs.mvilce.view.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.begfs.mvilce.error.ErrorView
import com.begfs.mvilce.error.ErrorViewType
import com.begfs.mvilce.error.Errored
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.Req
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer
import com.begfs.mvilce.view.mvi.ReqRes

abstract class BaseActivity<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviActivity<V, P>(), VPExchange, ErrorView {

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

    override fun onLCE(rr: ReqRes) {

        val lce = rr.data

        lce.onLoadingOrResult(
            Consumer { var1 -> onLoading(rr.req to var1) },
            Consumer { //Result
                result -> result.onFailureOrSuccess(
                    Consumer { failure -> onFailure(rr.req to failure) },
                    Consumer { //Success
                            success -> run {
                                viewModel = onReduce(viewModel, rr.req to success.result())
                                onRender(viewModel)
                            }
                    }
                )
            }
        )
    }

    // loading handle template
    open fun onLoading(pair: Pair<Req, Loading>) {

    }

    // error handle template
    open fun onFailure(pair:  Pair<Req, Throwable>) {
        Errored.handleError(this, pair.second)
    }

    /**child should call super.showError*/
    override fun showError(type : ErrorViewType, message : String, throwable: Throwable){

    }
    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, pair: Pair<Req, Any>) : S

    abstract fun onRender(vm: S)

}