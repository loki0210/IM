package com.ccm.im.presenter

import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.ccm.im.contract.RegisterContract
import com.ccm.im.extentions.isValidPassword
import com.ccm.im.extentions.isValidUserName

class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {
    override fun register(username: String, password: String, confirmPassword: String) {
        if (username.isValidUserName()) {
            if (password.isValidPassword()) {
                if (confirmPassword == password) {
                    view.onStartRegister()
                    registerBmob(username, password)
                } else view.onConfirmPasswordError()
            } else view.onPasswordError()
        } else view.onUserNameError()
    }

    private fun registerBmob(username: String, password: String) {
        val bu = BmobUser()
        bu.username = username
        bu.setPassword(password)
        bu.signUp<BmobUser>(object : SaveListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                if (p1 == null) {

                } else view.onRegisterFailed()
            }
        })
    }
}