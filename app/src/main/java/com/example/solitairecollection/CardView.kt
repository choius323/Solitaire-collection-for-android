package com.example.solitairecollection

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

@SuppressLint("ViewConstructor")
class CardView @JvmOverloads constructor(
    mContext: Context,
    number: Int,
    shape: Int,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    var isFront: Boolean = true
) : androidx.appcompat.widget.AppCompatImageView(mContext, attrs, defStyle), View.OnTouchListener {

    val card: Card = Card(number, shape)
    var imageName = "${card.getShapeName()}_${card.number}"
    var image = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    var mouseX = 0f // 클릭 위치
    var mouseY = 0f
    var isMoveStart = false

    init {
        adjustViewBounds = true
        maxWidth = dpToPx(context, 40f)
        setImage()
    }

    fun setCard(number: Int, shape: Int) {
        card.setCard(number, shape)
        setImage()
    }

    fun setIsFront(boolean: Boolean) {
        isFront = boolean
        setImage()
    }

    private fun setImage() {
        if (isFront) {
            imageName = "${card.getShapeName()}_${card.number}"
            image = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        } else {
            imageName = "card_back"
            image = R.drawable.card_back
        }
        setImageResource(image)
    }

    private fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}
