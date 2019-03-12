package com.begfs.mvilce.adt

import io.reactivex.functions.Consumer
import java.util.NoSuchElementException
import io.reactivex.functions.Function

sealed class Either<L, R>{
    companion object {
        @JvmStatic
        fun <L, R> left(left: L): Either<L, R> {
            return Left(left)
        }
        @JvmStatic
        fun <L, R> right(right: R): Either<L, R> {
            return Right(right)
        }
    }

    abstract fun getLeft(): L
    abstract fun getRight(): R

    abstract fun isLeft(): Boolean
    abstract fun isRight(): Boolean

    abstract fun <T> fold(transformLeft: Function<L, T>, transformRight: Function<R, T>): T
    abstract fun <T, U> map(transformLeft: Function<L, T>, transformRight: Function<R, U>): Either<T, U>
    abstract fun runLR(runLeft: Consumer<L>, runRight: Consumer<R>)
}

class Left<L, R> constructor(var leftValue: L) : Either<L, R>() {

    override fun getLeft() = leftValue

    override fun getRight(): R {
        throw NoSuchElementException("Tried to getResult from a LCELoading")
    }

    override fun isLeft() = true
    override fun isRight() = false

    override fun <T> fold(transformLeft: Function<L, T>, transformRight: Function<R, T>): T {
        return transformLeft.apply(this.leftValue)
    }

    override fun <T, U> map(
        transformLeft: Function<L, T>,
        transformRight: Function<R, U>
    ): Either<T, U> {
        return Either.left(transformLeft.apply(this.leftValue))
    }

    override fun runLR(runLeft: Consumer<L>, runRight: Consumer<R>) {
        runLeft.accept(this.leftValue)
    }

    override fun hashCode(): Int {
        return this.leftValue.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Left<*, *>) {
            val otherAsLeft = other as Left<*, *>?
            return this.leftValue == otherAsLeft!!.leftValue
        } else {
            return false
        }
    }
}

class Right<L, R> constructor(var rightValue: R) : Either<L, R>() {

    override fun getLeft(): L {
        throw NoSuchElementException("Tried to getLoading from a LCEResult")
    }

    override fun getRight() = rightValue
    override fun isLeft() = false
    override fun isRight() = true

    override fun <T> fold(transformLeft: Function<L, T>, transformRight: Function<R, T>): T {
        return transformRight.apply(this.rightValue)
    }

    override fun <T, U> map(transformLeft: Function<L, T>, transformRight: Function<R, U>): Either<T, U> {
        return Either.right(transformRight.apply(this.rightValue))
    }

    override fun runLR(runLeft: Consumer<L>, runRight: Consumer<R>) {
        runRight.accept(this.rightValue)
    }


    override fun hashCode(): Int {
        return this.rightValue.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Right<*, *>) {
            val otherAsRight = other as Right<*, *>?
            this.rightValue == otherAsRight!!.rightValue
        } else {
            false
        }
    }
}