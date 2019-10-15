package com.ccm.im

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.ccm.im.base.BaseActivity
import com.itheima.updatelib.PatchUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.io.File

class MainActivity : BaseActivity() {
    //懒加载初始化
    private val mDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    //增量更新
    fun upDate(view: View) {
        var pm: PackageManager = packageManager
        val appInfo = pm.getApplicationInfo("com.ss.android", 0)
        //获取旧版本apk的路径
        var oldPath: String = appInfo.sourceDir

        //指定patch文件的保存路径
        var patchFile = File(Environment.getExternalStorageDirectory(), "toutiao.patch")

        //生成新版本,设置新版本保存路径
        var newApkFile = File(Environment.getExternalStorageDirectory(), "toutiao.apk")

        mDialog.show()
        //合并文件
        //子线程耗时操作
        doAsync {

            val result = PatchUtil.patch(oldPath, newApkFile.absolutePath, patchFile.absolutePath)
            if (result == 0) {
                //合并成功
                runOnUiThread {
                    mDialog.dismiss()
                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.setDataAndType(
                        Uri.parse("file://" + newApkFile.absoluteFile),
                        "application/vnd.android.package-archive"
                    )
                    startActivity(intent)
                }
            }

        }
    }
}
