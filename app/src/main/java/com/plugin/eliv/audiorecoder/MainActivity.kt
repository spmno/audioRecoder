package com.plugin.eliv.audiorecoder

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.plugin.eliv.recoderlibrary.AudioEngine
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1024
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()

        AudioEngine.getInstance().init(this)
        val waveFilePath = getExternalFilesDir(null)?.absolutePath + "/wave.wav";

        audioSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                AudioEngine.getInstance().enableSystemAEC()
            else
                AudioEngine.getInstance().disableSystemAEC()
        }

        recode.setOnClickListener {
            AudioEngine.getInstance().startRecord(waveFilePath)
            Toast.makeText(this, "开始录音", Toast.LENGTH_SHORT).show()
        }


        stopRecode.setOnClickListener {
            AudioEngine.getInstance().stopRecord()
            Toast.makeText(this, "停止录音", Toast.LENGTH_SHORT).show()
        }

        play.setOnClickListener {
            AudioEngine.getInstance().startPlay(waveFilePath)
        }

        stopPlay.setOnClickListener {
            AudioEngine.getInstance().stopPlay()
        }
    }

    private fun requestPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            }
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), REQUEST_CODE)
        }
    }
}
