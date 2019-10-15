package com.ccm.im.contract

import com.ccm.im.base.BasePresenter

class LoginContract {
    interface Presenter : BasePresenter {
        fun login(username: String, password: String)
    }

    interface View {
        fun onUserNameError()
        fun onPasswordError()
        fun onStartLogin()
        fun onLoginInSuccess()
        fun onLoginInFailed()
    }
}