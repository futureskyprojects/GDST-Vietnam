package vn.vistark.qrinfoscanner.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.ViewfinderView
import vn.vistark.qrinfoscanner.R

class CustomViewFinderView(context: Context?, attrs: AttributeSet?) :
    ViewfinderView(context, attrs) {
    private var scanLineTop = 0
    private var scanLightHeight = 180
    private var isDrawTopToBottom = true
    var onHaveBottomPosition: ((Float) -> Unit)? = null

    override fun onDraw(canvas: Canvas?) {

        refreshSizes()
        if (framingRect == null || previewFramingRect == null) {
            return
        }

        paint.color = if (paint != null) resultColor else maskColor

        val frame = framingRect
        val previewFrame = previewFramingRect

        val frameTop = frame.top.toFloat()
        val frameBottom = frame.bottom.toFloat()
        val frameLeft = frame.left.toFloat()
        val frameRight = frame.right.toFloat()

        onHaveBottomPosition?.invoke(frame.bottom.toFloat())

        val width = width.toFloat()
        val height = height.toFloat()

        canvas?.apply {
            drawRect(0f, 0f, width, frameTop, paint)
            drawRect(0f, frameTop, frameLeft, frameBottom + 1, paint)
            drawRect(frameRight + 1, frameTop, width, frameBottom + 1, paint)
            drawRect(0f, frameBottom + 1, width, height, paint)
        }

        canvas?.let { canvas ->
            if (resultBitmap != null) {
                paint.alpha = CURRENT_POINT_OPACITY
                canvas.drawBitmap(resultBitmap, null, frame, paint)
            } else {
                //Vẽ viền
                drawFrameBounds(canvas, frame)
                //vẽ đường scan
                drawScanLight(canvas, frame)

                val scaleX = frame.width() / previewFrame.width()
                val scaleY = frame.height() / previewFrame.height()

                val currentPossible = possibleResultPoints
                val currentLast = lastPossibleResultPoints

                if (currentPossible.isEmpty()) {
                    lastPossibleResultPoints = null
                } else {
                    possibleResultPoints = ArrayList<ResultPoint>(5)
                    lastPossibleResultPoints = currentPossible

                    paint.apply {
                        alpha = CURRENT_POINT_OPACITY
                        color = resultPointColor
                    }
                    currentPossible.forEach { point ->
                        canvas.drawCircle(
                            frameLeft + (point.x * scaleX),
                            frameTop + (point.y * scaleY),
                            POINT_SIZE.toFloat(), paint
                        )
                    }
                }

                currentLast?.let {
                    paint.apply {
                        alpha = CURRENT_POINT_OPACITY / 2
                        color = resultPointColor
                    }

                    val radius = POINT_SIZE / 2.0f

                    currentLast.forEach { point ->
                        canvas.drawCircle(
                            frameLeft + (point.x * scaleX),
                            frameTop + (point.y * scaleY),
                            radius, paint
                        )
                    }
                }

                //vẽ lại scanline
                postInvalidateDelayed(
                    2L,
                    frameLeft.toInt() - POINT_SIZE,
                    frameTop.toInt() - POINT_SIZE,
                    frameRight.toInt() + POINT_SIZE,
                    frameBottom.toInt() + POINT_SIZE
                )
            }
        }
    }

    /**
     * Vẽ đường viền khung - 4 góc của khung quét
     *
     * @param canvas
     * @param frame
     */
    private fun drawFrameBounds(canvas: Canvas, frame: Rect) {

        val frameTop = frame.top.toFloat()
        val frameBottom = frame.bottom.toFloat()
        val frameLeft = frame.left.toFloat()
        val frameRight = frame.right.toFloat()

        paint.color = Color.parseColor("#ffffff")
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        //chiều dài/rộng của các góc
        val width: Int = frame.width()
        val corLength = (width * 0.07).toInt()
        var corWidth = (corLength * 0.2).toInt()
        corWidth = if (corWidth > 15) 15 else corWidth

        canvas.apply {
            //trên - trái
            drawRect(frameLeft - corWidth, frameTop, frameLeft, frameTop + corLength, paint)
            drawRect(
                frameLeft - corWidth,
                frameTop - corWidth,
                frameLeft + corLength,
                frameTop,
                paint
            )
            // trên - phải
            drawRect(frameRight, frameTop, frameRight + corWidth, frameTop + corLength, paint)
            drawRect(
                frameRight - corLength,
                frameTop - corWidth,
                frameRight + corWidth,
                frameTop,
                paint
            )
            // dưới - trái
            drawRect(frameLeft - corWidth, frameBottom - corLength, frameLeft, frameBottom, paint)
            drawRect(
                frameLeft - corWidth,
                frameBottom,
                frameLeft + corLength,
                frameBottom + corWidth,
                paint
            )
            // dưới - phải
            drawRect(frameRight, frameBottom - corLength, frameRight + corWidth, frameBottom, paint)
            drawRect(
                frameRight - corLength,
                frameBottom,
                frameRight + corWidth,
                frameBottom + corWidth,
                paint
            )
        }
    }

    /**
     * Vẽ đường quét
     *
     * @param canvas
     * @param frame
     */
    val SCAN_VELOCITY = 10 // tốc độ của của scan line
    private fun drawScanLight(canvas: Canvas, frame: Rect) {
        paint.color = laserColor
        paint.alpha = 250

        if (scanLineTop <= 0) {
            scanLineTop = frame.top
        }

        if (isDrawTopToBottom)
            drawTopToBottom(canvas, frame)
        else
            drawBottomToTop(canvas, frame)
    }

    fun drawTopToBottom(canvas: Canvas, frame: Rect) {
        val scanLight: Bitmap =
            BitmapFactory.decodeResource(resources, R.drawable.img_scan_line_big_effect)
        if (scanLineTop >= frame.bottom + scanLightHeight) {
            // Chuyển sang chế độ vẽ từ tưới lên trên
            isDrawTopToBottom = false
            scanLineTop = frame.bottom
            drawBottomToTop(canvas, frame)
            return
        } else {
            scanLineTop += SCAN_VELOCITY // thay đổi vị trí của scan line
        }
        val scanRect = Rect(
            frame.left,
            calBoudaryOfScanLight(frame.top, frame.bottom, scanLineTop),
            frame.right,
            calBoudaryOfScanLight(
                frame.top,
                frame.bottom,
                scanLineTop + calScanLightHeight(frame.top, frame.bottom)
            )
        )
        canvas.drawBitmap(scanLight, null, scanRect, paint)
    }

    fun drawBottomToTop(canvas: Canvas, frame: Rect) {
        val scanLight: Bitmap =
            BitmapFactory.decodeResource(resources, R.drawable.img_scan_line_big_effect_rotate)
        if (scanLineTop <= frame.top - scanLightHeight) {
            // Chuyển sang chế độ vẽ từ tưới lên trên
            isDrawTopToBottom = true
            scanLineTop = frame.top - scanLightHeight
            drawTopToBottom(canvas, frame)
            return
        } else {
            scanLineTop -= SCAN_VELOCITY // thay đổi vị trí của scan line
        }
        val scanRect = Rect(
            frame.left,
            calBoudaryOfScanLight(frame.top, frame.bottom, scanLineTop),
            frame.right,
            calBoudaryOfScanLight(
                frame.top,
                frame.bottom,
                scanLineTop + calScanLightHeight(frame.top, frame.bottom)
            )
        )
        canvas.drawBitmap(scanLight, null, scanRect, paint)
    }

    private fun calBoudaryOfScanLight(top: Int, bottom: Int, currentValue: Int): Int {
        if (currentValue in top..bottom)
            return currentValue
        else if (currentValue < top)
            return top
        else if (currentValue > bottom)
            return bottom
        else return currentValue
    }

    private fun calScanLightHeight(frameTop: Int, frameBottom: Int): Int {
        var scanLinghtHeightResult = 0
        if (isDrawTopToBottom) {
            if (scanLineTop <= frameTop + scanLightHeight) {
                scanLinghtHeightResult = scanLineTop - frameTop
            }
        } else {
            if (scanLineTop >= frameBottom - scanLightHeight) {
                scanLinghtHeightResult = frameBottom - scanLineTop
            }
        }
        scanLinghtHeightResult = scanLightHeight

        if (isDrawTopToBottom && scanLinghtHeightResult + scanLineTop >= frameBottom) {
            scanLinghtHeightResult = frameBottom - scanLineTop
        }

        if (!isDrawTopToBottom && scanLineTop <= frameTop) {
            scanLinghtHeightResult = (scanLineTop + scanLightHeight) - frameTop
        }

        if (scanLinghtHeightResult < 0)
            scanLinghtHeightResult == 0

        if (scanLinghtHeightResult > scanLightHeight) {
            scanLinghtHeightResult = scanLightHeight
        }

        if (scanLinghtHeightResult == 0)
            scanLinghtHeightResult = 2

        return scanLinghtHeightResult
    }
}