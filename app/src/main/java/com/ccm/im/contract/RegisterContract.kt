package com.ccm.im.contract

import com.ccm.im.base.BasePresenter

class RegisterContract {
    interface Presenter : BasePresenter {
        fun register(username: String, password: String, confirmPassword: String)
    }

    interface View {
        fun onUserNameError()
        fun onPasswordError()
        fun onConfirmPasswordError()
        fun onStartRegister()
        fun onRegisterSuccess()
        fun onRegisterFailed()
        fun onUserExist()
    }
}