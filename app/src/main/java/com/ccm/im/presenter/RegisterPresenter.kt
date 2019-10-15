package com.ccm.im.presenter

import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.ccm.im.contract.RegisterContract
import com.ccm.im.extentions.isValidPassword
import com.ccm.im.extentions.isValidUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync

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
            override fun done(p0: BmobUser, p1: BmobException?) {
                if (p1 == null) {
                    //注册到环信
                    registerEaseMob(username, password)
                } else {
                    if (p1.errorCode == 202) view.onUserExist()
                    else view.onRegisterFailed()
                }
            }
        })
    }

    private fun registerEaseMob(username: String, password: String) {
        doAsync {
            try {
                //注册失败会出现HyphenateException
                EMClient.getInstance().createAccount(username, password)
                //子线程到主线程
                uiThread {
                    view.onRegisterSuccess()
                }
            } catch (e: HyphenateException) {
                uiThread {

                    view.onRegisterFailed()
                }
            }
        }
    }
}