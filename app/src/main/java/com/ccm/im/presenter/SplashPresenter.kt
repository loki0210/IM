package com.ccm.im.presenter

import com.ccm.im.contract.SplashContract
import com.hyphenate.chat.EMClient

class SplashPresenter(val view: SplashContract.View) : SplashContract.Presenter {
    override fun checkLoginStatus() {
        if (isLoginIn()) view.onLoggedIn() else view.onNotLoginIn()
    }

    //判断是否登录到环信的服务器
    private fun isLoginIn(): Boolean =
        EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
}