package com.begfs.mvilce.view.base

import android.os.Bundle
import com.begfs.mvilce.view.mvi.*
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer

abstract class BaseFragment<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviFragment<V, P>(), VPExchange {

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewMode()
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

    }
    /**
     * onReduce: (s, t) -> new s
     * */
    abstract fun onReduce(vm: S, pair: Pair<Req, Any>) : S

    abstract fun onRender(vm: S)
}