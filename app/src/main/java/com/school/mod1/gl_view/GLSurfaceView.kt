package com.school.mod1.gl_view

import android.content.Context
import android.opengl.GLSurfaceView

class ModGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: ModGLRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = ModGLRenderer()
        setRenderer(renderer)
    }
}