package de.thkoeln.simpleprogressbar

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.simple_progressbar.view.*

private const val PROGRESS_DEFAULT = 50
private const val SECONDARY_DEFAULT = 70
private const val MAX_DEFAULT = 100

class SimpleProgressBar(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var progress : Int = PROGRESS_DEFAULT
        set(value) {
            field = if(value > maxProgress) maxProgress else value
            updateProgress(field, progress_primary)
            updateText()
        }

    var secondaryProgress: Int = SECONDARY_DEFAULT
        set(value) {
            field = if(value > maxProgress) maxProgress else value
            updateProgress(field, progress_secondary)
        }

    var maxProgress : Int = MAX_DEFAULT
        set(value) { field = value; draw() }

    private var radius : Float = 0f
    private var viewWidth = 0
    private var padding = 0

    private val backgroundDrawable = GradientDrawable()
    private val progressDrawable = GradientDrawable()
    private val secondaryDrawable = GradientDrawable()

    var bgColor = -1
        set(value) { field = value; backgroundDrawable.setColor(value) }

    var progressColor = -1
        set(value) { field = value; progressDrawable.setColor(value) }

    var secondaryColor = -1
        set(value) { field = value; secondaryDrawable.setColor(value) }


    init{

        LayoutInflater.from(context).inflate(R.layout.simple_progressbar, this)

        with(context!!.theme.obtainStyledAttributes(attrs, R.styleable.SimpleProgressBar, 0, 0)){
            try {
                radius = getDimension(R.styleable.SimpleProgressBar_corner_radius, 0f)

                val defaultBgColor = ContextCompat.getColor(context, R.color.backgroundDefault)
                bgColor = getInteger(R.styleable.SimpleProgressBar_background_color, defaultBgColor)

                val defaultProgressColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                progressColor = getInteger(R.styleable.SimpleProgressBar_progress_color, defaultProgressColor)

                val defaultSecondaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
                secondaryColor = getInteger(R.styleable.SimpleProgressBar_secondary_progress_color, defaultSecondaryColor)

                padding = getDimensionPixelSize(R.styleable.SimpleProgressBar_padding, 0)

            } finally {
                recycle()
            }
        }
        setup()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        postDelayed({ draw(); }, 15)
    }

    private fun setup(){

        backgroundDrawable.setColor(bgColor)
        backgroundDrawable.cornerRadius = radius
        progress_background.background = backgroundDrawable

        progressDrawable.setColor(progressColor)
        progressDrawable.cornerRadius = radius
        progress_primary.background = progressDrawable

        secondaryDrawable.setColor(secondaryColor)
        secondaryDrawable.cornerRadius = radius
        progress_secondary.background = secondaryDrawable

        val progressParams = progress_primary.layoutParams as MarginLayoutParams
        progressParams.setMargins(padding, padding, padding, padding)

        val secondaryParams = progress_secondary.layoutParams as MarginLayoutParams
        secondaryParams.setMargins(padding, padding, padding, padding)
    }

    private fun draw(){
        updateProgress(progress, progress_primary)
        updateProgress(secondaryProgress, progress_secondary)
        invalidate(); requestLayout()
        updateText()
    }

    private fun updateProgress(newProgress : Int, affectedView : View){
        val ratio = newProgress / maxProgress.toFloat()
        val progressWidth = (viewWidth * ratio) - (padding * 2)
        if(progressWidth.toInt() <= 0){
            affectedView.visibility = View.GONE
        } else{
            affectedView.visibility = View.VISIBLE
        }
        val params = affectedView.layoutParams
        params.width = progressWidth.toInt()
        affectedView.layoutParams = params
    }

    private fun updateText(){ progress_text.text = "$progress/$maxProgress" }
}