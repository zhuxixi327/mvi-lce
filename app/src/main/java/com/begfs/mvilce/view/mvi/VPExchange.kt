package com.begfs.mvilce.view.mvi

import com.begfs.mvilce.adt.ZResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

//view 和 presenter的交互接口，包含用户发出的意图和repo返回的数据
interface VPExchange: IntentTo, LCEListener

object RxHelper {

    fun  combineLatest(rq: Req, result: Observable<LCE<ZResult<Any>>>) : Observable<ReqRes> {
        return Observable.combineLatest(
            Observable.just(rq),
            result,
            BiFunction<Req, LCE<ZResult<Any>>, ReqRes> {
                t1, t2 -> ReqRes(t1, t2)
            })
    }

    fun loadingErrorTransformer(type: LoadingStyle, message: String) : ObservableTransformer<LCE<ZResult<Any>>, LCE<ZResult<Any>>> {
        return ObservableTransformer { upstream -> upstream
            .startWith(LCE.loading(type, message))
            .onErrorReturn { LCE.failure(it) }
        }
    }

}