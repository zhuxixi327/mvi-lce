package com.begfs.mvilce.view.base

import android.content.Context
import android.util.AttributeSet
import com.begfs.mvilce.view.mvi.*
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvi.layout.MviFrameLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviLinearLayout
import com.hannesdorfmann.mosby3.mvi.layout.MviRelativeLayout

abstract class BaseLinearLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviLinearLayout<V, P>, VPExchange, VView<S> {

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
        initView(context)
    }

    abstract fun initView(context: Context)

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

abstract class BaseFrameLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviFrameLayout<V, P>, VPExchange, VView<S> {

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
        initView(context)
    }

    abstract fun initView(context: Context)

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

abstract class BaseRelativeLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
    : MviRelativeLayout<V, P>, VPExchange, VView<S> {

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
        initView(context)
    }

    abstract fun initView(context: Context)

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
