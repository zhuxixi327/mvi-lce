package com.begfs.mvilce.view.helloworld

import com.begfs.mvilce.repo.HelloWorldRepo
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.VPExchange
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.MVIHelper

enum class HelloIntentType{
    SAY_HELLO, SAY_WORLD
}

interface HelloWorldExchange : VPExchange {
    fun requestSayHelloWorld(): Observable<Pair<Any, Unit>>
}

//绑定HelloWorldExchange，说明Presenter绑定了view和presenter
class HelloWorldPresenter : MviBasePresenter<HelloWorldExchange, Pair<Any, LCE<ZResult<Any>>> >() {
    override fun bindIntents() {
        val helloWorldReact: Observable<Pair<Any, LCE<ZResult<Any>>>> = intent(HelloWorldExchange::requestSayHelloWorld)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS)
            .flatMap { MVIHelper.mapResult(it.first, HelloWorldRepo.getHelloWorldText()) }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(helloWorldReact, HelloWorldExchange::onLCE)
    }
}