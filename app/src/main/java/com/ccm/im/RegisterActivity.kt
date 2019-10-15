package com.ccm.im

import android.widget.TextView
import com.ccm.im.base.BaseActivity
import com.ccm.im.contract.RegisterContract
import com.ccm.im.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity(), RegisterContract.View {
    val presenter = RegisterPresenter(this)
    override fun init() {
        super.init()
        btn_register.setOnClickListener { register() }
        password2.setOnEditorActionListener { _, _, _ ->
            register()
            true
        }
    }

    private fun register() {
        hideSoftKeyboard()
        val usernameString = username.text.trim().toString()
        val passwordString = password.text.trim().toString()
        val confirmPasswordString = password2.text.trim().toString()
        presenter.register(usernameString, passwordString, confirmPasswordString)
    }

    override fun onUserNameError() {
        username.error = "用户名不合法"
    }

    override fun onPasswordError() {
        password.error = "密码不合法"
    }

    override fun onConfirmPasswordError() {
        password2.error = "密码不合法"
    }

    override fun onStartRegister() {
        showProgress("正在注册")
    }

    override fun onRegisterSuccess() {
        dismissProgress()
        toast(R.string.register_success)
        finish()
    }

    override fun onRegisterFailed() {
        dismissProgress()
        toast("注册失败")
    }

    override fun onUserExist() {
        dismissProgress()
        toast(R.string.user_exist)
    }

    override fun getLayoutResId(): Int = R.layout.activity_register
}