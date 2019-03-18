package com.begfs.mvilce.view.base

import android.os.Bundle
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer

abstract class BaseFragment<V : VPExchange, P : MviPresenter<V, Pair<Any, LCE<ZResult<Any>>>>, S: Any>
    : MviFragment<V, P>(), VPExchange {

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewMode()
    }



    override fun onLCE(pair: Pair<Any, LCE<ZResult<Any>>>) {
        val type = pair.first
        val lce = pair.second

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