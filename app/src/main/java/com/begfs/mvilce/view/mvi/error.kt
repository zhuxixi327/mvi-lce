package com.begfs.mvilce.view.mvi

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * 错误分类：
 * 1 开发者看的错误
 * 2 用户看的错误
 * */
enum class ErrorType(code : Int) {
    NO_NETWORK(1000), SOCKET(1001), HTTP(1002), PARSE(1003), OTHER(1004)
}

data class Errored(val type : ErrorType, val message: String, val throwable: Throwable)

object ErrorHandle {
    fun parse(throwable: Throwable): Errored {

        return when(throwable){
            is HttpException
            -> Errored(ErrorType.HTTP, "HTTP", throwable)

            is ConnectException, is SocketTimeoutException, is UnknownHostException, is SSLHandshakeException
            -> Errored(ErrorType.SOCKET, "SOCKET", throwable)

            is JsonParseException, is JSONException
            -> Errored(ErrorType.PARSE, "PARSE", throwable)

            else -> Errored(ErrorType.OTHER, "OTHER", throwable)
        }
    }


    fun <S> handleError(r: Req, failure: Throwable, viewHandler : VView<S>){
        val e = parse(failure)
        when (e.type) {
            ErrorType.NO_NETWORK -> {
                viewHandler.showError(r, ErrorViewType.TOAST, e.message, failure)
            }
            ErrorType.SOCKET -> {
            }
            ErrorType.HTTP -> {
            }
            ErrorType.PARSE -> {
            }
            ErrorType.OTHER -> {
            }
        }
    }
}
