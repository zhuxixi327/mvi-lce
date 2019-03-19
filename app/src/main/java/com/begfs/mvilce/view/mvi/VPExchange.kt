package com.begfs.mvilce.view.mvi

import com.begfs.mvilce.adt.ZResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

//view 和 presenter的交互接口，包含用户发出的意图和repo返回的数据
interface VPExchange: IntentTo, LCEListener

object VPHelper {

    fun  combineLatest(rq: Req, result: Observable<LCE<ZResult<Any>>>) : Observable<ReqRes> {

        val biFunction = object :BiFunction<Req, LCE<ZResult<Any>>, ReqRes> {
            override fun apply(t1: Req, t2: LCE<ZResult<Any>>): ReqRes {
                return ReqRes(req = t1, data = t2)
            }
        }

        return Observable.combineLatest(Observable.just(rq), result, biFunction)
    }

}