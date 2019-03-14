package com.begfs.mvilce.view.base

import android.os.Bundle
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer

abstract class BaseFragment<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>, S: Any>
    : MviFragment<V, P>(), VPExchange {

    private lateinit var viewModel : S

    abstract fun initViewMode() : S

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewMode()
    }

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