package com.begfs.mvilce.view.mvi

import io.reactivex.functions.Consumer
import java.util.NoSuchElementException
import com.begfs.mvilce.adt.ZResult
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

//loading的样式
enum class LoadingStyle { PROGRESS_DIALOG, PROGRESS_BAR }

// Loading的类型
data class Loading(val style: LoadingStyle, val message: String)

/**
 * 错误分类：
 * 1 开发者看的错误
 * 2 用户看的错误
 * */
enum class ErrorType(code : Int) {
    NO_NETWORK(1000), SOCKET(1001), HTTP(1002), PARSE(1003), OTHER(1004)
}

data class Errored(val type : ErrorType, val message: String, val throwable: Throwable)


/***
 * LCE<C>: Loading Content Error模式, C是正确时返回数据的类型
 * 实质是Either<Loading, ZResult<C>>，其性质：
 * 1 Loading和Result<C>互斥
 * 2 ZResult<C>可包含正确和错误两种情况，正确内容C和错误的Throwable互斥
 */
sealed class LCE<C> {
    companion object {  //生产LCE的便利工具

        fun <C> loading(type: LoadingStyle, message: String) : LCE<ZResult<C>> = LCELoading(Loading(type, message))

        fun <C> success(c: C): LCE<ZResult<C>> = LCEResult(ZResult.success(c))

        fun <C> failure(e: Throwable): LCE<ZResult<C>> = LCEResult(ZResult.failure(e))
    }


    abstract fun getLoading(): Loading
    abstract fun getResult(): ZResult<C>

    abstract fun isLoading(): Boolean
    abstract fun isResult(): Boolean

    abstract fun onLoadingOrResult(loading: Consumer<Loading>, result: Consumer<ZResult<C>>)
}

class LCELoading<C> constructor(var loadingValue: Loading) : LCE<C>() {

    override fun getLoading() = loadingValue

    override fun getResult(): ZResult<C> {
        throw NoSuchElementException("Tried to getResult from a LCELoading")
    }

    override fun isLoading() = true
    override fun isResult() = false

    override fun onLoadingOrResult(loading: Consumer<Loading>, result: Consumer<ZResult<C>>) {
        loading.accept(this.loadingValue)
    }
}

class LCEResult<C> constructor(var resultValue: C) : LCE<C>() {

    override fun getLoading(): Loading {
        throw NoSuchElementException("Tried to getLoading from a LCEResult")
    }

    override fun getResult() = ZResult.success(resultValue)

    override fun isLoading() = false
    override fun isResult() = true

    override fun onLoadingOrResult(loading: Consumer<Loading>, result: Consumer<ZResult<C>>) {
        result.accept(getResult())
    }
}

object ErrorHandle {
    private fun parse(throwable: Throwable): Errored {

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
                viewHandler.showError(r, ErrorStyle.TOAST, e.message, failure)
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
