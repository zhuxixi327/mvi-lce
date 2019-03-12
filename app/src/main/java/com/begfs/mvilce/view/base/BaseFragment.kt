package com.begfs.mvilce.view.base

import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.Loading
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import io.reactivex.functions.Consumer

abstract class BaseFragment<V : VPExchange, P : MviPresenter<V, LCE<ZResult<Any>>>>
    : MviFragment<V, P>(), VPExchange {

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