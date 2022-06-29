package com.ougi.passwordscreenimpl.presentation.view


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import com.ougi.passwordscreenimpl.R


class OtpEditText : AppCompatEditText {

    private val density by lazy { context.resources.displayMetrics.density }

    private var horizontalLineSpacing = 24 //space between the lines
    private var numChars = 4
    private var verticalLineSpacing = 8 //height of the text from lines
    private var onClickListener: OnClickListener? = null

    private var lineStroke = 1f
    private var lineStrokeSelected = 2f

    private lateinit var linesPaint: Paint
    private lateinit var textPaint: TextPaint
    private lateinit var textEmptyPaint: TextPaint

    private var textWidths = FloatArray(4)

    private var _primaryColor: Int? = null
    private val primaryColor: Int
        get() = requireNotNull(_primaryColor)
    private var _secondaryColor: Int? = null
    private val secondaryColor: Int
        get() = requireNotNull(_secondaryColor)

    var onInputFinished: ((String) -> Unit)? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet, defStyleAttr: Int = 0) {
        val intDensity = density.toInt()
        lineStroke *= intDensity
        lineStrokeSelected *= intDensity
        horizontalLineSpacing *= intDensity //convert to pixels for our density
        verticalLineSpacing *= intDensity //convert to pixels for our density

        setColorsFromAttrs(attrs, defStyleAttr)
        returnTextColor()
        setBackgroundResource(0)
        setMaxTextLength(attrs)
        addTextWatcher()

        setOnClickListener { view ->
            setOnClick(view)
        }
    }

    private fun returnTextColor() {
        val density = context.resources.displayMetrics.density
        setTextPaintParams(density)
        setTextEmptyPaintParams()
        setLinesPaintParams()
    }

    private fun setColorsFromAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        val attrsArray =
            context.obtainStyledAttributes(attrs, R.styleable.OtpEditText, defStyleAttr, 0)
        _primaryColor =
            attrsArray.getColor(R.styleable.OtpEditText_primaryColor, R.attr.primaryColor)
        _secondaryColor =
            attrsArray.getColor(R.styleable.OtpEditText_secondaryColor, R.attr.secondaryColor)
        attrsArray.recycle()
    }

    private fun setTextPaintParams(displayDensity: Float) {
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            density = displayDensity
            style = Paint.Style.FILL
            textSize = this@OtpEditText.textSize
            color = primaryColor
        }
    }

    private fun setTextEmptyPaintParams() {
        textEmptyPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            textSize = this@OtpEditText.textSize
            strokeWidth = lineStrokeSelected
            color = primaryColor
        }
    }

    private fun setLinesPaintParams() {
        linesPaint = Paint(paint).apply {
            strokeWidth = lineStroke
        }
    }

    private fun setMaxTextLength(attrs: AttributeSet) {
        val maxLength = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, MAX_LENGTH, 4)
        textWidths = FloatArray(maxLength)
        numChars = maxLength
    }

    private fun addTextWatcher() {
        addTextChangedListener { editable ->
            returnTextColor()
            if (editable?.length == numChars) {
                onInputFinished?.let { it(editable.toString().trim()) }
            }
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        onClickListener = listener
    }

    private fun setOnClick(view: View) {
        setSelection(text?.length ?: 0)
        onClickListener?.onClick(view)
    }


    override fun onDraw(canvas: Canvas) {
        showSoftInput()

        val availableWidth: Int = width - paddingRight - paddingLeft
        val charSize: Int =
            if (horizontalLineSpacing < 0) availableWidth / (numChars * 2 - 1)
            else (availableWidth - horizontalLineSpacing * (numChars - 1)) / numChars

        var startX = paddingLeft.toFloat()
        val bottom = (height - paddingBottom).toFloat()

        val text = text
        val textLength = text!!.length
        paint.getTextWidths(getText(), 0, textLength, textWidths)

        val passwordInputType = isPasswordInputType(inputType)
        for (charNumber in 0..numChars) {
            updateLinesColor(charNumber == textLength)
            val middle = startX + charSize / 2
            val radius = (charSize / 8).toFloat()
            if (passwordInputType) {
                canvas.drawCircle(
                    middle,
                    (bottom - verticalLineSpacing) / 2,
                    radius,
                    textEmptyPaint
                )
            } else
                canvas
                    .drawLine(startX, bottom, startX + charSize, bottom, linesPaint)

            if (getText()!!.length > charNumber) {
                if (passwordInputType)
                    canvas.drawCircle(
                        middle,
                        (bottom - verticalLineSpacing) / 2,
                        radius,
                        textPaint
                    )
                else {
                    val x = middle - textWidths[charNumber] / 2
                    val y = bottom - verticalLineSpacing
                    canvas.drawText(text, charNumber, charNumber + 1, x, y, textPaint)
                }
            }

            startX +=
                if (horizontalLineSpacing < 0) (charSize * 2)
                else (charSize + horizontalLineSpacing)
        }
    }

    private fun showSoftInput() {
        requestFocus()
        val imm: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }


    private fun isPasswordInputType(inputType: Int): Boolean {
        val variation =
            inputType and (EditorInfo.TYPE_MASK_CLASS or EditorInfo.TYPE_MASK_VARIATION)
        return (variation == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                || variation == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD
                || variation == EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD)
    }

    private fun updateLinesColor(next: Boolean) {
        linesPaint.apply {
            if (isFocused) {
                strokeWidth = lineStrokeSelected
                color = secondaryColor
                if (next)
                    color = primaryColor
            } else {
                strokeWidth = lineStroke
                color = primaryColor
            }
        }
    }

    fun updateTextColor(@ColorRes newColorRes: Int) {
        val newColor = context.getColor(newColorRes)
        textPaint.apply {
            strokeWidth = lineStrokeSelected
            color = newColor
        }
        textEmptyPaint.apply {
            strokeWidth = lineStrokeSelected
            color = newColor
        }
        linesPaint.apply {
            strokeWidth = lineStrokeSelected
            color = newColor
        }
    }

    fun clearText() {
        val textPaintColor = textPaint.color
        val textEmptyPaintColor = textEmptyPaint.color
        val linesPaintColor = linesPaint.color
        text = null
        textPaint.apply {
            strokeWidth = lineStrokeSelected
            color = textPaintColor
        }
        textEmptyPaint.apply {
            strokeWidth = lineStrokeSelected
            color = textEmptyPaintColor
        }
        linesPaint.apply {
            strokeWidth = lineStrokeSelected
            color = linesPaintColor
        }
    }

    companion object {
        private const val MAX_LENGTH = "maxLength"
        private const val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    }
}