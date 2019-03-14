package com.begfs.mvilce.view.helloworld

import android.view.View
import com.begfs.mvilce.R
import com.begfs.mvilce.view.base.BaseActivity
import com.begfs.mvilce.view.mvi.Loading
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.activity_helloworld.*

class HelloWorldActivity : BaseActivity<HelloWorldExchange, HelloWorldPresenter>(), HelloWorldExchange {

    override fun layoutId() = R.layout.activity_helloworld

    override fun onLoading(loading: Loading) {
        super.onLoading(loading)
        helloWorldTextview.visibility = View.INVISIBLE
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun onSuccess(content: Any) {
        loadingIndicator.visibility = View.INVISIBLE

        with(helloWorldTextview) {
            visibility = View.VISIBLE
            text = content as HelloContent
        }
    }

    override fun createPresenter() = HelloWorldPresenter()

    override fun requestSayHelloWorld() = helloWorldButton.clicks()

}