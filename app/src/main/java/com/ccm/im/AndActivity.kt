package com.ccm.im

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_and.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AndActivity : AppCompatActivity() {
    private val textView: TextView by lazy {
        old_view
    }

    private val btn: Button by lazy {
        btn_pay
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_and)
        init()
    }

    fun init() {
        textView.text = "some things"
        btn.setOnClickListener {
            startActivity<WebViewActivity>()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}