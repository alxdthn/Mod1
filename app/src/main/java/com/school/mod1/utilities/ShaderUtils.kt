package com.school.mod1.utilities

import android.opengl.GLES20.*
import android.util.Log
import androidx.annotation.RawRes

class ShaderUtils(private val fileUtils: FileUtils) {

    fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = glCreateProgram()
        val linkStatus = IntArray(1)
        if (programId == GL_FALSE) return GL_FALSE
        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)
        glLinkProgram(programId)
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == GL_FALSE) {
            glDeleteProgram(programId)
            return GL_FALSE
        }
        return programId
    }

    fun createShader(type: Int, @RawRes shaderRawId: Int): Int {
        val shaderText = fileUtils.readTextFromRaw(shaderRawId)
        return createShader(type, shaderText)
    }

    fun createShader(type: Int, shaderText: String): Int {
        val compileStatus = IntArray(1)
        val shaderId = glCreateShader(type)
        if (shaderId == GL_FALSE) return GL_FALSE
        glShaderSource(shaderId, shaderText)
        glCompileShader(shaderId)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == GL_FALSE) {
            glDeleteShader(shaderId)
            return GL_FALSE
        }
        return shaderId
    }
}