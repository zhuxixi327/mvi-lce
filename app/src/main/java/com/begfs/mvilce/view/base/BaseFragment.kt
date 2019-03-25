package com.begfs.mvilce.view.base

import android.os.Bundle
import com.begfs.mvilce.view.mvi.*
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter

abstract class BaseFragment<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviFragment<V, P>(), VPExchange, VView<S> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onLCE(rr: ReqRes) {
        VHandler.onLCE(rr, this)
    }

    // loading handle template
    override fun onLoading(pair: Pair<Req, Loading>) {

    }

    /**child should call super.showError*/
    override fun showError(req : Req, style : ErrorStyle, message : String, throwable: Throwable){

    }

}