package com.ccm.im

import android.os.Handler
import com.ccm.im.base.BaseActivity
import com.ccm.im.contract.SplashContract
import com.ccm.im.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity(), SplashContract.View {

    val presenter = SplashPresenter(this)

    companion object {
        const val DELAY = 2000L
    }

    private val handler by lazy {
        Handler()
    }

    override fun init() {
        super.init()
        presenter.checkLoginStatus()
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onNotLoginIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, DELAY)
    }

    override fun onLoggedIn() {
        startActivity<WebViewActivity>()
        finish()
    }
}

