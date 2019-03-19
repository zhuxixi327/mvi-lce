package com.begfs.mvilce.view.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.begfs.mvilce.adt.ZResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * 带label的数据
 * */
data class Labeled<T>(val label: Any, val data: T)
typealias LabeledLCE = Labeled<LCE<ZResult<Any>>>

data class Req<T>(val id: Any, val data: T)

data class ReqRes<ReqT, ResT>(val req: Req<ReqT>, val data: ResT)

//view层用户的意图
interface IntentTo

//view层对presenter返回的消息的监听，返回的数据类型是：LCE<ZResult<C>>, C是正确返回的Content的类型
interface LCEListener : MvpView {
    fun onLCE(pair: LabeledLCE)
}

//view 和 presenter的交互接口，包含用户发出的意图和repo返回的数据
interface VPExchange: IntentTo, LCEListener

object MVIHelper {
//    fun <T> mapIntent( intentType: Any, ot : T) : Observable<Labeled<T>> {
//
//        val biFunction = object : BiFunction<Any, T, Labeled<T>> {
//            override fun apply(t1: Any, t2: T): Labeled<T> { return  Labeled(t1, t2)}
//        }
//
//        return Observable.just(intentType).zipWith(Observable.just(ot), biFunction)
//    }

    fun  mapResult(it: Any, result: Observable<LCE<ZResult<Any>>>)
            : Observable<LabeledLCE> {

        val biFunction = object :BiFunction<Any, LCE<ZResult<Any>>, LabeledLCE> {
            override fun apply(t1: Any, t2: LCE<ZResult<Any>>): LabeledLCE {
                return LabeledLCE(label = t1, data = t2)
            }
        }

        return Observable.combineLatest(Observable.just(it), result, biFunction)
    }

}