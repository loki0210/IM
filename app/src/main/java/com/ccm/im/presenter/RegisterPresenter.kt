package com.ccm.im.presenter

import com.ccm.im.contract.RegisterContract
import com.ccm.im.extentions.isValidPassword
import com.ccm.im.extentions.isValidUserName

class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {
    override fun register(username: String, password: String, confirmPassword: String) {
        if (username.isValidUserName()) {
            if (password.isValidPassword()) {
                if (confirmPassword == password) {
                    view.onStartRegister()
                } else view.onConfirmPasswordError()
            } else view.onPasswordError()
        } else view.onUserNameError()
    }
}