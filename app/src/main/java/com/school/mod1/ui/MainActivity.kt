package com.school.mod1.ui

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.school.mod1.R
import com.school.mod1.gl_view.ModGLSurfaceView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = ModGLSurfaceView(applicationContext)
        setContentView(gLView)

        //sample_text.text = stringFromJNI()
    }

    override fun onPause() {
        super.onPause()
        gLView.onPause()
    }

    override fun onResume() {
        super.onResume()
        gLView.onResume()
    }

    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
