package com.streets.myframework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bounds = this.windowManager.currentWindowMetrics.bounds
        val centerX = bounds.exactCenterX()

        setContentView(
            MyView(this) {
                listOf(
                    Boxes.SmallBox(centerX, 300f, Color.RED),
                    Boxes.MediumBox(centerX, 700f, Color.GREEN),
                    Boxes.LargeBox(centerX, 1300f, Color.BLACK)
                )
            }.ActualView()
        )
    }
}

class MyView(private val context: Context, private val drawable: () -> List<MyBoxes>) {

    inner class ActualView: View(context) {
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val rectForDrawing = drawable()

            rectForDrawing.forEach {
                canvas.drawRect(it.box, it.mColor)
            }
        }
    }
}

object Boxes {
    data class SmallBox(val x: Float, val y: Float, val color: Int? = null) : MyBoxes(x, y, color) {
        override val box: RectF
            get() = RectF(
                x - SMALL_BOX_OFFSET,
                y + SMALL_BOX_OFFSET,
                x + SMALL_BOX_OFFSET,
                y - SMALL_BOX_OFFSET
            )
    }

    data class MediumBox(val x: Float, val y: Float, val color: Int? = null) : MyBoxes(x, y, color) {
        override val box: RectF
            get() = RectF(
                x - MEDIUM_BOX_OFFSET,
                y + MEDIUM_BOX_OFFSET,
                x + MEDIUM_BOX_OFFSET,
                y - MEDIUM_BOX_OFFSET
            )
    }

    data class LargeBox(val x: Float, val y: Float, val color: Int? = null) : MyBoxes(x, y, color) {
        override val box: RectF
            get() = RectF(
                x - LARGE_BOX_OFFSET,
                y + LARGE_BOX_OFFSET,
                x + LARGE_BOX_OFFSET,
                y - LARGE_BOX_OFFSET
            )
    }

    private const val SMALL_BOX_OFFSET = 100f
    private const val MEDIUM_BOX_OFFSET = 200f
    private const val LARGE_BOX_OFFSET = 300f
}

abstract class MyBoxes(x: Float, y: Float, private val color: Int? = null) {
//    val position: Pair<Float, Float> = Pair(x, y)
    val mColor: Paint = Paint().apply { color = this@MyBoxes.color ?: Color.MAGENTA }
    abstract val box: RectF
}