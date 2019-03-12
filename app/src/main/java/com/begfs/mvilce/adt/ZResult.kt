package com.begfs.mvilce.adt

import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

interface ExceptionThrowingFunction<T, R> {
    @Throws(Exception::class)
    fun apply(t: T): R
}

interface ExceptionThrowingSupplier<T> {
    @Throws(Exception::class)
    fun get(): T
}

/**
 * ZResult<R> = Result<R>
 * because it is hard to distinguish this Result with kotlin.Result
 * */
sealed class ZResult<R>{

    companion object {

        fun <R> attempt(resultSupplier: ExceptionThrowingSupplier<R>): ZResult<R> {
            return try {
                ZResult.success(resultSupplier.get())
            } catch (e: Exception) {
                ZResult.failure(e)
            }
        }

        fun <R> failure(e: Throwable): ZResult<R> = Failure(e)

        fun <R> success(result: R): ZResult<R> = Success(result)
    }

    abstract fun failure(): Throwable
    abstract fun result(): R

    abstract fun isFailure(): Boolean
    abstract fun isSuccess(): Boolean

    abstract fun <T> fold(transformException: Function<Throwable, T>, transformValue: Function<R, T>): T
    abstract fun <T> map(transformValue: ExceptionThrowingFunction<R, T>): ZResult<T>
    abstract fun <T> flatMap(transformValue: ExceptionThrowingFunction<R, ZResult<T>>): ZResult<T>

    abstract fun onFailureOrSuccess(onFailure: Consumer<Throwable>, onSuccess: Consumer<R>)
}

class Failure<R> constructor(private val failure: Throwable) : ZResult<R>() {
    override fun onFailureOrSuccess(onFailure: Consumer<Throwable>, onSuccess: Consumer<R>) {
        onFailure.accept(failure)
    }

    override fun failure(): Throwable {
        return this.failure
    }

    override fun result(): R {
        throw NoSuchElementException("Tried to getResult from an Failure")
    }

    override fun isFailure(): Boolean {
        return true
    }

    override fun isSuccess(): Boolean {
        return false
    }

    override fun <T> fold(transformException: Function<Throwable, T>, transformValue: Function<R, T>): T {
        return transformException.apply(this.failure)
    }

    override fun <T> map(transformValue: ExceptionThrowingFunction<R, T>): ZResult<T> {
        return ZResult.failure(this.failure)
    }

    override fun <T> flatMap(transformValue: ExceptionThrowingFunction<R, ZResult<T>>): ZResult<T> {
        return ZResult.failure(this.failure)
    }
}

class Success<R> constructor(private val content: R) : ZResult<R>() {
    override fun onFailureOrSuccess(onFailure: Consumer<Throwable>, onSuccess: Consumer<R>) {
        onSuccess.accept(content)
    }

    override fun failure(): Throwable {
        throw NoSuchElementException("Tried to getException from an Success")
    }

    override fun result(): R {
        return content
    }

    override fun isFailure(): Boolean {
        return false
    }

    override fun isSuccess(): Boolean {
        return true
    }

    override fun <T> fold(transformException: Function<Throwable, T>, transformValue: Function<R, T>): T {
        return transformValue.apply(this.content)
    }

    override fun <T> map(transformValue: ExceptionThrowingFunction<R, T>): ZResult<T> {
        return attempt( object : ExceptionThrowingSupplier<T>{
            override fun get(): T {
                return transformValue.apply(content)
            }
        } )
    }

    override fun <T> flatMap(transformValue: ExceptionThrowingFunction<R, ZResult<T>>): ZResult<T> {
        return try {
            transformValue.apply(this.content)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}