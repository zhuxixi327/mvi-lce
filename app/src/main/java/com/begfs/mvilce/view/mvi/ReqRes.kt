package com.begfs.mvilce.view.mvi

import com.begfs.mvilce.adt.ZResult
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * client server 模式：view是client, presenter是server
 * request和response命令的交互，命令格式：
 * opcode opdata
 * */

/**request操作所操作的对象, 即opcode=IntentTo, opdata=Req*/
data class Req(val id: Any, val data: Any)
/**response操作所操作的对象, 即opcode=onLCE, opdata=ReqRes*/
data class ReqRes(val req: Req, val data: LCE<ZResult<Any>>)

/**view层用户的意图
 * 相当于request操作，request的内容是Res对象
 * */
interface IntentTo

//view层对presenter返回的消息的监听，返回的数据类型是：LCE<ZResult<C>>, C是正确返回的Content的类型
//相当于response操作，response的内容包含在ReqRes对象中
interface LCEListener : MvpView {
    fun onLCE(rr: ReqRes)
}