package com.ccm.im.app

import android.app.Application
import android.os.Environment
import cn.bmob.v3.Bmob
import com.alipay.euler.andfix.patch.PatchManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import java.io.File

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //AndFix()
        MyIm()
        MyMob()
    }

    private fun MyMob() {
        Bmob.initialize(applicationContext, "c276aefd3651c056a216493441295fb6")
    }

    //初始化环信
    private fun MyIm() {
        EMClient.getInstance().init(applicationContext, EMOptions())
        EMClient.getInstance().setDebugMode(true)
    }

    private fun AndFix() {
        //初始化阿里Andfix
        val patchManager = PatchManager(this)
        patchManager.init("1.0")
        patchManager.loadPatch()
        //设置布局文件路径
        var patchFile = File(Environment.getExternalStorageDirectory(), "fixbug.apatch")
        if (patchFile.exists()) {
            //合并
            patchManager.addPatch(patchFile.absolutePath)
        }
    }
}