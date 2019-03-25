package com.begfs.mvilce.view.mvi

import io.reactivex.functions.Consumer

enum class ErrorStyle{
    DIALOG, TOAST
}

interface VView<S> {
    var viewModel : S

    fun showError(req: Req, style : ErrorStyle, message : String, throwable: Throwable)

    fun onLoading(pair: Pair<Req, Loading>)

    /**
     * onReduce: (s, t) -> new s
     * */
    fun onReduce(vm: S, pair: Pair<Req, Any>) : S

    fun onRender(vm: S)
}

object VHandler {
    fun <S> onLCE(rr: ReqRes, viewHandler : VView<S>) {
        val lce = rr.data

        lce.onLoadingOrResult(
            Consumer { var1 -> viewHandler.onLoading(rr.req to var1) },
            Consumer { //Result
                    result -> result.onFailureOrSuccess(
                        Consumer { ErrorHandle.handleError(rr.req, it, viewHandler) },
                        Consumer { //Success
                                success -> run {
                                    viewHandler.viewModel = viewHandler.onReduce(viewHandler.viewModel, rr.req to success.result())
                                    viewHandler.onRender(viewHandler.viewModel)
                                }
                        }
                    )
            }
        )
    }

}