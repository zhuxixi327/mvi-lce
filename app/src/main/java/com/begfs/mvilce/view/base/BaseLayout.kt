//package com.begfs.mvilce.view.base
//
//import android.content.Context
//import android.util.AttributeSet
//import com.begfs.mvilce.view.mvi.Errored
//import com.begfs.mvilce.view.mvi.*
//import com.hannesdorfmann.mosby3.mvi.MviPresenter
//import com.hannesdorfmann.mosby3.mvi.layout.MviFrameLayout
//import com.hannesdorfmann.mosby3.mvi.layout.MviLinearLayout
//import com.hannesdorfmann.mosby3.mvi.layout.MviRelativeLayout
//import io.reactivex.functions.Consumer
//
//abstract class BaseLinearLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
//    : MviLinearLayout<V, P>, VPExchange, VView {
//
//    constructor(context: Context) : super(context) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
//        context,
//        attrs,
//        defStyleAttr,
//        defStyleRes
//    ) {
//        initViewLocal(context)
//    }
//
//    private fun initViewLocal(context: Context) {
//        viewModel = initViewMode()
//        initView(context)
//    }
//
//    abstract fun initView(context: Context)
//
//    private lateinit var viewModel : S
//
//    abstract fun initViewMode() : S
//
//    override fun onLCE(rr: ReqRes) {
//        val lce = rr.data
//
//        lce.onLoadingOrResult(
//            Consumer { var1 -> onLoading(rr.req to var1) },
//            Consumer { //Result
//                    result -> result.onFailureOrSuccess(
//                Consumer { failure -> onFailure(rr.req to failure) },
//                Consumer { //Success
//                        success -> run {
//                    viewModel = onReduce(viewModel, rr.req to success.result())
//                    onRender(viewModel)
//                }
//                }
//            )
//            }
//        )
//    }
//
//    // loading handle template
//    open fun onLoading(pair: Pair<Req, Loading>) {
//
//    }
//
//    // error handle template
//    open fun onFailure(pair:  Pair<Req, Throwable>) {
//        Errored.handleError(this, pair.second)
//    }
//
//    /**child should call super.showError*/
//    override fun showError(type : ErrorViewType, message : String, throwable: Throwable){
//
//    }
//    /**
//     * onReduce: (s, t) -> new s
//     * */
//    abstract fun onReduce(vm: S, pair: Pair<Req, Any>) : S
//
//    abstract fun onRender(vm: S)
//}
//
//abstract class BaseFrameLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
//    : MviFrameLayout<V, P>, VPExchange, VView {
//
//    constructor(context: Context) : super(context) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
//        context,
//        attrs,
//        defStyleAttr,
//        defStyleRes
//    ) {
//        initViewLocal(context)
//    }
//
//    private fun initViewLocal(context: Context) {
//        viewModel = initViewMode()
//        initView(context)
//    }
//
//    abstract fun initView(context: Context)
//
//    private lateinit var viewModel : S
//
//    abstract fun initViewMode() : S
//
//    override fun onLCE(rr: ReqRes) {
//        val lce = rr.data
//
//        lce.onLoadingOrResult(
//            Consumer { var1 -> onLoading(rr.req to var1) },
//            Consumer { //Result
//                    result -> result.onFailureOrSuccess(
//                Consumer { failure -> onFailure(rr.req to failure) },
//                Consumer { //Success
//                        success -> run {
//                    viewModel = onReduce(viewModel, rr.req to success.result())
//                    onRender(viewModel)
//                }
//                }
//            )
//            }
//        )
//    }
//
//    // loading handle template
//    open fun onLoading(pair: Pair<Req, Loading>) {
//
//    }
//
//    // error handle template
//    open fun onFailure(pair:  Pair<Req, Throwable>) {
//        Errored.handleError(this, pair.second)
//    }
//
//    /**child should call super.showError*/
//    override fun showError(type : ErrorViewType, message : String, throwable: Throwable){
//
//    }
//    /**
//     * onReduce: (s, t) -> new s
//     * */
//    abstract fun onReduce(vm: S, pair: Pair<Req, Any>) : S
//
//    abstract fun onRender(vm: S)
//}
//
//abstract class BaseRelativeLayout<V : VPExchange, P : MviPresenter<V, ReqRes>, S: Any>
//    : MviRelativeLayout<V, P>, VPExchange, VView {
//
//    constructor(context: Context) : super(context) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initViewLocal(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
//        context,
//        attrs,
//        defStyleAttr,
//        defStyleRes
//    ) {
//        initViewLocal(context)
//    }
//
//    private fun initViewLocal(context: Context) {
//        viewModel = initViewMode()
//        initView(context)
//    }
//
//    abstract fun initView(context: Context)
//
//    private lateinit var viewModel : S
//
//    abstract fun initViewMode() : S
//
//    override fun onLCE(rr: ReqRes) {
//        val lce = rr.data
//
//        lce.onLoadingOrResult(
//            Consumer { var1 -> onLoading(rr.req to var1) },
//            Consumer { //Result
//                    result -> result.onFailureOrSuccess(
//                Consumer { failure -> onFailure(rr.req to failure) },
//                Consumer { //Success
//                        success -> run {
//                    viewModel = onReduce(viewModel, rr.req to success.result())
//                    onRender(viewModel)
//                }
//                }
//            )
//            }
//        )
//    }
//
//    // loading handle template
//    open fun onLoading(pair: Pair<Req, Loading>) {
//
//    }
//
//    // error handle template
//    open fun onFailure(pair:  Pair<Req, Throwable>) {
//        Errored.handleError(this, pair.second)
//    }
//
//    /**child should call super.showError*/
//    override fun showError(type : ErrorViewType, message : String, throwable: Throwable){
//
//    }
//    /**
//     * onReduce: (s, t) -> new s
//     * */
//    abstract fun onReduce(vm: S, pair: Pair<Req, Any>) : S
//
//    abstract fun onRender(vm: S)
//}
