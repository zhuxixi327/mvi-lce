package com.begfs.mvilce.view.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.begfs.mvilce.adt.ZResult

//view层用户的意图
interface IntentTo

//view层对presenter返回的消息的监听，返回的数据类型是：LCE<ZResult<C>>, C是正确返回的Content的类型
interface LCEListener : MvpView {
    fun onLCE(lce: LCE<ZResult<Any>>)
}

//view 和 presenter的交互接口，包含用户发出的意图和repo返回的数据
interface VPExchange: IntentTo, LCEListener