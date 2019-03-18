package com.begfs.mvilce.view.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.begfs.mvilce.adt.ZResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

//view层用户的意图
interface IntentTo

//view层对presenter返回的消息的监听，返回的数据类型是：LCE<ZResult<C>>, C是正确返回的Content的类型
interface LCEListener : MvpView {
    fun onLCE(pair: Pair<Any, LCE<ZResult<Any>>>)
}

//view 和 presenter的交互接口，包含用户发出的意图和repo返回的数据
interface VPExchange: IntentTo, LCEListener

object MVIHelper {
    fun <T> mapIntent( intentType: Any, ot : T) : Observable<Pair<Any, T>> {

        val biFunction = object : BiFunction<Any, T, Pair<Any, T>> {
            override fun apply(t1: Any, t2: T): Pair<Any, T> { return  t1 to t2}
        }

        return Observable.just(intentType).zipWith(Observable.just(ot), biFunction)
    }

    fun  mapResult(it: Any, result: Observable<LCE<ZResult<Any>>>)
            : Observable<Pair<Any, LCE<ZResult<Any>>>> {

        val biFunction = object :BiFunction<Any, LCE<ZResult<Any>>, Pair<Any, LCE<ZResult<Any>>>> {
            override fun apply(t1: Any, t2: LCE<ZResult<Any>>): Pair<Any, LCE<ZResult<Any>>> {
                return t1 to t2
            }
        }

        return Observable.combineLatest(Observable.just(it), result, biFunction)
    }

}