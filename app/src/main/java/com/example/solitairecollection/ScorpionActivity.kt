package com.example.solitairecollection

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.view.allViews
import com.example.solitairecollection.databinding.ActivityScorpionBinding
import com.google.android.material.snackbar.Snackbar

class ScorpionActivity : SuperActivity() {

    private lateinit var binding: ActivityScorpionBinding
    private val cardPlaceArr = HashMap<CardPlaceCustom, CardPlace>()
    private val cards = ArrayList<CardView>(12)
    private var startPlacePosition = IntArray(2)
    private var waitTime = 0L
    private var score = 500

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScorpionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cardPlaceStart.post {
            val view = binding.cardPlaceStart
            view.getLocationOnScreen(startPlacePosition)
            cardPlaceArr += view to CardPlace(ArrayDeque(), 0, view, statusBarHeight)
            Log.i("startPlacePosition", startPlacePosition.contentToString())
        }

        for (view in binding.cardPlaceLayout2.allViews) {
//            val id = resources.getIdentifier("cardPlaceComplete" + (it + 1), "id", packageName)
//            val view = findViewById<CardPlaceCustom>(id)
            if (view is CardPlaceCustom) {
                Log.i("view class", "${view.id} ${view::class.java}")
                view.post {
                    cardPlaceArr += view to CardPlace(ArrayDeque(), 0, view, statusBarHeight)
                    // 상태바 끝나는 지점 : 75}
                }
            }
        }

        for (view in binding.cardPlaceLayout1.allViews) {
//            val id = resources.getIdentifier("cardPlaceComplete" + (it + 1), "id", packageName)
//            val view = findViewById<CardPlaceCustom>(id)
            if (view is CardPlaceCustom) {
                Log.i("view class", "${view.id} ${view::class.java}")
                view.post {
                    cardPlaceArr += view to CardPlace(ArrayDeque(), 1, view, statusBarHeight)
                    Log.i("cardPlaceArr", "${cardPlaceArr.size} ")
                    // 상태바 끝나는 지점 : 75}
                }
            }
        }
        Log.i("cardPlaceArr", "${cardPlaceArr.size} ")

//        cardPlaceArr.addAll(
//            listOf(
//                binding.cardPlaceComplete1,
//                binding.cardPlaceComplete2,
//                binding.cardPlaceComplete3,
//                binding.cardPlaceComplete4,
//                binding.cardPlace1,
//                binding.cardPlace2,
//                binding.cardPlace3,
//                binding.cardPlace4,
//                binding.cardPlace5,
//                binding.cardPlace6,
//                binding.cardPlace7
//            )
//        )

//        var card: Card? = Card(5, Card.CLUBS)
//        if (intent.hasExtra("cardInfo")) {
//            val array = intent.getIntegerArrayListExtra("cardInfo")
//            card = array?.get(0)?.let { Card(it, array[1]) }
//
//            card?.let {
//                binding.textView3.text = card.name
//                binding.imageView2.setImageResource(
//                    applicationContext.resources.getIdentifier(
//                        "${card.getShapeName()}_${card.number}",
//                        "drawable", applicationContext.packageName
//                    )
//                )
//            }
//        }


        binding.resetButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                .setTitle(R.string.reset_title)
                .setMessage(R.string.reset_message)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ -> resetGame() }
                .setNegativeButton(R.string.cancle) { _, _ -> }
                .create()
            builder.show()
        }
        binding.undoButton.setOnClickListener { clickUndo() }

//        binding.imageView2.setOnLongClickListener(LongClickListener())
//        binding.imageView2.setOnClickListener { v ->
//            val tag = "card image"
//            val item = ClipData.Item(v?.tag as? CharSequence)
//            val dragData = ClipData(
//                v?.tag as? CharSequence,
//                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
//                item
//            )
//
//            v?.startDragAndDrop(dragData, View.DragShadowBuilder(v), null, 0)
//            true
//        }

        binding.cardPlaceStart.post { startGame() }
    }

    var mouseX = 0f // 클릭 위치
    var mouseY = 0f
    val moveStartRange = -30f..30f

    inner class TouchListener(private val cardView: CardView) : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            v?.let {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        cardView.lastX = v.x
                        cardView.lastY = v.y
                        mouseX = event.rawX
                        mouseY = event.rawY
//                        v.moveTo(event.rawX - v.x + v.width / 2, event.rawY - v.y + v.height / 2)
                        Log.i(
                            "MotionEvent ACTION_DOWN",
                            "event.rawX : ${event.rawX}, v.x : ${v.x}, mouseX : $mouseX, "
                                    + "last : ${cardView.lastX} ${cardView.lastY}"
                        )
                        cardView.isMoveStart = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val nx = event.rawX - v.width / 2
                        val ny = event.rawY - statusBarHeight - v.height / 2
//                        val nx = event.rawX - v.x
//                        val ny = event.rawY - v.y

//                        Log.i(
//                            "MotionEvent ACTION_MOVE",
//                            "v.x : ${v.x}, event.rawX : ${event.rawX}, " +
//                                    "mouseX : $mouseX, isMoveStart : $isMoveStart"
//                        )
                        v.bringToFront()

                        if (cardView.isFront && cardView.isMoveStart &&
                            nx in 0f..screenWidth.toFloat() - v.width && ny in 0f..screenHeight.toFloat() - v.height
                        )
                            v.moveTo(nx, ny)
                        else if (!cardView.isMoveStart && (event.rawX - mouseX !in moveStartRange || event.rawY - mouseY !in moveStartRange))
                            cardView.isMoveStart = true
                        else true
                    }
                    MotionEvent.ACTION_UP -> {
                        val range = 5
                        if (cardView.isMoveStart) {
                            Log.i(
                                "MotionEvent ACTION_UP",
                                "last : ${cardView.lastX} ${cardView.lastY}"
                            )
                            var isInCardPlace = false
                            for ((view, obj) in cardPlaceArr) {
                                // 박스 안에 카드를 놓으면 박스 중앙으로 이동

                                Log.i("view Position", "${obj.pos.contentToString()} ")
                                if ((v.x + v.width / 2 - obj.pos[0]).toInt() in obj.width / range..obj.width * 4 / range &&
                                    (v.y + v.height / 2 - obj.pos[1]).toInt() in obj.height / range..obj.height * 4 / range
                                ) {
                                    isInCardPlace = true
                                    v.moveTo(
                                        obj.pos[0] + (obj.width - v.width) / 2f,
                                        obj.pos[1] + (obj.height - v.height) / 2f
                                    )
                                    addScore(10)
                                    cardPlaceArr[cardView.lastCardPlace]!!.removeCard()
                                    obj.addCard(cardView.card)
                                    cardView.lastCardPlace = view
                                    break
                                }
                            }
                            if (!isInCardPlace) {
                                v.moveTo(cardView.lastX, cardView.lastY)
                            } else true

                        } else v.performClick()
                    }
                    else -> true
                }
            }
            return true  // false로 하면 setOnClickListener 실행 가능
            // 추가 설명 -> https://www.masterqna.com/android/2054/ontouch%EC%99%80-onlongclick
        }
    }

    private fun addScore(num: Int) {
        score += num
        binding.scoreTextView.text = getString(R.string.score, score)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime <= 1500) {
            super.onBackPressed()
            startActivity(Intent(this@ScorpionActivity, MainActivity::class.java))
            finish()
        }
        Snackbar.make(binding.root, R.string.notify_back, Snackbar.LENGTH_LONG).show()
        waitTime = System.currentTimeMillis()
    }

    private fun View.moveTo(x: Float, y: Float) {
        this.animate()
            .x(x)
            .y(y)
            .setDuration(0)
            .start()
    }

    private fun dpToPx(dp: Float, context: Context = applicationContext): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    private fun startGame() {
        for (i in 0..50) {
            cards.add(
                CardView(
                    applicationContext,
                    (1..13).random(),
                    (0..3).random(),
                    isFront = false,
                    lastCardPlace = binding.cardPlaceStart
                )
            )
            cardPlaceArr[binding.cardPlaceStart]!!.addCard(cards[i].card)
            cards[i].setOnTouchListener { v, event ->
                TouchListener(cards[i]).onTouch(v, event)
            }
            cards[i].setOnClickListener {
                if (!cards[i].isFront) {
                    cards[i].setIsFront(true)
                    cards[i].moveTo(cards[i].x - binding.cardPlaceStart.width, cards[i].y)
                    cards[i].bringToFront()
                } else {
                    cards[i].setCard((1..13).random(), (0..3).random())
                }
            }
            binding.root.addView(cards[i])
            cards[i].post {
                cards[i].moveTo(
                    startPlacePosition[0] + (binding.cardPlaceStart.width - cards[i].width.toFloat()) / 2,
                    startPlacePosition[1] + (binding.cardPlaceStart.height - cards[i].height.toFloat()) / 2 - statusBarHeight
                )
            }
        }

        score = 500
        addScore(0)
    }

    private fun resetGame() {
        cards.forEach { binding.root.removeView(it) }
        cards.clear()
        startGame()
    }

    private fun clickUndo() {
        addScore(-1)
    }
}