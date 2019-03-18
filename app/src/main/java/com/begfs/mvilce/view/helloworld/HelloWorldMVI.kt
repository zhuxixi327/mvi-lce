package com.begfs.mvilce.view.helloworld

import com.begfs.mvilce.repo.HelloWorldRepo
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.*

enum class HelloIntentType{
    SAY_HELLO, SAY_WORLD
}

interface HelloWorldExchange : VPExchange {
    fun requestSayHelloWorld(): Observable<Labeled<Unit>>
}

//绑定HelloWorldExchange，说明Presenter绑定了view和presenter
class HelloWorldPresenter : MviBasePresenter<HelloWorldExchange, LabeledLCE >() {
    override fun bindIntents() {
        val helloWorldReact: Observable<LabeledLCE> = intent(HelloWorldExchange::requestSayHelloWorld)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap { MVIHelper.mapResult(it.label, HelloWorldRepo.getHelloWorldText()) }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(helloWorldReact, HelloWorldExchange::onLCE)

    }

}