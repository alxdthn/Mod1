package com.school.mod1.gl_view

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.school.mod1.R
import com.school.mod1.utilities.ShaderUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ModGLRenderer(private val shaderUtils: ShaderUtils) : GLSurfaceView.Renderer {

	private var programId = 0
	private var uColorLocation = 0
	private var aPositionLocation = 0
	private val vertexData: FloatBuffer

	class Grid(private val xInfo: Info, private val yInfo: Info) {
		constructor(info: Info): this(info, info)

		val vertices: FloatArray

		init {
			vertices = initVertices()
		}

		private fun initVertices(): FloatArray {
			val size = xInfo.count * yInfo.count * 2
			val vertices = FloatArray(size)
			val xSide = xInfo.side()
			val ySide = yInfo.side()
			var y = yInfo.start
			var i = 0
			for (yIterator in 0 until yInfo.count) {
				var x = xInfo.start
				for (xIterator in 0 until xInfo.count) {
					vertices[i++] = x
					vertices[i++] = y
					vertices[i++] = x
					vertices[i++] = y + ySide
					vertices[i++] = x + xSide
					vertices[i++] = y
					x += xSide
				}
				y += ySide
			}
			return vertices
		}

		data class Info(val start: Float, val end: Float, val count: Int) {
			fun side(): Float {
				return (end - start) / (count - 1)
			}
		}
	}

	init {
		val grid = Grid(Grid.Info(-0.9f, 0.9f, 2))
		val vertices = grid.vertices
		vertexData = ByteBuffer.allocateDirect(vertices.size * SIZE_OF_FLOAT)
			.order(ByteOrder.nativeOrder())
			.asFloatBuffer()
		vertexData.put(vertices)
	}

	override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
		glClearColor(0f, 0f, 0f, 1f)
		shaderUtils.apply {
			val vertexShaderId = createShader(GL_VERTEX_SHADER, R.raw.vertex_shader)
			val fragmentShaderId = createShader(GL_FRAGMENT_SHADER, R.raw.fragment_shader)
			programId = createProgram(vertexShaderId, fragmentShaderId)
		}
		glUseProgram(programId)
		bindData()
	}

	override fun onDrawFrame(gl: GL10) {
		glDrawArrays(GL_TRIANGLES, 0, 3)
	}

	override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
		glViewport(0, 0, width, height)
	}

	private fun bindData() {
		uColorLocation = glGetUniformLocation(programId, U_COLOR)
		aPositionLocation = glGetAttribLocation(programId, A_POSITION)
		vertexData.position(0)
		glUniform4f(uColorLocation, 1f, 1f, 1f, 1f)
		glVertexAttribPointer(aPositionLocation, SIZE, GL_FLOAT, NORMALIZED, STRIDE, vertexData)
		glEnableVertexAttribArray(aPositionLocation)
	}

	companion object {
		const val U_COLOR = "u_Color"
		const val A_POSITION = "a_Position"
		const val SIZE = 2
		const val STRIDE = 0
		const val NORMALIZED = false
		const val SIZE_OF_FLOAT = 4
	}
}