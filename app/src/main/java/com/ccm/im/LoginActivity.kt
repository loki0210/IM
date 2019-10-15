package com.ccm.im

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.ccm.im.base.BaseActivity
import com.ccm.im.contract.LoginContract
import com.ccm.im.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), LoginContract.View {
    val presenter = LoginPresenter(this)
    override fun init() {
        super.init()
        login.setOnClickListener {
            loginIn()
        }
        btn_register.setOnClickListener {
            startActivity<RegisterActivity>()
        }
        password.setOnEditorActionListener { _, _, _ ->
            loginIn()
            true
        }
    }

    private fun loginIn() {
        //隐藏软键盘
        hideSoftKeyboard()
        if (hasWriteExternalStoragePermission()) {
            val usernameString = username.text.trim().toString()
            val passwordString = password.text.trim().toString()
            presenter.login(usernameString, passwordString)
        } else applyWriteExternalStoragePermission()


    }

    //动态权限申请
    private fun applyWriteExternalStoragePermission() {
        val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permission, 0)
    }

    private fun hasWriteExternalStoragePermission(): Boolean {
        val result =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_DENIED
    }

    override fun onUserNameError() {
        username.error = "用户名不合法"
    }

    override fun onPasswordError() {
        password.error = "密码不合法"
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgress("登录中")
    }

    override fun onLoginInSuccess() {
        dismissProgress()
        startActivity<WebViewActivity>()
        finish()
    }

    override fun onLoginInFailed() {
        dismissProgress()
        toast("登录失败")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //用户同意权限
            loginIn()
        } else toast("权限被拒绝")
    }

    override fun getLayoutResId(): Int = R.layout.activity_login
}