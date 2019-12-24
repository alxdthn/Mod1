package com.school.mod1.gl_view

import android.content.Context
import android.opengl.GLSurfaceView
import com.school.mod1.utilities.FileUtils
import com.school.mod1.utilities.ShaderUtils

class ModGLSurfaceView(appContext: Context) : GLSurfaceView(appContext) {

    private val renderer: ModGLRenderer

    init {
        val fileUtils = FileUtils(appContext)
        val shaderUtils = ShaderUtils(fileUtils)
        setEGLContextClientVersion(2)
        renderer = ModGLRenderer(shaderUtils)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}