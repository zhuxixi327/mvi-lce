package com.begfs.mvilce.view.helloworld

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.begfs.mvilce.view.mvi.*

enum class HelloIntentType{
    SAY_HELLO, SAY_WORLD
}

interface HelloWorldExchange : VPExchange {
    fun requestSayHelloWorld(): Observable<Req>
}

//绑定HelloWorldExchange，说明Presenter绑定了view和presenter
class HelloWorldPresenter : MviBasePresenter<HelloWorldExchange, ReqRes >() {
    override fun bindIntents() {
        val helloWorldReact: Observable<ReqRes> = intent(HelloWorldExchange::requestSayHelloWorld)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap { RxHelper.combineLatest(it, HelloWorldRepo.getHelloWorldText()) }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(helloWorldReact, HelloWorldExchange::onLCE)

    }

}