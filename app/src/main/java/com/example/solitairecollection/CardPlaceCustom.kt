package com.example.solitairecollection

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

@SuppressLint("Recycle")
class CardPlaceCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {
    private var textView: TextView
    private var imageView: ImageView

    init {
        val v = inflate(context, R.layout.card_place, this)
//        v.setBackgroundResource(R.drawable.card_place_shape)
//        Log.i("CardPlaceCustom context", "${context.javaClass}")
//        Log.i("CardPlaceCustom context2", "${ScorpionActivity::class.java}")
        imageView = v.findViewById(R.id.card_place_iv)
        textView = v.findViewById(R.id.card_place_tv)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CardPlaceCustom,
            0, 0
        ).apply {
            try {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardPlaceCustom)
                setTextVisible(typedArray.getBoolean(R.styleable.CardPlaceCustom_showText, true))
                setImageSrc(
                    typedArray.getResourceId(
                        R.styleable.CardPlaceCustom_image,
                        R.drawable.card_place_shape
                    )
                )
                setPadding(typedArray.getInt(R.styleable.CardPlaceCustom_padding, 4))
            } finally {
                recycle()
            }
        }
    }

    private fun setTextVisible(boolean: Boolean) {
        textView.visibility = if (boolean) View.VISIBLE else View.INVISIBLE
    }

    private fun setImageSrc(id: Int) {
        imageView.setImageResource(id)
    }

    private fun setPadding(padding: Int) {
        imageView.setPadding(padding, 0, padding, 0)
    }
}