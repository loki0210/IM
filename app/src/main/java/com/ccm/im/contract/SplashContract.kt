package com.ccm.im.contract

import com.ccm.im.base.BasePresenter

interface SplashContract {
    interface Presenter : BasePresenter {
        fun checkLoginStatus() //检查登录状态
    }

    interface View {
        fun onNotLoginIn() //未登录的
        fun onLoggedIn()    //已经登录的
    }
}