package com.hbzhou.open.camera

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hbzhou.open.flowcamera.CustomCameraView.BUTTON_STATE_BOTH
import com.hbzhou.open.flowcamera.FlowCameraView
import com.hbzhou.open.flowcamera.listener.ClickListener
import com.hbzhou.open.flowcamera.listener.FlowCameraListener
import com.hbzhou.open.flowcamera.util.LogUtil
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flowCamera = findViewById<FlowCameraView>(R.id.flowCamera)
        flowCamera.saveToAlbum = false
        flowCamera.saveToPublic = false
        // 绑定生命周期 您就不用关心Camera的开启和关闭了 不绑定无法预览
        flowCamera.setBindToLifecycle(this)
        // 设置白平衡模式
//        flowCamera.setWhiteBalance(WhiteBalance.AUTO)
        // 设置只支持单独拍照拍视频还是都支持
        // BUTTON_STATE_ONLY_CAPTURE  BUTTON_STATE_ONLY_RECORDER  BUTTON_STATE_BOTH
        flowCamera.setCaptureMode(BUTTON_STATE_BOTH)
        // 开启HDR
//        flowCamera.setHdrEnable(Hdr.ON)
        // 设置最大可拍摄小视频时长 S
        flowCamera.setRecordVideoMaxTime(10)
        // 设置拍照或拍视频回调监听
        flowCamera.setFlowCameraListener(object : FlowCameraListener {
            // 录制完成视频文件返回
            override fun recordSuccess(file: File) {
                Log.d("MainActivity", "record success:" + file.absolutePath)
                Toast.makeText(this@MainActivity, file.absolutePath, Toast.LENGTH_SHORT).show()
                finish()
            }

            // 操作拍照或录视频出错
            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                LogUtil.e(
                    videoCaptureError.toString().plus("----").plus(message).plus("---").plus(
                        cause.toString()
                    )
                )
            }

            // 拍照返回
            override fun captureSuccess(file: File) {
                Toast.makeText(this@MainActivity, file.absolutePath, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
        //左边按钮点击事件
        flowCamera.setLeftClickListener(ClickListener { finish() })
    }
}
