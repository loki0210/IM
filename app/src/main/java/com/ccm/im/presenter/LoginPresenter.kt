package com.ccm.im.presenter

import com.ccm.im.adapter.EMCallBackAdapter
import com.ccm.im.contract.LoginContract
import com.ccm.im.extentions.isValidPassword
import com.ccm.im.extentions.isValidUserName
import com.hyphenate.chat.EMClient

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {
    override fun login(username: String, password: String) {
        if (username.isValidUserName()) {
            if (password.isValidPassword()) {
                //密码合法
                view.onStartLogin()
                loginEaseMob(username, password)
            } else view.onPasswordError()
        } else view.onUserNameError()
    }

    private fun loginEaseMob(username: String, password: String) {
        EMClient.getInstance().login(username, password, object : EMCallBackAdapter() {
            //在子线程回调的
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                //在主线程中通知主线程
                uiThread {
                    view.onLoginInSuccess()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                uiThread {
                    view.onLoginInFailed()
                }
            }

        })
    }
}