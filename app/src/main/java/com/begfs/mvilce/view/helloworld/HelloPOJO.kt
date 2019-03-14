package com.begfs.mvilce.view.helloworld

//TODO implement model view onReduce pattern

data class HelloDTO(var content: String?)

data class HelloViewModel(var greeting: String)

fun HelloDTO?.toViewModel() : HelloViewModel {
    return HelloViewModel(this?.content?:"")

}
