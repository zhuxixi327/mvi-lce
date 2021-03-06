package com.begfs.mvilce.view.helloworld

import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.LCEHelper
import com.begfs.mvilce.view.mvi.LoadingStyle
import com.begfs.mvilce.view.mvi.RxHelper
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

object HelloWorldRepo {

    fun getHelloWorldText(): Observable<LCE<ZResult<Any>>> {
        return Observable
                .just(getRandomMessage())
                .delay(1, TimeUnit.SECONDS)
                .map { LCEHelper.success<Any>(it) }
                .compose(RxHelper.loadingErrorTransformer(LoadingStyle.PROGRESS_BAR, ""))
    }

     fun getRandomMessage(): HelloDTO {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
            .map { HelloDTO(it) }
        return messages[Random().nextInt(messages.size)]
    }
}