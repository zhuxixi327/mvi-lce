package com.begfs.mvilce.repo

import com.begfs.mvilce.adt.ZResult
import com.begfs.mvilce.view.helloworld.HelloDTO
import com.begfs.mvilce.view.mvi.LCE
import com.begfs.mvilce.view.mvi.LoadingStyle
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

object HelloWorldRepo {

    fun getHelloWorldText(): Observable<LCE<ZResult<Any>>> {

        return Observable
                .just(getRandomMessage())
                .delay(1, TimeUnit.SECONDS)
                .map { LCE.success<Any>(it) }
                .startWith ( LCE.loading(LoadingStyle.PROGRESS_BAR, "") )
                .onErrorReturn { LCE.failure(it) }
    }


     fun getRandomMessage(): HelloDTO {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
            .map { HelloDTO(it) }
        return messages[Random().nextInt(messages.size)]
    }
}